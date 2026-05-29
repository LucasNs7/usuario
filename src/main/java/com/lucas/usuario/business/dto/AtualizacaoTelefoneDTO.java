package com.lucas.usuario.business.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AtualizacaoTelefoneDTO {

    @Positive(message = "O ID informado deve ser maior que zero")
    private Long id;

    @Pattern(
            regexp = "^\\d{8,9}$",
            message = "Telefone deve ter 8 ou 9 números"
    )
    private String telefone;

    @Pattern(
            regexp = "^\\d{2}$",
            message = "DDD deve ter 2 números"
    )
    private String ddd;
}
