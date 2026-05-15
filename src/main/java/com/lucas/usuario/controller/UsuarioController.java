package com.lucas.usuario.controller;

import com.lucas.usuario.business.dto.UsuarioDTO;
import com.lucas.usuario.business.service.UsuarioService;
import com.lucas.usuario.infrastructure.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        try {
            return ResponseEntity.ok(usuarioService.criaUsuario(usuarioDTO));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Erro no servidor");
        }
    }
}
