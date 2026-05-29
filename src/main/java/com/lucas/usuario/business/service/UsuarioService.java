package com.lucas.usuario.business.service;

import com.lucas.usuario.business.Helper.ServiceHelper;
import com.lucas.usuario.business.converter.UsuarioConverter;
import com.lucas.usuario.business.dto.*;
import com.lucas.usuario.infrastructure.entity.Endereco;
import com.lucas.usuario.infrastructure.entity.Telefone;
import com.lucas.usuario.infrastructure.entity.Usuario;
import com.lucas.usuario.infrastructure.repository.EnderecoRepository;
import com.lucas.usuario.infrastructure.repository.TelefoneRepository;
import com.lucas.usuario.infrastructure.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final EnderecoRepository enderecoRepository;
    private final TelefoneRepository telefoneRepository;
    private final UsuarioConverter usuarioConverter;
    private final ServiceHelper serviceHelper;

    public UsuarioDTO criaUsuario(@NonNull UsuarioDTO usuarioDTO) {
        serviceHelper.existsEmail(usuarioDTO.getEmail());
        Usuario usuario = serviceHelper.criptografaSenha(usuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public UsuarioDTO buscaUsuarioPorEmail(String email) {
        return serviceHelper.buscarUsuarioPorEmail(email);
    }

    @Transactional
    public UsuarioDTO deletaUsuarioPorEmail(String email) {
        return serviceHelper.deletarUsuarioPorEmail(email);
    }

    @Transactional
    public UsuarioDTO atualizarDadosUsuario(String token, AtualizacaoUsuarioDTO atualizacaoUsuarioDTO) {
        Usuario usuario = serviceHelper.atualizaDadosUsuario(token, atualizacaoUsuarioDTO);
        return usuarioConverter.paraUsuarioDTO(usuarioRepository.save(usuario));
    }

    @Transactional
    public EnderecoDTO atualizarEndereco(Long enderecoId, AtualizacaoEnderecoDTO atualizacaoEnderecoDTO) {
        Endereco endereco = serviceHelper.atualizaEndereco(enderecoId, atualizacaoEnderecoDTO);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    @Transactional
    public EnderecoDTO adicionarEndereco(String token, EnderecoDTO enderecoDTO) {
        Endereco endereco = serviceHelper.adicionaEndereco(token, enderecoDTO);
        return usuarioConverter.paraEnderecoDTO(enderecoRepository.save(endereco));
    }

    @Transactional
    public TelefoneDTO atualizarTelefone(Long telefoneId, AtualizacaoTelefoneDTO atualizacaoTelefoneDTO) {
        Telefone telefone = serviceHelper.atualizaTelefone(telefoneId, atualizacaoTelefoneDTO);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }

    @Transactional
    public TelefoneDTO adicionarTelefone(String token, TelefoneDTO telefoneDTO) {
        Telefone telefone = serviceHelper.adicionaTelefone(token, telefoneDTO);
        return usuarioConverter.paraTelefoneDTO(telefoneRepository.save(telefone));
    }
}
