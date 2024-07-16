package pl.coderslab.controller;

import ch.qos.logback.core.pattern.color.ANSIConstants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;
import javax.validation.Valid;
import java.awt.*;

@Controller
@RequestMapping("/register")
public class RegistrationController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public RegistrationController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }



//    @GetMapping(value = "/add")
//    public String showAddForm(Model model) {
//        model.addAttribute("user", new User());
//        return "adminUserAdd";
//    }
//
//    @PostMapping(value = "/add")
//    public String saveUser(@Valid User user, BindingResult result) {
//        if (result.hasErrors()) {
//            return "adminUserAdd";
//        }
//        String encodedPassword = passwordEncoder.encode(user.getPassword());
//        user.setPassword(encodedPassword);
//        userService.addUser(user);
//        return "redirect:/admin/users/all";
//    }




    @GetMapping("")
    public String registerPage(Model model) {
        model.addAttribute("user", new User());

        System.out.println("\033[36m}}}}}}}}}}}}}}}}GET MAPPING}}}}}}}}}}}}}}}}}}\033[0m");
        return "homePage";
    }

    @PostMapping("")
    public String registerUser(@Valid @ModelAttribute User user, BindingResult result) {
//    public String registerUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            System.out.println("\033[36m}}}}}}}}}}}}}}}}POST MAPPING}}}}}}}}}}}}}}}}}}\033[0m");
            return "homePage";
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        System.out.println("}}}}}}}}}}}}}}}}}}}}}}}Dodawany user: " + user.getFullName());
        userService.addUser(user);
        return "redirect:/login";
    }
}