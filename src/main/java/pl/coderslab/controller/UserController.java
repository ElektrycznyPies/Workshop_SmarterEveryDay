package pl.coderslab.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/user")
public class UserController {

    private UserService userService;
    private PasswordEncoder passwordEncoder;
    public UserController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    // EDYCJA PROFILU PRZEZ USERA
    @GetMapping("/edit")
    public String showEditProfileForm(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        if (user == null) {
            return "redirect:/login";
        }
        model.addAttribute("user", user);
        return "userProfileEdit";
    }
    @PostMapping("/edit")
    public String editUserProfile(@Valid User user, BindingResult result, HttpSession session) {
        if (result.hasErrors()) {
            return "userProfileEdit";
        }
        User sessionUser = (User) session.getAttribute("user");
        if (sessionUser == null || !sessionUser.getId().equals(user.getId())) {
            return "redirect:/login";
        }
        User updatedUser = userService.getUser(user.getId()).orElseThrow(() -> new EntityNotFoundException("User not found"));
        updatedUser.setFirstName(user.getFirstName());
        updatedUser.setLastName(user.getLastName());
        updatedUser.setNick(user.getNick());
        updatedUser.setEmail(user.getEmail());
        if (!user.getPassword().isEmpty()) {
            String encodedPassword = passwordEncoder.encode(user.getPassword());
            updatedUser.setPassword(encodedPassword);
        }
        userService.updateUser(updatedUser);
        session.setAttribute("user", updatedUser);
        return "redirect:/user/home";
    }
}
