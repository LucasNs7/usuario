package com.lucas.usuario.business.Helper;

import com.lucas.usuario.business.converter.UsuarioConverter;
import com.lucas.usuario.business.dto.UsuarioDTO;
import com.lucas.usuario.infrastructure.entity.Usuario;
import com.lucas.usuario.infrastructure.exceptions.ConflictException;
import com.lucas.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.lucas.usuario.infrastructure.repository.UsuarioRepository;
import com.lucas.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceHelper {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

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

    public Usuario buscarPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Email: " + email + " não encontrado!"));
    }

    public Usuario criptografaSenha(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        String senhaOriginal = usuarioDTO.getSenha();
        String hash = passwordEncoder.encode(senhaOriginal);
        usuario.setSenha(hash);

        return usuario;
    }

    public Usuario atualizaDadosUsuario(String token, UsuarioDTO usuarioDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario entity = buscarPorEmail(email);
        Usuario usuario = usuarioConverter.usuarioAtualizado(usuarioDTO, entity);
        if (usuarioDTO.getSenha() != null) {
            return criptografaSenha(usuarioConverter.paraUsuarioDTO(usuario));
        }
        return usuario;
    }
}
