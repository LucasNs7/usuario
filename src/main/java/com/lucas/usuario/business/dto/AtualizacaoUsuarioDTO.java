package com.lucas.usuario.business.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtualizacaoUsuarioDTO {

    @Size(min = 3, max = 150, message = "O nome deve conter entre {min} e {max} caracteres")
    @Pattern(
            regexp = "^(?!\\s*$)[A-Za-zÀ-ÖØ-öø-ÿ\\s]+$",
            message = "O nome deve conter apenas letras e espaços, e não pode ser composto apenas por espaços"
    )
    private String nome;

    @Email(message = "Email inválido")
    private String email;

    @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
            message = "Senha deve conter maiúscula, minúscula e número"
    )
    private String senha;

    @Valid
    private List<EnderecoDTO> enderecos;

    @Valid
    private List<TelefoneDTO> telefones;
}
