package com.lucas.usuario.business.service;

import com.lucas.usuario.business.Helper.ServiceHelper;
import com.lucas.usuario.business.converter.UsuarioConverter;
import com.lucas.usuario.business.dto.UsuarioDTO;
import com.lucas.usuario.infrastructure.entity.Usuario;
import com.lucas.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final ServiceHelper serviceHelper;
    private final PasswordEncoder encoder;

    public UsuarioDTO criaUsuario(@NonNull UsuarioDTO usuarioDTO) {
        serviceHelper.existsEmail(usuarioDTO.getEmail());

        usuarioDTO.setSenha(encoder.encode(usuarioDTO.getSenha()));

        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }
}
