package br.com.barbosa.controllers;

import br.com.barbosa.dtos.AuthRequestDTO;
import br.com.barbosa.dtos.AuthResponseDTO;
import br.com.barbosa.services.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/auth")
public class AuthController {

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDTO> login(@RequestBody AuthRequestDTO authRequest) {
        AuthResponseDTO authResponse = authService.authenticate(authRequest);
        return ResponseEntity.ok(authResponse);
    }

}