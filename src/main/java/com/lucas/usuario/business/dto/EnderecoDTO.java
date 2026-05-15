package com.lucas.usuario.business.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class EnderecoDTO {

    @NotBlank(message = "Rua obrigatória!")
    @Size(min = 3, max = 200, message = "Rua deve ter entre 3 e 200 caracteres")
    private String rua;

    @Min(value = 1, message = "Número deve ser maior que zero")
    @Size(max = 10, message = "Número deve ter no máximo 10 digitos")
    private int numero;

    @Size(max = 100, message = "Complemento muito grande")
    private String complemento;

    @NotBlank(message = "Cidade obrigatória!")
    @Size(min = 2, max = 150, message = "Cidade deve ter entre 2 e 150 caracteres")
    private String cidade;

    @NotBlank(message = "Estado obrigatório!")
    @Pattern(
            regexp = "^[A-Z]{2}$",
            message = "Estado deve ser na sigla UF"
    )
    private String estado;

    @NotBlank(message = "CEP obrigatório!")
    @Pattern(
            regexp = "^\\d{5}-?\\d{3}$",
            message = "CEP inválido"
    )
    private String cep;
}
