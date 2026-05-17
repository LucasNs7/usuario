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
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final ServiceHelper serviceHelper;
    private final PasswordEncoder encoder;

    public UsuarioDTO criaUsuario(@NonNull UsuarioDTO usuarioDTO) {
        serviceHelper.existsEmail(usuarioDTO.getEmail());

        Usuario usuario = serviceHelper.criptografaSenha(usuarioDTO);

        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioDTO buscaUsuarioPorEmail(String email) {
        return usuarioConverter.paraUsuarioDTO(serviceHelper.buscarPorEmail(email));
    }

    @Transactional
    public UsuarioDTO deletaUsuarioPorEmail(String email) {
        UsuarioDTO usuarioDto = buscaUsuarioPorEmail(email);
        usuarioRepository.deleteByEmail(email);
        return usuarioDto;
    }
}
