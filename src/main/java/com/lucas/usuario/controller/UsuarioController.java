package com.lucas.usuario.controller;

import com.lucas.usuario.business.Helper.ControllerHelper;
import com.lucas.usuario.business.dto.*;
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
    public String login(@RequestBody LoginDTO loginDTO) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @GetMapping
    public ResponseEntity<?> buscarUsuarioPorEmail(@RequestParam("email") @Valid String email) {
        return controllerHelper.tryCatchFunction(
                () -> usuarioService.buscaUsuarioPorEmail(email),
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @DeleteMapping
    public ResponseEntity<?> deletarUsuarioPorEmail(@RequestParam("email") @Valid String email) {
        return controllerHelper.tryCatchFunction(
                () -> usuarioService.deletaUsuarioPorEmail(email),
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @PutMapping
    public ResponseEntity<?> atualizarUsuario(@RequestBody @Valid AtualizacaoUsuarioDTO atualizacaoUsuarioDTO,
                                              @RequestHeader("Authorization") @Valid String token) {
        return controllerHelper.tryCatchFunction(
                () -> usuarioService.atualizarDadosUsuario(token, atualizacaoUsuarioDTO),
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    // ==> Endereco Section
    @PostMapping("/address")
    public ResponseEntity<?> adicionarEndereco(@RequestBody @Valid EnderecoDTO enderecoDTO,
                                               @RequestHeader("Authorization")  @Valid String token) {
        return controllerHelper.tryCatchFunction(
                () -> usuarioService.adicionarEndereco(token, enderecoDTO),
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("/address")
    public ResponseEntity<?> atualizarEndereco(@RequestBody @Valid AtualizacaoEnderecoDTO atualizacaoEnderecoDTO,
                                               @RequestParam("id") @Valid Long id) {
        return controllerHelper.tryCatchFunction(
                () -> usuarioService.atualizarEndereco(id, atualizacaoEnderecoDTO),
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    // ==> Telefone Section
    @PostMapping("/telephone")
    public ResponseEntity<?> adicionarTelefone(@RequestBody @Valid TelefoneDTO telefoneDTO,
                                               @RequestHeader("Authorization") @Valid String token) {
        return controllerHelper.tryCatchFunction(
                () -> usuarioService.adicionarTelefone(token, telefoneDTO),
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }

    @PutMapping("/telephone")
    public ResponseEntity<?> atualizarTelefone(@RequestBody @Valid AtualizacaoTelefoneDTO atualizacaoTelefoneDTO,
                                               @RequestParam("id") @Valid Long id) {
        return controllerHelper.tryCatchFunction(
                () -> usuarioService.atualizarTelefone(id, atualizacaoTelefoneDTO),
                ResourceNotFoundException.class,
                HttpStatus.NOT_FOUND
        );
    }
}
