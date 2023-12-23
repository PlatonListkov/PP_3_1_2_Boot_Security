package ru.kata.spring.boot_security.demo.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ru.kata.spring.boot_security.demo.models.Role;
import ru.kata.spring.boot_security.demo.models.User;
import ru.kata.spring.boot_security.demo.services.RoleService;
import ru.kata.spring.boot_security.demo.services.UserService;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private UserService userService;
    private RoleService roleService;

    public AdminController(UserService userService, RoleService roleService) {
        this.userService = userService;
        this.roleService = roleService;
    }

    @GetMapping("/users-list")
    public String getUsersPage(Model model) {
        model.addAttribute("users", userService.getAllUsers());
        return "admin/users-list";
    }

    @GetMapping("/user")
    public String showUserById(@RequestParam("id") long id, Model model) {
        model.addAttribute("user", userService.getUserById(id));
        return "admin/user";
    }

    @GetMapping("/new")
    public String newUser(Model model) {
        model.addAttribute("user", new User());
        model.addAttribute("roleSet", roleService.getAllRoles());
        model.addAttribute("selectedRoles", new ArrayList<Role>());
        return "admin/form-new";
    }

    @PostMapping("/save")
    public String saveUser(@ModelAttribute("user") @Valid User user,
                           @RequestParam("selectedRoles") List<Long> selectedRoles, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/form-new";
        }
        setRolesToUser(selectedRoles, user);
        userService.saveUser(user);
        return "redirect:/admin/users-list";
    }

    @GetMapping("/edit")
    public String editUser(@RequestParam("id") long id, Model model) {
        User user = userService.getUserById(id);
        model.addAttribute("user", user);
        model.addAttribute("roleSet", roleService.getAllRoles());
        return "admin/form-edit";
    }

    @PatchMapping("/update")
    public String updateUser(@ModelAttribute("user") @Valid User user,
                             @RequestParam("selectedRoles") List<Long> selectedRoles, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            return "admin/form-edit";
        }
        setRolesToUser(selectedRoles, user);
        userService.saveUser(user);
        return "redirect:/admin/users-list";
    }

    @DeleteMapping("/{id}")
    public String delete(@PathVariable("id") long id) {
        userService.deleteUser(id);
        return "redirect:/admin/users-list";
    }

    private void setRolesToUser(List<Long> selectedRoles, User user) {
        Set<Role> userRoles = new HashSet<>();
        for (Long roleId : selectedRoles) {
            userRoles.add(roleService.getById(roleId));
        }
        user.setRoles(userRoles);
    }
}
