package com.lucas.usuario.business.Helper;

import com.lucas.usuario.business.converter.UsuarioConverter;
import com.lucas.usuario.business.dto.*;
import com.lucas.usuario.infrastructure.entity.Endereco;
import com.lucas.usuario.infrastructure.entity.Telefone;
import com.lucas.usuario.infrastructure.entity.Usuario;
import com.lucas.usuario.infrastructure.exceptions.ConflictException;
import com.lucas.usuario.infrastructure.exceptions.ResourceNotFoundException;
import com.lucas.usuario.infrastructure.repository.EnderecoRepository;
import com.lucas.usuario.infrastructure.repository.TelefoneRepository;
import com.lucas.usuario.infrastructure.repository.UsuarioRepository;
import com.lucas.usuario.infrastructure.security.JwtUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ServiceHelper {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public void existsEmail(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new ConflictException("Email: " + email + " já foi cadastrado!");
        }
    }

    // ==> Usuario Section
    public Usuario buscaPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Usuário com e-mail: " + email + " não encontrado!")
        );
    }

    public UsuarioDTO buscarPorEmail(String email) {
        Usuario usuario = buscaPorEmail(email);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    // TODO: Refatorar
    public Usuario criptografaSenha(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        String senhaOriginal = usuarioDTO.getSenha();
        String hash = passwordEncoder.encode(senhaOriginal);
        usuario.setSenha(hash);
        return usuario;
    }

    // TODO: Refatorar
    public UsuarioDTO deletarUsuarioPorEmail(String email) {
        UsuarioDTO usuarioDTO = buscarPorEmail(email);
        usuarioRepository.deleteByEmail(email);
        return usuarioDTO;
    }

    // TODO: Refatorar para private
    public Usuario atualizaDadosUsuario(String token, AtualizacaoUsuarioDTO atualizacaoUsuarioDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario entity = buscaPorEmail(email);
        Usuario usuario = usuarioConverter.usuarioAtualizado(atualizacaoUsuarioDTO, entity);
        if (atualizacaoUsuarioDTO.getSenha() != null && !atualizacaoUsuarioDTO.getSenha().isBlank()) {
            String senhaCriptografada = passwordEncoder.encode(atualizacaoUsuarioDTO.getSenha()); // Use o seu encoder aqui
            usuario.setSenha(senhaCriptografada);
        }
        return usuario;
    }

    // ==> Endereco Section
    // TODO: Refatorar para EnderecoDTO
    public Endereco buscarEnderecoPorId(Long enderecoId) {
        return enderecoRepository.findById(enderecoId).orElseThrow(
                () -> new ResourceNotFoundException("Id: " + enderecoId + " não encontrado!")
        );
    }

    // TODO: Refatorar para EnderecoDTO
    public Endereco atualizaEndereco(Long enderecoId, AtualizacaoEnderecoDTO atualizacaoEnderecoDTO) {
        Endereco entity = buscarEnderecoPorId(enderecoId);
        Endereco endereco = usuarioConverter.enderecoAtualizado(atualizacaoEnderecoDTO, entity);
        return endereco;
    }

    // ==> Telefone Section
    // TODO: Refatorar para TelefoneDTO
    public Telefone buscarTelefonePorId(Long telefoneId) {
        return telefoneRepository.findById(telefoneId).orElseThrow(
                () -> new ResourceNotFoundException("Id: " + telefoneId + " " + "não encontrado!")
        );
    }

    // TODO: Refatorar para TelefoneDTO
    public Telefone atualizaTelefone(Long telefoneId, AtualizacaoTelefoneDTO atualizacaoTelefoneDTO) {
        Telefone entity = buscarTelefonePorId(telefoneId);
        Telefone telefone = usuarioConverter.telefoneAtualizado(atualizacaoTelefoneDTO, entity);
        return telefone;
    }
}
