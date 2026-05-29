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
    public Usuario buscaUsuarioPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Usuário com e-mail: " + email + " não encontrado!")
        );
    }

    public UsuarioDTO buscarUsuarioPorEmail(String email) {
        Usuario usuario = buscaUsuarioPorEmail(email);
        return usuarioConverter.paraUsuarioDTO(usuario);
    }

    public Usuario criptografaSenha(UsuarioDTO usuarioDTO) {
        Usuario usuario = usuarioConverter.paraUsuario(usuarioDTO);
        String senhaOriginal = usuarioDTO.getSenha();
        usuario.setSenha(passwordEncoder.encode(senhaOriginal));
        return usuario;
    }

    public UsuarioDTO deletarUsuarioPorEmail(String email) {
        UsuarioDTO usuarioDTO = buscarUsuarioPorEmail(email);
        usuarioRepository.deleteByEmail(email);
        return usuarioDTO;
    }

    public Usuario atualizaDadosUsuario(String token, AtualizacaoUsuarioDTO atualizacaoUsuarioDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario entity = buscaUsuarioPorEmail(email);
        Usuario usuario = usuarioConverter.usuarioAtualizado(atualizacaoUsuarioDTO, entity);
        if (atualizacaoUsuarioDTO.getSenha() != null && !atualizacaoUsuarioDTO.getSenha().isBlank()) {
            String senhaCriptografada = passwordEncoder.encode(atualizacaoUsuarioDTO.getSenha());
            usuario.setSenha(senhaCriptografada);
        }
        return usuario;
    }

    // ==> Endereco Section
    public Endereco buscarEnderecoPorId(Long enderecoId) {
        return enderecoRepository.findById(enderecoId).orElseThrow(
                () -> new ResourceNotFoundException("Id: " + enderecoId + " não encontrado!")
        );
    }

    public Endereco atualizaEndereco(Long enderecoId, AtualizacaoEnderecoDTO atualizacaoEnderecoDTO) {
        Endereco entity = buscarEnderecoPorId(enderecoId);
        Endereco endereco = usuarioConverter.enderecoAtualizado(atualizacaoEnderecoDTO, entity);
        return endereco;
    }

    public Endereco adicionaEndereco(String token, EnderecoDTO enderecoDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = buscaUsuarioPorEmail(email);
        Endereco endereco = usuarioConverter.paraAdicionarEndereco(enderecoDTO, usuario.getId());
        return endereco;
    }

    // ==> Telefone Section
    public Telefone buscarTelefonePorId(Long telefoneId) {
        return telefoneRepository.findById(telefoneId).orElseThrow(
                () -> new ResourceNotFoundException("Id: " + telefoneId + " " + "não encontrado!")
        );
    }

    public Telefone atualizaTelefone(Long telefoneId, AtualizacaoTelefoneDTO atualizacaoTelefoneDTO) {
        Telefone entity = buscarTelefonePorId(telefoneId);
        Telefone telefone = usuarioConverter.telefoneAtualizado(atualizacaoTelefoneDTO, entity);
        return telefone;
    }

    public Telefone adicionaTelefone(String token, TelefoneDTO telefoneDTO) {
        String email = jwtUtil.extractUsername(token.substring(7));
        Usuario usuario = buscaUsuarioPorEmail(email);
        Telefone telefone = usuarioConverter.paraAdicionarTelefone(telefoneDTO, usuario.getId());
        return telefone;
    }
}
