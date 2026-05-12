package com.lucas.usuario.bussiness.service;

import com.lucas.usuario.infrastructure.Entity.Usuario;
import com.lucas.usuario.infrastructure.Exceptions.ConflictException;
import com.lucas.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {
    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder encoder;

    public Usuario criaUsuario(@NonNull Usuario usuario) {
        existsEmail(usuario.getEmail());
        usuario.setSenha(encoder.encode(usuario.getSenha()));
        return usuarioRepository.save(usuario);
    }

    public void existsEmail(String email) {
        try {
            boolean existe = verificaEmailExistente(email);
            if (existe) {
                throw new ConflictException("Email: " + email + " já cadastrado!");
            }
        } catch (Exception e) { throw new ConflictException("Email existente!", e.getCause()); }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
