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
@RequestMapping("/flashcard")
public class FlashcardController {

    private final UserService userService;
    @Autowired
    public FlashcardController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/newPackage")
    public String newPackagePage(Model model) {
        model.addAttribute("user", new User());
        return "redirect:/";
    }

    @PostMapping("/newFlashcard")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/";
    }
}
