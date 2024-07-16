package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;

import javax.servlet.http.HttpServletRequest;

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
        System.out.println("\033[36m}}}}}}}}}}}}}}}}TESTING}}}}}}}}}}}}}}}}}}\033[0m");

        return "homePage"; // Nazwa pliku widoku
    }

    @GetMapping("/about")
    public String aboutPage(HttpServletRequest request, Model model) {
        String referer = request.getHeader("Referer");
        model.addAttribute("prevUrl", referer != null ? referer : "/");
        return "aboutPage";
    }

    @GetMapping("/user/home")
    public String loggedPage() {
        return "userPage"; // Nazwa pliku widoku
    }

    @GetMapping("/t")
    public String testPage() {
        return "picotest";
    }
}