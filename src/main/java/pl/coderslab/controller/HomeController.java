package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;

import javax.servlet.http.HttpSession;
import java.util.Optional;

@Controller
@RequestMapping("/")
public class HomeController {

    private UserService userService;

    public HomeController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String homePage(Model model) {
        model.addAttribute("user", new User());
        return "homePage"; // Nazwa pliku widoku
    }

    @GetMapping("/about")
    public String aboutPage() {
        return "aboutPage";
    }

    @GetMapping("/user/home")
    public String loggedPage() {
        return "userPage"; // Nazwa pliku widoku
    }

    @GetMapping("/t")
    public String testPage() {
        return "picotest"; // Nazwa pliku widoku
    }
}