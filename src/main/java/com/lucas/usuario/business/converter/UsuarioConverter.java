package com.lucas.usuario.business.converter;

import com.lucas.usuario.business.dto.*;
import com.lucas.usuario.infrastructure.entity.Endereco;
import com.lucas.usuario.infrastructure.entity.Telefone;
import com.lucas.usuario.infrastructure.entity.Usuario;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class UsuarioConverter {

    // ==> Usuario Section
    public Usuario paraUsuario(@NonNull UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(usuarioDTO.getSenha())
                .enderecos(paraListaEnderecos(usuarioDTO.getEnderecos()))
                .telefones(paraListaTelefones(usuarioDTO.getTelefones()))
                .build();
    }

    public UsuarioDTO paraUsuarioDTO(@NonNull Usuario usuario) {
        return UsuarioDTO.builder()
                .nome(usuario.getNome())
                .email(usuario.getEmail())
                .senha(usuario.getSenha())
                .enderecos(paraListaEnderecosDTO(usuario.getEnderecos()))
                .telefones(paraListaTelefonesDTO(usuario.getTelefones()))
                .build();
    }

    public Usuario usuarioAtualizado(AtualizacaoUsuarioDTO atualizacaoUsuarioDTO, Usuario entity) {
        if (atualizacaoUsuarioDTO.getNome() != null) {
            entity.setNome(atualizacaoUsuarioDTO.getNome());
        }
        if (atualizacaoUsuarioDTO.getSenha() != null) {
            entity.setSenha(atualizacaoUsuarioDTO.getSenha());
        }
        if (atualizacaoUsuarioDTO.getEmail() != null) {
            entity.setEmail(atualizacaoUsuarioDTO.getEmail());
        }

        if (atualizacaoUsuarioDTO.getEnderecos() != null) {
            entity.getEnderecos().clear();

            List<Endereco> enderecosEntidade =
                    paraListaEnderecos(atualizacaoUsuarioDTO.getEnderecos());

            entity.getEnderecos().addAll(enderecosEntidade);
        }

        if (atualizacaoUsuarioDTO.getTelefones() != null) {
            entity.getTelefones().clear();

            List<Telefone> telefonesEntidade =
                    paraListaTelefones(atualizacaoUsuarioDTO.getTelefones());

            entity.getTelefones().addAll(telefonesEntidade);
        }

        return entity;
    }

    // ==> Endereco Section
    public List<Endereco> paraListaEnderecos(@NonNull List<EnderecoDTO> enderecosDTO) {
        return enderecosDTO.stream()
                .map(this::paraEndereco)
                .toList();
    }

    public Endereco paraEndereco(@NonNull EnderecoDTO enderecoDTO) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .build();
    }

    public List<EnderecoDTO> paraListaEnderecosDTO(@NonNull List<Endereco> enderecos) {
        return enderecos.stream()
                .map(this::paraEnderecoDTO)
                .toList();
    }

    public EnderecoDTO paraEnderecoDTO(@NonNull Endereco endereco) {
        return EnderecoDTO.builder()
                .id(endereco.getId())
                .rua(endereco.getRua())
                .numero(endereco.getNumero())
                .cidade(endereco.getCidade())
                .complemento(endereco.getComplemento())
                .cep(endereco.getCep())
                .estado(endereco.getEstado())
                .build();
    }

    public Endereco enderecoAtualizado(AtualizacaoEnderecoDTO atualizacaoEnderecoDTO,
                                        Endereco entity) {
        return Endereco.builder()
                .id(entity.getId())
                .rua(atualizacaoEnderecoDTO.getRua() != null ? atualizacaoEnderecoDTO.getRua() : entity.getRua())
                .numero(atualizacaoEnderecoDTO.getNumero() != null ? atualizacaoEnderecoDTO.getNumero() : entity.getNumero())
                .complemento(atualizacaoEnderecoDTO.getComplemento() != null ? atualizacaoEnderecoDTO.getComplemento() : entity.getCep())
                .cidade(atualizacaoEnderecoDTO.getCidade() != null ? atualizacaoEnderecoDTO.getCidade(): entity.getCidade())
                .estado(atualizacaoEnderecoDTO.getEstado() != null ? atualizacaoEnderecoDTO.getEstado() : entity.getEstado())
                .cep(atualizacaoEnderecoDTO.getCep() != null ? atualizacaoEnderecoDTO.getCep() : entity.getCep())
                .usuarioId(entity.getUsuarioId())
                .build();
    }

    public Endereco paraAdicionarEndereco(@NonNull EnderecoDTO enderecoDTO, Long usuarioId) {
        return Endereco.builder()
                .rua(enderecoDTO.getRua())
                .numero(enderecoDTO.getNumero())
                .cidade(enderecoDTO.getCidade())
                .complemento(enderecoDTO.getComplemento())
                .cep(enderecoDTO.getCep())
                .estado(enderecoDTO.getEstado())
                .usuarioId(usuarioId)
                .build();
    }

    // ==> Telefone Section
    public List<Telefone> paraListaTelefones(@NonNull List<TelefoneDTO> telefoneDTO) {
        return telefoneDTO.stream()
                .map(this::paraTelefone)
                .toList();
    }

    public Telefone paraTelefone(@NonNull TelefoneDTO telefoneDTO) {
        return Telefone.builder()
                .telefone(telefoneDTO.getTelefone())
                .ddd(telefoneDTO.getDdd())
                .build();
    }

    public List<TelefoneDTO> paraListaTelefonesDTO(@NonNull List<Telefone> telefone) {
        return telefone.stream()
                .map(this::paraTelefoneDTO)
                .toList();
    }

    public TelefoneDTO paraTelefoneDTO(@NonNull Telefone telefone) {
        return TelefoneDTO.builder()
                .id(telefone.getId())
                .telefone(telefone.getTelefone())
                .ddd(telefone.getDdd())
                .build();
    }

    public Telefone telefoneAtualizado(AtualizacaoTelefoneDTO atualizacaoTelefoneDTO,
                                       Telefone entity) {
        return Telefone.builder()
                .id(entity.getId())
                .telefone(atualizacaoTelefoneDTO.getTelefone() != null ? atualizacaoTelefoneDTO.getTelefone() : entity.getTelefone())
                .ddd(atualizacaoTelefoneDTO.getDdd() != null ? atualizacaoTelefoneDTO.getDdd() : entity.getDdd())
                .build();
    }

    public Telefone paraAdicionarTelefone(@NonNull TelefoneDTO telefoneDTO, Long usuarioId) {
        return Telefone.builder()
                .telefone(telefoneDTO.getTelefone())
                .ddd(telefoneDTO.getDdd())
                .usuarioId(usuarioId)
                .build();
    }
}
