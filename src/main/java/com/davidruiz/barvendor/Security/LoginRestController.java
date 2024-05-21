package com.davidruiz.barvendor.Security;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.davidruiz.barvendor.Users.UserModel;
import com.davidruiz.barvendor.Users.UserService;

@RestController
public class LoginRestController {

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

   @PostMapping("/api/login")
public ResponseEntity<?> login(@RequestBody UserModel userModel) {
    Optional<UserModel> optionalUser = userService.findByUsername(userModel.getUsername());
    if (optionalUser.isEmpty() || !passwordEncoder.matches(userModel.getPassword(), optionalUser.get().getPassword())) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales inválidas");
    }
    // Cambia este mensaje a un objeto JSON
    return ResponseEntity.ok().body("{\"message\": \"Login exitoso\"}");
}

}
