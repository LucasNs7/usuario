package com.lucas.usuario.business.converter;

import com.lucas.usuario.business.dto.EnderecoDTO;
import com.lucas.usuario.business.dto.TelefoneDTO;
import com.lucas.usuario.business.dto.UsuarioDTO;
import com.lucas.usuario.infrastructure.entity.Endereco;
import com.lucas.usuario.infrastructure.entity.Telefone;
import com.lucas.usuario.infrastructure.entity.Usuario;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    public Usuario paraUsuario (@NonNull UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecos(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();
    }

    public List<Endereco> paraListaEnderecos (@NonNull List<EnderecoDTO> enderecosDTO) {
        return enderecosDTO.stream()
                .map(this::paraEndereco)
                .toList();
    }

    public Endereco paraEndereco (@NonNull EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<Telefone> paraListaTelefones (@NonNull List<TelefoneDTO> telefoneDTO) {
        return telefoneDTO.stream()
                .map(this::paraTelefone)
                .toList();
    }

    public Telefone paraTelefone (@NonNull TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .telefone(telefoneDTO.getTelefone())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public UsuarioDTO paraUsuarioDTO (@NonNull Usuario usuarioDTO) {
        return UsuarioDTO.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecosDTO(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuarioDTO.getTelefones()))
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecosDTO (@NonNull List<Endereco> enderecosDTO) {
        return enderecosDTO.stream()
                .map(this::paraEnderecoDTO)
                .toList();
    }

    public EnderecoDTO paraEnderecoDTO (@NonNull Endereco enderecoDTO) {
        return EnderecoDTO.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO (@NonNull List<Telefone> telefoneDTO) {
        return telefoneDTO.stream()
                .map(this::paraTelefoneDTO)
                .toList();
    }

    public TelefoneDTO paraTelefoneDTO (@NonNull Telefone telefoneDTO) {
        return TelefoneDTO.builder()
                .telefone(telefoneDTO.getTelefone())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public Usuario usuarioAtualizado(UsuarioDTO usuarioDTO, Usuario entity) {
        if (usuarioDTO.getNome() != null) {
            entity.setNome(usuarioDTO.getNome());
        }
        if (usuarioDTO.getSenha() != null) {
            entity.setSenha(usuarioDTO.getSenha());
        }
        if (usuarioDTO.getEmail() != null) {
            entity.setEmail(usuarioDTO.getEmail());
        }

        if (usuarioDTO.getEnderecos() != null) {
            entity.getEnderecos().clear();

            List<Endereco> enderecosEntidade = usuarioDTO.getEnderecos()
                    .stream()
                    .map(this::paraEndereco)
                    .toList();

            entity.getEnderecos().addAll(enderecosEntidade);
        }

        if (usuarioDTO.getTelefones() != null) {
            entity.getTelefones().clear();

            List<Telefone> telefonesEntidade = usuarioDTO.getTelefones()
                    .stream()
                    .map(this::paraTelefone)
                    .toList();

            entity.getTelefones().addAll(telefonesEntidade);
        }

        return entity;
    }
}
