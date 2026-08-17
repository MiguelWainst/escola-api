package com.escola.escola_api.controller.dto.usuario;

import com.escola.escola_api.validator.ValidConst;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public record UsuarioLoginDTO(

        @NotBlank(message = "O usuário é obrigatório")
        @Size(min = ValidConst.USUARIO_MIN, max = ValidConst.USUARIO_MAX, message = "O username deve ter entre {min} e {max} caracteres")
        String username,

        @NotBlank(message = "A senha é obrigatória")
        @Size(min = ValidConst.SENHA_MIN, max = ValidConst.SENHA_MAX, message = "A senha deve ter no mínimo {min} caracteres")
        @Pattern(regexp = ".*[0-9].*", message = "A senha deve ter pelo menos um número")
        String senha
) {
}
