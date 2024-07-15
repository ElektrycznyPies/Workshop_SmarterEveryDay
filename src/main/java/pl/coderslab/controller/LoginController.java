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
public class LoginController {

    private UserService userService;

    public LoginController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/login")
    public String loginPage() {
        return "redirect:/";
    }
    @PostMapping("/login")
    public String loginUser(@RequestParam String email, @RequestParam String password,
                            HttpSession sess, Model model) {
        Optional<User> optionalUser = userService.authorize(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            if (userService.checkPassword(password, user.getPassword())) {
                sess.setAttribute("user", user);
                sess.setAttribute("loggedIn", true);
                if (user.getRole() == 1L) {
                    return "redirect:/user/home";
                } else if (user.getRole() == 0L) {
                    {
                        return "redirect:/user/home";
                    }
                }
            }
        }
        model.addAttribute("error", "Invalid email or password"); //REDO:POP-UP
        return "redirect:/";
    }



}