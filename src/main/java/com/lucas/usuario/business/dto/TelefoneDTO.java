package com.lucas.usuario.business.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TelefoneDTO {

    private Long id;

    @NotBlank(message = "Telefone obrigatório!")
    @Pattern(
            regexp = "^\\d{8,9}$",
            message = "Telefone deve ter 8 ou 9 números"
    )
    private String telefone;

    @NotBlank(message = "DDD obrigatório!")
    @Pattern(
            regexp = "^\\d{2}$",
            message = "DDD deve ter 2 números"
    )
    private String ddd;
}
