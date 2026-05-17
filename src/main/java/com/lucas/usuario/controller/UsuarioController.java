package com.lucas.usuario.controller;

import com.lucas.usuario.business.Helper.ControllerHelper;
import com.lucas.usuario.business.dto.UsuarioDTO;
import com.lucas.usuario.business.service.UsuarioService;
import com.lucas.usuario.infrastructure.exceptions.ConflictException;
import com.lucas.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.lucas.usuario.infrastructure.security.JwtUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final ControllerHelper controllerHelper;
    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    @PostMapping
    public ResponseEntity<?> criarUsuario(@RequestBody @Valid UsuarioDTO usuarioDTO) {
        return controllerHelper.tryCatchFunction(
                () -> usuarioService.criaUsuario(usuarioDTO),
                ConflictException.class,
                HttpStatus.CONFLICT
        );
    }

    @PostMapping("/login")
    public String login(@RequestBody UsuarioDTO usuarioDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(usuarioDTO.getEmail(), usuarioDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<?> buscarUsuarioPorEmail(@RequestParam("email") String email) {
        return controllerHelper.tryCatchFunction(
                () -> usuarioService.buscaUsuarioPorEmail(email),
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping
    public ResponseEntity<?> deletarUsuarioPorEmail(@RequestParam("email") String email) {
        return controllerHelper.tryCatchFunction(
                () -> usuarioService.deletaUsuarioPorEmail(email),
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }
}
