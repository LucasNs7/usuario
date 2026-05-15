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
public class UsuarioDTO {

    @NotBlank(message = "Nome Obrigatório!")
    @Size(min = 2, max = 100, message = "Nome inválido")
    private String nome;

    @NotBlank(message = "Email obrigatório!")
    @Email(message = "Email inválido")
    private String email;

    @NotBlank(message = "Senha obrigatória!")
    @Size(min = 8, message = "Senha deve ter pelo menos 8 caracteres")
    @Pattern(
            regexp = "^(?=.*[A-Z])(?=.*[a-z])(?=.*\\d).+$",
            message = "Senha deve conter maiúscula, minúscula e número"
    )
    private String senha;

    @NotEmpty(message = "Informe pelo menos um endereço")
    @Valid
    private List<EnderecoDTO> enderecos;

    @NotEmpty(message = "Informe pelo menos um telefone")
    @Valid
    private List<TelefoneDTO> telefones;
}
