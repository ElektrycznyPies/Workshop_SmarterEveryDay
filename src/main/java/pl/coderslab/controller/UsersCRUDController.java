package pl.coderslab.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.Optional;

@Controller
@RequestMapping("/admin/users")
public class UsersCRUDController {

    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    public UsersCRUDController(UserService userService, PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }


    // POKAŻ WSZYSTKIE
    @GetMapping("/all")     // w przeglądarce
    public String showAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "usersAll";  // nazwa pliku
    }

    // DODAWANIE
    @GetMapping(value = "/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "userAdd";
    }


    @PostMapping(value = "/add")
    public String saveUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "userAdd";
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.addUser(user);
        return "redirect:/admin/users/all";
    }

    // WYŚWIETL detale jednej PO ID

    @GetMapping("/show/{id}")
    public String showUser(Model model, @PathVariable Long id) {
        //stworzenie obiektu, ponieważ met. get() z UserController daje Optional, a nie obiekt. Optional utrudnia pracę z jsp
        User user = userService.getUser(id).orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        model.addAttribute("user", user);
        return "showOne";
    }

    // EDYCJA
    @GetMapping(value = "/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "userEdit";
    }

    @PostMapping(value = "/edit")
    public String editUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "userEdit";
        }
        Optional<User> existingUser = userService.getUser(user.getId());
        if (existingUser.isPresent()) {
            User updatedUser = existingUser.get();
            updatedUser.setFirstName(user.getFirstName());
            updatedUser.setLastName(user.getLastName());
            updatedUser.setEmail(user.getEmail());
            if (!user.getPassword().equals(updatedUser.getPassword())) {
                String encodedPassword = passwordEncoder.encode(user.getPassword());
                updatedUser.setPassword(encodedPassword);
            }
            userService.updateUser(updatedUser);
        }
        return "redirect:/admin/users/all";
    }

//    @PostMapping(value = "/edit")
//    public String editUser(@Valid User user, BindingResult result) {
//        if (result.hasErrors()) {
//            return "userEdit";
//        }
//        userService.addUser(user);
//        return "redirect:/admin/users/all";
//    }


    // KASOWANIE
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users/all";
    }


}
