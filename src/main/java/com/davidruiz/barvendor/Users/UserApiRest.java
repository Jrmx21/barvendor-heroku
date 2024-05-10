package com.davidruiz.barvendor.Users;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserApiRest {
 @Autowired
    private IUserRepository usuarioRepository;
    @Autowired
    private UserService userService;
    @PostMapping("/login")
    public ResponseEntity<UserModel> login(@RequestBody UserModel usuario) {
        UserModel usuarioEncontrado = usuarioRepository.findByUsernameAndPassword(usuario.getUsername(), usuario.getPassword());
        if (usuarioEncontrado != null && usuarioEncontrado.getRole()==UserModel.Role.Camarero) {
            System.out.println("Usuario autenticado: " + usuarioEncontrado);
            return ResponseEntity.ok(usuarioEncontrado);
        } else {
            // Usuario no autenticado o no tiene el rol adecuado
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }
    }
       @GetMapping("/api/users")
    public ResponseEntity<List<UserModel>> getAllUsers() {
        List<UserModel> users = userService.getUsers();
        return ResponseEntity.ok(users);
    }
    
}
