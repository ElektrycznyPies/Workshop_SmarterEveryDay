package pl.coderslab.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.coderslab.app.FileChooserUtil;
import pl.coderslab.app.NameShortenerUtil;
import pl.coderslab.model.Flashcard;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;
import pl.coderslab.service.FlashcardService;
import pl.coderslab.service.PacketService;
import pl.coderslab.service.UserService;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("")
public class FlashPackController {
    private static final Logger logger = LoggerFactory.getLogger(FlashPackController.class);

    private final UserService userService;
    private final PacketService packetService;
    private final FlashcardService flashcardService;

    @Autowired
    public FlashPackController(UserService userService, PacketService packetService, FlashcardService flashcardService) {
        this.userService = userService;
        this.packetService = packetService;
        this.flashcardService = flashcardService;
    }


    // ADMIN: POKAŻ PAKIETY USERA
    @GetMapping("/admin/users/packets/{id}")
    public String showPackets(Model model, @PathVariable Long id) {
        User user = userService.getUser(id).orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        List<Packet> userPackets = userService.getUserPackets(id);

        List<Packet> sortedPackets = new ArrayList<>(userPackets);
        sortedPackets.sort(Comparator.comparing(packet -> packet.getName().toLowerCase())); //sort. po name, bez case sens.
        model.addAttribute("user", user);
        model.addAttribute("packets", sortedPackets);
        model.addAttribute("packetsWithFlashcards", sortedPackets.stream()
                .collect(Collectors.toMap(
                        Packet::getId,
                        p -> p.getFlashcards().size()
                ))); // podaje liczbę fiszek w każdym pakiecie
        return "adminUserPacketsList";
    }

    //ADMIN USUWA PAKIET
    @GetMapping("/admin/users/packets/{userId}/delete/{packetId}")
    public String admintPacketDelete(@PathVariable Long userId, @PathVariable Long packetId) {
        packetService.deletePacket(packetId);
        return "redirect:/admin/users/packets/" + userId;
    }

    // NOWY PAKIET
    @GetMapping("/flashpack/new/packet")
    public String newPacketPage(Model model) {
        model.addAttribute("packet", new Packet());
        return "newUserPacket";
    }

    @PostMapping("/flashpack/new/packet")
    public String createNewPacket(@ModelAttribute Packet packet, @RequestParam(required = false) String authorType, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        if (packet.getAuthor() == null || packet.getAuthor().trim().isEmpty()) {
            packet.setAuthor("");
        } else if ("nick".equals(authorType)) {
            packet.setAuthor(user.getNick()); // tu podmienić potem na nick
        } else if ("name".equals(authorType)) {
            packet.setAuthor(user.getFullName());
        }
        packetService.addPacket(packet, user);
        return "redirect:/flashpack/user/packets";
    }


    // USER: POKAŻ WŁASNE PAKIETY

    @GetMapping("/flashpack/user/packets")
    public String showUserPackets(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        List<Packet> userPackets = userService.getUserPackets(user.getId());
        List<Packet> sortedPackets = new ArrayList<>(userPackets);
        sortedPackets.sort(Comparator.comparing(packet -> packet.getName().toLowerCase())); // sort. po name, bez case sens.


        model.addAttribute("packets", sortedPackets);
        model.addAttribute("packetsWithFlashcards", sortedPackets.stream()
                .collect(Collectors.toMap(
                        Packet::getId,
                        p -> p.getFlashcards().size()
                ))); // podaje liczbę fiszek w każdym pakiecie
        model.addAttribute("noFieldsSet", sortedPackets.stream()
                .collect(Collectors.toMap(
                        Packet::getId,
                        p -> p.getShowFields().isEmpty() ||
                                (p.getCompareField() == null || p.getCompareField().isEmpty() || p.getCompareField().isBlank())
                        // sprawdza, czy każdy pakiet ma ustawione pola wyświetlania/porównawcze
                )));
        return "userPacketsList";
    }

    // EDYCJA PAKIETU

    @GetMapping("/flashpack/user/packets/edit/{id}")
    public String editPacketPage(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        Packet packet = packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        model.addAttribute("packet", packet);
        model.addAttribute("user", user);
        return "userPacketEdit";
    }

    @PostMapping("/flashpack/user/packets/edit")
    public String editPacket(@ModelAttribute Packet packet, @RequestParam(required = false) String authorType, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }

