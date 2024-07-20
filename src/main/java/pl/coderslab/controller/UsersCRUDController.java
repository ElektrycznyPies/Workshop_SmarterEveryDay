package pl.coderslab.controller;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.Packet;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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
        List<User> allUsers = userService.getAllUsers();

        Map<Long, Boolean> usersWithExclusivePackets = allUsers.stream()
                .collect(Collectors.toMap(User::getId, user -> user.getPackets().stream()
                                .anyMatch(packet -> packet.getUsers().size() == 1 && packet.getUsers().contains(user))
                )); // czy user jest przypisany do pakietu, do kt. jest przypisany tylko 1 user, i ten user to on :)

        Map<Long, Boolean> usersWithSharedPackets = allUsers.stream()
                .collect(Collectors.toMap(User::getId, user -> user.getPackets().stream()
                        .anyMatch(packet -> packet.getUsers().size() > 1 && packet.getUsers().contains(user))
                )); // czy user jest przypisany do pakietu, do kt. jest przypisany ponad 1 user

        model.addAttribute("usersWithExclusivePackets", usersWithExclusivePackets);
        model.addAttribute("usersWithSharedPackets", usersWithSharedPackets);
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
            updatedUser.setNick(user.getNick());
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

    // KASOWANIE
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users/all";
    }
}
