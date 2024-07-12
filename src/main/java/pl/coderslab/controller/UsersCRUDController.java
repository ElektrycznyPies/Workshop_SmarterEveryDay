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
        return "adminUsersList";  // nazwa pliku
    }

    // DODAWANIE
    @GetMapping(value = "/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "adminUserAdd";
    }


    @PostMapping(value = "/add")
    public String saveUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "adminUserAdd";
        }
        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        userService.addUser(user);
        return "redirect:/admin/users/all";
    }

    // WYŚWIETL detale jednej PO ID

    @GetMapping("/show/packages/{id}")
    public String showUser(Model model, @PathVariable Long id) { //REDO:POKAŻ PACKAGE, A NIE SZCZEG. USERA
        //stworzenie obiektu, ponieważ met. get() z UserController daje Optional, a nie obiekt. Optional utrudnia pracę z jsp
        User user = userService.getUser(id).orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        model.addAttribute("user", user);
        return "adminUserPackagesList";
    }

    // EDYCJA
    @GetMapping(value = "/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        Optional<User> userOptional = userService.getUser(id);
        if (userOptional.isPresent()) {
            model.addAttribute("user", userOptional.get());
            return "adminUserEdit";
        } else {
            // Handle the case where the user is not found
            return "redirect:/admin/users/all";
        }
    }

    @PostMapping(value = "/edit")
    public String editUser(@Valid User user, BindingResult result, @RequestParam(required = false) boolean isAdmin) {
        if (result.hasErrors()) {
            return "adminUserEdit";
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
            updatedUser.setRole(isAdmin ? 1L : 0L);
            userService.updateUser(updatedUser);
        }
        return "redirect:/admin/users/all";
    }
    //    @PostMapping(value = "/edit")
//    public String editUser(@Valid User user, BindingResult result) {
//        if (result.hasErrors()) {
//            return "userEdit";
//        }
//        Optional<User> existingUser = userService.getUser(user.getId());
//        if (existingUser.isPresent()) {
//            User updatedUser = existingUser.get();
//            updatedUser.setFirstName(user.getFirstName());
//            updatedUser.setLastName(user.getLastName());
//            updatedUser.setEmail(user.getEmail());
//            if (!user.getPassword().equals(updatedUser.getPassword())) {
//                String encodedPassword = passwordEncoder.encode(user.getPassword());
//                updatedUser.setPassword(encodedPassword);
//            }
//            userService.updateUser(updatedUser);
//        }
//        return "redirect:/admin/users/all";
//    }


    // KASOWANIE
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users/all";
    }


}
