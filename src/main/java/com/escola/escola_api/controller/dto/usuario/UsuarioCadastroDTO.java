package com.escola.escola_api.controller.dto.usuario;

import com.escola.escola_api.validator.DominioEmailValido;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioCadastroDTO(
        @NotBlank(message = "O username é obrigatório")
        @Size(min = 3, max = 30, message = "O username deve ter entre 3 e 30 caracteres")
        String username,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = 8, max = 100, message = "A senha deve ter no mínimo 8 caracteres")
        @Pattern(regexp = ".*[0-9].*", message = "A senha deve ter pelo menos um número")
        String senha,

        @NotBlank(message = "O email é obrigatório")
        @DominioEmailValido(message = "Domínio inválido")
        @Email(message = "O email deve ser válido")
        String email
) {
}