        // pobiera istniejący pakiet z bazy danych
        Packet existingPacket = packetService.getPacket(packet.getId())
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));

        // aktualizuje pola pakietu
        existingPacket.setName(packet.getName());
        existingPacket.setDescription(packet.getDescription());

        // obsł. pola author
        if (packet.getAuthor() == null || packet.getAuthor().trim().isEmpty()) {
            existingPacket.setAuthor("");
        } else if ("nick".equals(authorType)) {
            existingPacket.setAuthor(user.getNick());
        } else if ("name".equals(authorType)) {
            existingPacket.setAuthor(user.getFullName());
        } else {
            existingPacket.setAuthor(packet.getAuthor());
        }
        packetService.updatePacket(existingPacket);
        return "redirect:/flashpack/user/packets";
    }

    // USUWANIE PAKIETU
    @GetMapping("/flashpack/user/packets/delete/{id}")
    public String deletePacket(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        packetService.deletePacket(id);
        return "redirect:/flashpack/user/packets";
    }

    // POKAŻ FISZKI W PAKIECIE

    @GetMapping("/flashpack/user/packets/{id}/flashcards")
    public String showFlashcards(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        Packet packet = packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        List<Flashcard> sortedFlashcards = new ArrayList<>(packet.getFlashcards());
        sortedFlashcards.sort(Comparator.comparing(flashcard -> flashcard.getName().toLowerCase()));

        int lenOfShortValue = 24; // ile liter linku pokazać w tabelce


        // skracanie linku obrazków i addit. text do tabeli ze spr. nulla
        List<String> shortImageLinks = sortedFlashcards.stream()
                .map(flashcard -> flashcard.getImageLink() != null
                        ? NameShortenerUtil.shortenName(flashcard.getImageLink(), 2, 16)
                        : "")
                .collect(Collectors.toList());

        List<String> shortAdditionalTexts = sortedFlashcards.stream()
                .map(flashcard -> flashcard.getAdditionalText() != null
                        ? NameShortenerUtil.shortenName(flashcard.getAdditionalText(), 1, 24)
                        : "")
                .collect(Collectors.toList());

        model.addAttribute("flashcards", sortedFlashcards);
        model.addAttribute("shortImageLinks", shortImageLinks);
//        model.addAttribute("shortImageLinks2", shortImageLinks2);
        model.addAttribute("shortAdditionalTexts", shortAdditionalTexts);
        model.addAttribute("packetId", id);
        model.addAttribute("packetName", packet.getName());
        model.addAttribute("showFields", packet.getShowFields());
        model.addAttribute("compareField", packet.getCompareField());
        return "userFlashcardsList";
    }

    // DODAWANIE FISZKI, JEDEN FORM.

    @GetMapping("/flashpack/user/packets/{packetId}/flashcards/add")
    public String showAddFlashcardForm(@PathVariable Long packetId, Model model) {
        Packet packet = packetService.getPacket(packetId).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        model.addAttribute("flashcard", new Flashcard());
        model.addAttribute("packetId", packetId);
        model.addAttribute("packetName", packet.getName());
        model.addAttribute("isEdit", false);
        return "userFlashcardForm";
    }


    @PostMapping("/flashpack/user/packets/{packetId}/flashcards/add")
    public String addFlashcard(@PathVariable Long packetId, @ModelAttribute Flashcard flashcard, @RequestParam("file") MultipartFile file) {
        Packet packet = packetService.getPacket(packetId).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        if (file != null && !file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            flashcard.setImageLink(fileName);
            }
        flashcardService.addFlashcard(flashcard, packet);
        return "redirect:/flashpack/user/packets/" + packetId + "/flashcards";
    }



    // EDYCJA FISZKI, JEDEN FORM.
    @GetMapping("/flashpack/user/packets/{packetId}/flashcards/edit/{id}")
    public String showEditFlashcardForm(@PathVariable Long packetId, @PathVariable Long id, Model model) {
        Packet packet = packetService.getPacket(packetId).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        Flashcard flashcard = flashcardService.getFlashcard(id).orElseThrow(() -> new EntityNotFoundException("Flashcard not found"));
        model.addAttribute("flashcard", flashcard);
        model.addAttribute("packetId", packetId);
        model.addAttribute("packetName", packet.getName());
        model.addAttribute("isEdit", true);
        return "userFlashcardForm";
    }

    @PostMapping("/flashpack/user/packets/{packetId}/flashcards/edit")
    public String editFlashcard(@PathVariable Long packetId, @ModelAttribute Flashcard flashcard, @RequestParam("file") MultipartFile file) {
        Packet packet = packetService.getPacket(packetId).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        flashcard.setPack(packet);
        if (!file.isEmpty()) {
            String fileName = file.getOriginalFilename();
            flashcard.setImageLink(fileName);
        }

        flashcardService.updateFlashcard(flashcard);
        return "redirect:/flashpack/user/packets/" + packetId + "/flashcards";
    }


    // KASOWANIE FISZKI
    @GetMapping("/flashpack/user/packets/{packetId}/flashcards/delete/{id}")
    public String deleteFlashcard(@PathVariable Long packetId, @PathVariable Long id) {
        flashcardService.deleteFlashcard(id);
        return "redirect:/flashpack/user/packets/" + packetId + "/flashcards";
    }

    @PostMapping("/flashpack/user/packets/{packetId}/update-study-settings")
    public String updateStudySettings(@PathVariable Long packetId,
                                      @RequestParam(required = false) Set<String> showFields,
                                      @RequestParam(required = false) String compareField) {
        packetService.updateStudySettings(packetId, showFields, compareField);
        return "redirect:/flashpack/user/packets/" + packetId + "/flashcards";
    }

}
