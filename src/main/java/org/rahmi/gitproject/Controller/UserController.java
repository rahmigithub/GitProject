package org.rahmi.gitproject.Controller;

import org.rahmi.gitproject.Entity.User;
import org.rahmi.gitproject.Service.Imp.UserServiceImp;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import java.util.List;

@Controller
@RequestMapping("/users")
public class UserController {

private final UserServiceImp userService;

    public UserController(UserServiceImp userService) {
        this.userService = userService;
    }

    @GetMapping
    public String listUsers(Model model) {
        List<User> users = userService.findAllUsers();
        model.addAttribute("users", users);
        return "user-list";
    }

    @GetMapping("/{id}")
    public String viewUser(@PathVariable Long id, Model model) {
       User user = userService.findById(id);
        model.addAttribute("user", user);
        return "user-detail";
    }
}
