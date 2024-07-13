package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;
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

    @Autowired
    public FlashPackController(UserService userService, PacketService packetService) {
        this.userService = userService;
        this.packetService = packetService;
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
            throw new EntityNotFoundException("User not found in session");
        }
        packetService.addPacket(packet, user);
        return "redirect:/flashpack/user/packets";
    }


    // USER POBIERA SWOJE PAKIETY

    @GetMapping("/flashpack/user/packets")
    public String showUserPackets(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            throw new EntityNotFoundException("User not found in session");
        }
        List<Packet> userPackets = userService.getUserPackets(user.getId());
        model.addAttribute("packets", userPackets);
        return "userPacketsList";
    }

    // ADMIN POBIERA PAKIETY USERA
    @GetMapping("/admin/users/packets/{id}")
    public String showPackets(Model model, @PathVariable Long id) {
        User user = userService.getUser(id).orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        List<Packet> userPackets = userService.getUserPackets(id);
        model.addAttribute("user", user);
        model.addAttribute("packets", userPackets);
        return "adminUserPacketsList";
    }

    // NOWA FISZKA W PAKIECIE
    @PostMapping("/flashpack/new/flashcard")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/";
    }
}
