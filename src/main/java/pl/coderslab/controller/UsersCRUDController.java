package pl.coderslab.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import pl.coderslab.model.User;
import pl.coderslab.service.UserService;

import javax.persistence.EntityNotFoundException;
import javax.validation.Valid;

@Controller
@RequestMapping("/admin/users")
public class UsersCRUDController {

    private final UserService userService;

    public UsersCRUDController(UserService userService) {
        this.userService = userService;
    }

    // POKAŻ WSZYSTKIE
    @GetMapping("/all")     // w przeglądarce
    public String showAll(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "/usersAll";  // nazwa pliku
    }

    // DODAWANIE
    @GetMapping(value = "/add")
    public String showAddForm(Model model) {
        model.addAttribute("user", new User());
        return "/userAdd";
    }

    @PostMapping(value = "/add")
    public String saveUser(@Valid User user, BindingResult result) { // @Valid wymusza proces walidacji określony w modelu user
        if (result.hasErrors()) {
            return "/userAdd";
        }
        userService.addUser(user);
        return "redirect:/admin/users/all";
    }

    // WYŚWIETL detale jednej PO ID

    @GetMapping("/show/{id}")
    public String showUser(Model model, @PathVariable Long id) {
        //stworzenie obiektu, ponieważ met. get() z UserController daje Optional, a nie obiekt. Optional utrudnia pracę z jsp
        User user = userService.getUser(id).orElseThrow(() -> new EntityNotFoundException("User " + id + " not found"));
        model.addAttribute("user", user);
        return "/showOne";
    }

    // EDYCJA
    @GetMapping(value = "/edit/{id}")
    public String showEditForm(@PathVariable Long id, Model model) {
        model.addAttribute("user", userService.getUser(id));
        return "/userEdit";
    }

    @PostMapping(value = "/edit")
    public String editUser(@Valid User user, BindingResult result) {
        if (result.hasErrors()) {
            return "/userEdit";
        }
        userService.addUser(user);
        return "redirect:/admin/users/all";
    }


    // KASOWANIE
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable Long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users/all";
    }
}
