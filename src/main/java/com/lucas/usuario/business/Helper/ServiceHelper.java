package com.lucas.usuario.business.Helper;

import com.lucas.usuario.infrastructure.exceptions.ConflictException;
import com.lucas.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceHelper {

    private final UsuarioRepository usuarioRepository;

    public void existsEmail(String email) {
        try {
            boolean existe = verificaEmailExistente(email);

            if (existe) {
                throw new ConflictException("Email: " + email + " já foi cadastrado!");
            }
        } catch (Exception e) { throw new ConflictException("Email existente!", e.getCause()); }
    }

    public boolean verificaEmailExistente(String email) {
        return usuarioRepository.existsByEmail(email);
    }
}
