package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;

import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String homePage() {
        return "homePage"; // Nazwa pliku widoku
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "aboutPage";
    }
    @GetMapping("/login")
    public String loginPage() {
        return "loginPage";
    }

    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password, Model model) {
        Optional<User> optionalUser = userService.authorize(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userService.checkPassword(password, user.getPassword())) {
                // Zalogowano pomyślnie
                model.addAttribute("user", user);
                return "redirect:/user/home";
            }
        }
        // Nieudane logowanie
        model.addAttribute("error", "Invalid email or password");
        return "loginPage";
    }

    @GetMapping("/user/home")
    public String loggedPage() {
        return "homePage"; // Nazwa pliku widoku
    }

    @GetMapping("/t")
    public String testPage() {
        return "picotest"; // Nazwa pliku widoku
    }
}