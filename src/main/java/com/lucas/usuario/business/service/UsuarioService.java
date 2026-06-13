package com.lucas.usuario.business.service;

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
import org.jspecify.annotations.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final AuthenticationManager authenticationManager;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioRepository usuarioRepository;
    private final UsuarioConverter usuarioConverter;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    // ==> Úteis
    @Transactional(readOnly = true)
    private void existsEmail(String email) {
        if (usuarioRepository.existsByEmail(email)) {
            throw new ConflictException("Email: " + email + " já foi cadastrado!");
        }
    }

    @Transactional(readOnly = true)
    private Usuario buscaEntityPorEmail(String email) {
        return usuarioRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("Usuário com e-mail: " + email + " não encontrado!")
        );
    }

    @Transactional
    private Usuario criptografaSenha(Usuario usuario) {
        usuario.setSenha(passwordEncoder.encode(usuario.getSenha()));
        return usuario;
    }

    @Transactional(readOnly = true)
    private Endereco buscarEnderecoPorId(Long enderecoId) {
        return enderecoRepository.findById(enderecoId).orElseThrow(
                () -> new ResourceNotFoundException("Id: " + enderecoId + " não encontrado!")
        );
    }

    @Transactional(readOnly = true)
    private Telefone buscarTelefonePorId(Long telefoneId) {
        return telefoneRepository.findById(telefoneId).orElseThrow(
                () -> new ResourceNotFoundException("Id: " + telefoneId + " " + "não encontrado!")
        );
    }

    private String pegaEmail(String token){
        return jwtUtil.extractUsername(token.substring(7));
    }

    // ==> Service
    @Transactional
    public UsuarioDTO criaUsuario(@NonNull UsuarioDTO usuarioDTO) {
        existsEmail(usuarioDTO.getEmail());
        Usuario usuario = criptografaSenha(usuarioConverter.paraUsuario(usuarioDTO));
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    public String autenticarUsuario(LoginDTO loginDTO){
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDTO.getEmail(), loginDTO.getSenha())
        );
        return "Bearer " + jwtUtil.generateToken(authentication.getName());
    }

    @Transactional
    public UsuarioDTO buscaUsuarioPorEmail(String email) {
        return usuarioConverter.paraUsuarioDTO(buscaEntityPorEmail(email));
    }

    @Transactional
    public UsuarioDTO deletaUsuarioPorEmail(String email) {
        UsuarioDTO dto = buscaUsuarioPorEmail(email);
        usuarioRepository.deleteByEmail(email);
        return dto;
    }

    @Transactional
    public UsuarioDTO atualizarDadosUsuario(String token, AtualizacaoUsuarioDTO atualizacaoUsuarioDTO) {
        Usuario entity = buscaEntityPorEmail(pegaEmail(token));
        usuarioConverter.usuarioAtualizado(atualizacaoUsuarioDTO, entity);
        if (entity.getSenha() != null && !entity.getSenha().isBlank()) {
            entity.setSenha(passwordEncoder.encode(entity.getSenha()));
        }
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(entity));
    }

    @Transactional
    public EnderecoDTO atualizarEndereco(Long enderecoId, AtualizacaoEnderecoDTO atualizacaoEnderecoDTO) {
        Endereco entity = buscarEnderecoPorId(enderecoId);
        usuarioConverter.enderecoAtualizado(atualizacaoEnderecoDTO, entity);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(entity));
    }

    @Transactional
    public EnderecoDTO adicionarEndereco(String token, EnderecoDTO enderecoDTO) {
        Usuario usuario = buscaEntityPorEmail(pegaEmail(token));
        Endereco endereco = usuarioConverter.paraAdicionarEndereco(enderecoDTO, usuario.getId());
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    @Transactional
    public TelefoneDTO atualizarTelefone(Long telefoneId, AtualizacaoTelefoneDTO atualizacaoTelefoneDTO) {
        Telefone entity = buscarTelefonePorId(telefoneId);
        usuarioConverter.telefoneAtualizado(atualizacaoTelefoneDTO, entity);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(entity));
    }

    @Transactional
    public TelefoneDTO adicionarTelefone(String token, TelefoneDTO telefoneDTO) {
        Usuario usuario = buscaEntityPorEmail(pegaEmail(token));
        Telefone telefone = usuarioConverter.paraAdicionarTelefone(telefoneDTO, usuario.getId());
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
