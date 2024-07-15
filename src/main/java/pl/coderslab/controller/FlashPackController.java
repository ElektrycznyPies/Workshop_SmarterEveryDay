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
        return "userFlashcardsList";
    }


    // DODAWANIE FISZKI
    @GetMapping("/flashpack/user/packets/{packetId}/flashcards/add")
    public String showAddFlashcardForm(@PathVariable Long packetId, Model model) {
        model.addAttribute("flashcard", new Flashcard());
        model.addAttribute("packetId", packetId);
        return "userFlashcardAdd";
    }

//    @PostMapping("/flashpack/user/packets/{packetId}/flashcards/add")
//    public String addFlashcard(@PathVariable Long packetId, @ModelAttribute Flashcard flashcard) {
//        Packet packet = packetService.getPacket(packetId).orElseThrow(() -> new EntityNotFoundException("Packet not found"));
//        flashcardService.addFlashcard(flashcard, packet);
//        return "redirect:/flashpack/user/packets/" + packetId + "/flashcards";
//    }

    @PostMapping("/flashpack/user/packets/{packetId}/flashcards/add")
    public String addFlashcard(@PathVariable Long packetId, @ModelAttribute Flashcard flashcard) {
        Packet packet = packetService.getPacket(packetId).orElseThrow(() -> new EntityNotFoundException("Packet not found"));

        String imagePath = FileChooserUtil.chooseFile("Choose Image File");
        if (imagePath != null) {
            flashcard.setImageLink(imagePath);
        }
        String soundPath = FileChooserUtil.chooseFile("Choose Sound File");
        if (soundPath != null) {
            flashcard.setSoundLink(soundPath);
        }
        flashcardService.addFlashcard(flashcard, packet);
        return "redirect:/flashpack/user/packets/" + packetId + "/flashcards";
    }

    // EDYCJA FISZKI
    @GetMapping("/flashpack/user/packets/{packetId}/flashcards/edit/{id}")
    public String showEditFlashcardForm(@PathVariable Long packetId, @PathVariable Long id, Model model) {
        Flashcard flashcard = flashcardService.getFlashcard(id).orElseThrow(() -> new EntityNotFoundException("Flashcard not found"));
        model.addAttribute("flashcard", flashcard);
        model.addAttribute("packetId", packetId);
        return "userFlashcardEdit";
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
    public String chooseFile(Model model) {
        String filePath = FileChooserUtil.chooseFile("Choose File");
        model.addAttribute("filePath", filePath);
        return "fileChooserResult";
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
