package com.lucas.usuario.business.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtualizacaoEnderecoDTO {

    @Positive(message = "O ID informado deve ser maior que zero")
    private Long id;

    @Size(min = 3, max = 200, message = "Rua deve ter entre 3 e 200 caracteres")
    private String rua;

    @Min(value = 1, message = "Número deve ser maior que zero")
    private Integer numero;

    @Size(max = 100, message = "Complemento muito grande")
    private String complemento;

    @Size(min = 2, max = 150, message = "Cidade deve ter entre 2 e 150 caracteres")
    private String cidade;

    @Pattern(
            regexp = "^[A-Z]{2}$",
            message = "Estado deve ser na sigla UF"
    )
    private String estado;

    @Pattern(
            regexp = "^\\d{5}-?\\d{3}$",
            message = "CEP inválido"
    )
    private String cep;
}
