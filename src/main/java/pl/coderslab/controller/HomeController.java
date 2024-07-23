package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import pl.coderslab.model.StudySession;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/")
public class HomeController {

    private UserService userService;
    private StudyController studyController;
    public HomeController(UserService userService, StudyController studyController) {
        this.userService = userService;
        this.studyController = studyController;
    }

    @GetMapping("")
    public String homePage(Model model) {
        model.addAttribute("user", new User());
        return "homePage"; // Nazwa pliku widoku
    }

    @GetMapping("/about")
    public String aboutPage(HttpServletRequest request, Model model) {
        String referer = request.getHeader("Referer");
        model.addAttribute("prevUrl", referer != null ? referer : "/");
        return "aboutPage";
    }

    @GetMapping("/user/home")
    public String loggedPage(HttpSession sess, Model model) {

        studyController.getStats(sess, model);
        return "userPage";
    }

    @GetMapping("/t")
    public String testPage() {
        return "picotest";
    }
}