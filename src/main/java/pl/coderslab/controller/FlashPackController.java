package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.app.FileChooserUtil;
import pl.coderslab.model.Flashcard;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;
import pl.coderslab.service.FlashcardService;
import pl.coderslab.service.PacketService;
import pl.coderslab.service.UserService;
import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("")
public class FlashPackController {

    private final UserService userService;
    private final PacketService packetService;
    private final FlashcardService flashcardService;

    @Autowired
    public FlashPackController(UserService userService, PacketService packetService, FlashcardService flashcardService) {
        this.userService = userService;
        this.packetService = packetService;
        this.flashcardService = flashcardService;
    }

    // NOWY PAKIET
    @GetMapping("/flashpack/new/packet")
    public String newPacketPage(Model model) {
        model.addAttribute("packet", new Packet());
        return "newUserPacket";
    }

    @PostMapping("/flashpack/new/packet")
    public String createNewPacket(@ModelAttribute Packet packet, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        Packet savedPacket = packetService.addPacket(packet, user);
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
        model.addAttribute("packets", userPackets);
        return "userPacketsList";
    }

    // ADMIN: POKAŻ PAKIETY USERA
    @GetMapping("/admin/users/packets/{id}")
    public String showPackets(Model model, @PathVariable Long id) {
        User user = userService.getUser(id).orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        List<Packet> userPackets = userService.getUserPackets(id);
        model.addAttribute("user", user);
        model.addAttribute("packets", userPackets);
        return "adminUserPacketsList";
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
        return "userPacketEdit";
    }

    @PostMapping("/flashpack/user/packets/edit")
    public String editPacket(@ModelAttribute Packet packet, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        packetService.updatePacket(packet);
        return "redirect:/flashpack/user/packets";
    }

    @PostMapping("/flashpack/user/packets/{packetId}/update-study-settings")
    public String updateStudySettings(@PathVariable Long packetId,
                                      @RequestParam(required = false) Set<String> showFields,
                                      @RequestParam(required = false) String compareField) {
        packetService.updateStudySettings(packetId, showFields, compareField);
        return "redirect:/flashpack/user/packets/" + packetId + "/flashcards";
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
        model.addAttribute("flashcards", packet.getFlashcards());
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
    public String addFlashcard(@PathVariable Long packetId, @ModelAttribute Flashcard flashcard) {
        Packet packet = packetService.getPacket(packetId).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
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
    public String editFlashcard(@PathVariable Long packetId, @ModelAttribute Flashcard flashcard) {
        Packet packet = packetService.getPacket(packetId).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        flashcard.setPack(packet);
        flashcardService.updateFlashcard(flashcard);
        return "redirect:/flashpack/user/packets/" + packetId + "/flashcards";
    }

    // WYBÓR PLIKU OBRAZ/DŹWIĘK

    @GetMapping("/choose-file")
    @ResponseBody
    public String chooseFile(@RequestParam String type) {
        String title = type.equals("image") ? "Choose Image File" : "Choose Sound File";
        return FileChooserUtil.chooseFile(title);
    }


    // KASOWANIE FISZKI
    @GetMapping("/flashpack/user/packets/{packetId}/flashcards/delete/{id}")
    public String deleteFlashcard(@PathVariable Long packetId, @PathVariable Long id) {
        flashcardService.deleteFlashcard(id);
        return "redirect:/flashpack/user/packets/" + packetId + "/flashcards";
    }

    // ROZPOCZNIJ SESJĘ NAUKI
    @GetMapping("/flashpack/user/packets/{id}/study")
    public String startStudySession(@PathVariable Long id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found");
        }
        Packet packet = packetService.getPacket(id).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
        model.addAttribute("packet", packet);
        return "studySession";
    }
}
