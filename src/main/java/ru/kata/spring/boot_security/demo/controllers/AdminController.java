package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.service.UserService;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("")
    public String showAllUsers(Model model) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("admin", userService.findByUsername(authentication.getName()));
        model.addAttribute("users", userService.getAllUsers());
        model.addAttribute("newUser", new User());
        //model.addAttribute("newRole", new Role());
        model.addAttribute("currentUser", userService.findByUsername(authentication.getName()));
        model.addAttribute("getAllRoles", userService.getAllRoles());

        return "allusers";

        //alltest Koch
        //adminB Bob
        //allusers my
    }

    @GetMapping("/new")
    public String showAddNewUserPage(Model model) {
        User user = new User();
        model.addAttribute("user", user);
        model.addAttribute("role", userService.getAllRoles());
        return "new";
    }

    @GetMapping("edit/{id}")
    public String showEditPage(@PathVariable("id") int id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        model.addAttribute("role", userService.getAllRoles());
        return "/edit";
    }

    @DeleteMapping(value = "/delete/{id}")
    public String delete(@PathVariable("id") int id) {
        userService.deleteUserById(id);
        return "redirect:/admin";
    }

    @PatchMapping("/edit/{id}")
    public String update(@ModelAttribute("userUpdate") User user) {
        userService.updateUser(user);
        return "redirect:/admin";
    }


    @PostMapping("/new")
    public String create(@ModelAttribute("newUser") User user) {
        userService.saveUser(user);
        return "redirect:/admin";
    }

}