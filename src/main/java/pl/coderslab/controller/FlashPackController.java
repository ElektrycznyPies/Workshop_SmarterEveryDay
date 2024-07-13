package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@Controller
@RequestMapping("")
public class FlashPackController {

    private final UserService userService;
    @Autowired
    public FlashPackController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/newPacket")
    public String newPackagePage(Model model) {
        model.addAttribute("user", new User());
        return "redirect:/";
    }

    @GetMapping("/admin/users/packets/{id}")
    public String showPackages(Model model, @PathVariable Long id) {
        User user = userService.getUser(id).orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        List<Packet> userPackets = userService.getUserPackets(id);
        model.addAttribute("user", user);
        model.addAttribute("packets", userPackets);
        return "adminUserPacketsList";
    }

    @PostMapping("/newFlashcard")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/";
    }
}
