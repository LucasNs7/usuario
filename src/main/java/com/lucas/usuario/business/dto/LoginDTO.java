package com.lucas.usuario.business.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginDTO {

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
}
