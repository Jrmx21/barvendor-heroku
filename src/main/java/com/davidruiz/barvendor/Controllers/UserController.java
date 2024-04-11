package com.davidruiz.barvendor.Controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.davidruiz.barvendor.Models.UserModel;
import com.davidruiz.barvendor.Services.UserService;

@Controller
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping
    public ArrayList<UserModel> getUsers() {
        return this.userService.getUsers();
    }

    @GetMapping("/listar")
    public String showUsers(Model model) {
        List<UserModel> users = userService.getUsers();
        model.addAttribute("users", users);
        return "listarUsuarios";
    }
    @PostMapping
    public UserModel saveUser(@RequestBody UserModel user) {
        return this.userService.saveUser(user);
    }

    @GetMapping(path = "/{id}")
    public Optional<UserModel> getUserById(@PathVariable Long id) {
        return this.userService.getUserById(id);
    }

    @PutMapping(path = "/{id}")
    public UserModel updateUserById(@RequestBody UserModel user, Long id) {
        return this.userService.updateUser(id, user);
    }

    @DeleteMapping(path = "/{id}")
    public String deleteById(@PathVariable("id") Long id) {
        boolean ok = this.userService.deleteUserById(id);
        if (ok) {
            return "Usuario " + id + " eliminado";
        } else {
            return "Usuario " + id + " no eliminado";
        }
    }
    @GetMapping("/crear")
    public String showCreateForm(Model model) {
        model.addAttribute("user", new UserModel());
        return "crearUsuario";
    }

    @PostMapping("/crear")
    public String createUser(@ModelAttribute("user") UserModel user) {
        userService.saveUser(user);
        return "redirect:/users/listar";
    }

    @GetMapping("/modificar/{id}")
    public String showUpdateForm(@PathVariable("id") Long id, Model model) {
        Optional<UserModel> user = userService.getUserById(id);
        if (user.isPresent()) {
            model.addAttribute("user", user.get());
            return "modificarUsuario";
        } else {
            // Manejar el caso en que el usuario no exista
            return "redirect:/users/listar";
        }
    }

    @PostMapping("/modificar/{id}")
    public String updateUser(@PathVariable("id") Long id, @ModelAttribute("user") UserModel user) {
        userService.updateUser(id, user);
        return "redirect:/users/listar";
    }

    @PostMapping("/eliminar/{id}")
    public String deleteUser(@PathVariable("id") Long id) {
        userService.deleteUserById(id);
        return "redirect:/users/listar";
    }
}
