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
import pl.coderslab.service.CategoryService;
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
    private final CategoryService categoryService;

    @Autowired
    public FlashPackController(UserService userService, PacketService packetService, FlashcardService flashcardService, CategoryService categoryService) {
        this.userService = userService;
        this.packetService = packetService;
        this.flashcardService = flashcardService;
        this.categoryService = categoryService;
    }

    // P A K I E T Y
    // ADMIN: POKAŻ PAKIETY USERA
    @GetMapping("/admin/users/packets/{id}")
    public String showPackets(Model model, @PathVariable Long id) {
        User user = userService.getUser(id).orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        List<Packet> userPackets = userService.getUserPackets(id);

        List<Packet> sortedPackets = new ArrayList<>(userPackets);
        sortedPackets.sort(Comparator.comparing(packet -> packet.getName().toLowerCase())); //sort. po name, bez case sens.
        model.addAttribute("sharedPackets", sortedPackets.stream()
                .collect(Collectors.toMap(
                        Packet::getId,
                        p -> p.getUsers().size() > 1
                ))); // czy pakiet jest dzielony z kimś
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
    public String adminPacketDelete(@PathVariable Long userId, @PathVariable Long packetId) {
        packetService.destroyPacket(packetId); //admin całkowicie usuwa pakiet
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
            packet.setAuthor(user.getNick());
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
        model.addAttribute("packetsShared", sortedPackets.stream()
                .collect(Collectors.toMap(
                        Packet::getId,
                        p -> p.getUsers().size() > 1
                ))); // czy pakiet dzielony z innymi
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
//    @GetMapping("/flashpack/user/packets/delete/{id}")
//    public String deletePacket(@PathVariable Long id, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            throw new EntityNotFoundException("User not found");
//        }
//        packetService.deletePacket(id);
//        return "redirect:/flashpack/user/packets";
//    }
    @GetMapping("/flashpack/user/packets/delete/{id}")
    public String deletePacket(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        packetService.removePacketFromUser(id, user.getId()); // jeśli pakiet dzielony, tylko odłącza od usera
        return "redirect:/flashpack/user/packets";
    }

    // BAZAR
    @GetMapping("/flashpack/user/packets/sendToBazaar/{id}")
    public String sendToBazaar(@PathVariable Long id) {
        Packet packet = packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        packet.setOnBazaar(true);
        packetService.updatePacket(packet);
        return "redirect:/flashpack/user/packets";
    }

    @GetMapping("/flashpack/bazaar")
    public String showBazaar(@RequestParam(required = false) List<Long> categoryIds, Model model, HttpSession sess) {
        List<Packet> bazaarPackets;
        User user = (User) sess.getAttribute("user");
        if (user == null){
            throw new EntityNotFoundException("User not found.");
        }
        if (categoryIds == null || categoryIds.isEmpty()) {
            bazaarPackets = packetService.getBazaarPackets();
        } else {
            bazaarPackets = packetService.getBazaarPacketsByCategories(categoryIds);
        }

        // pakiety bazaru, których nie ma user
        List<Packet> filteredPackets = bazaarPackets.stream()
                .filter(packet -> !packet.getUsers().stream()
                        .anyMatch(u -> u.getId().equals(user.getId())))
                .collect(Collectors.toList());

        // pakiety, które ma user - będą wyszarzone na liście
        List<Packet> packetsGrey = bazaarPackets.stream()
                .filter(packet -> packet.getUsers().stream()
                        .anyMatch(u -> u.getId().equals(user.getId())))
                .collect(Collectors.toList());

        model.addAttribute("packetsWithFlashcardsBaz", filteredPackets.stream()
                .collect(Collectors.toMap(
                        Packet::getId,
                        p -> p.getFlashcards().size()
                ))); // podaje liczbę fiszek w każdym pakiecie
        model.addAttribute("greyPacketsWithFlashcardsBaz", packetsGrey.stream()
                .collect(Collectors.toMap(
                        Packet::getId,
                        p -> p.getFlashcards().size()
                ))); // podaje liczbę fiszek w każdym pakiecie
        model.addAttribute("packets", filteredPackets);
        model.addAttribute("packets_grey", packetsGrey);
        model.addAttribute("categories", categoryService.getAllCategories());
        return "userBazaarPacketsList";
    }

//    @GetMapping("/flashpack/bazaar/get/{id}")
//    public String getPacketFromBazaar(@PathVariable Long id, HttpSession session) {
//        User user = (User) session.getAttribute("user");
//        if (user == null) {
//            throw new EntityNotFoundException("User not found");
//        }
//        Packet packet = packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
//        packet.getUsers().add(user);
//        packetService.updatePacket(packet);
//        return "redirect:/flashpack/bazaar";
//    }

    @GetMapping("/flashpack/bazaar/get/{id}")
    public String getPacketFromBazaar(@PathVariable Long id, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        Packet packet = packetService.getPacket(id)
                .orElseThrow(() -> new EntityNotFoundException("Packet not found"));

        // Dodaj użytkownika do pakietu
        packet.getUsers().add(user);

        // Dodaj pakiet do użytkownika
        user.getPackets().add(packet);

        packetService.updatePacket(packet);
        userService.updateUser(user);
        if (session.getAttribute("fromwhere") == "admin") {
            return "redirect:/admin/users/all";
        } else {
            return "redirect:/flashpack/bazaar";
        }
    }




        // F I S Z K I

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
