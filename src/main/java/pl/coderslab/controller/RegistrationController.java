package pl.coderslab.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    @Autowired
    public RegistrationController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());
        return "registerPage";
    }

    @PostMapping("")
    public String registerUser(@ModelAttribute User user) {
        System.out.println("===== Received password: " + user.getPassword());
        userService.registerUser(user);
        return "redirect:/login";
    }
}

