import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;

import javax.persistence.EntityNotFoundException;

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

    @GetMapping("/show/packages/{id}") //////REDO REDO//////////
    public String showPackages(Model model, @PathVariable Long id) {
        //stworzenie obiektu, ponieważ met. get() z UserController daje Optional, a nie obiekt. Optional utrudnia pracę z jsp
        User user = userService.getUser(id).orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        model.addAttribute("user", user);
        return "adminUserPackagesList";
    }

    @PostMapping("/newFlashcard")
    public String registerUser(@ModelAttribute User user) {
        userService.registerUser(user);
        return "redirect:/";
    }
}
