package com.escola.escola_api.controller.dto.aluno;

import com.escola.escola_api.validator.DominioEmailValido;
import jakarta.validation.constraints.*;

import java.time.LocalDate;
import java.util.UUID;

public record AlunoCadastroDTO(
        @NotBlank
        @Size(max = 100, min = 2, message = "O nome deve ter entre 2 e 70 caracteres")
        String nome,
        @Email
        @DominioEmailValido(message = "O domínio do email é inválido")
        @NotBlank(message = "O email é obrigatório")
        String email,
        @Past(message = "A data de nascimento deve ser uma data passada")
        LocalDate dataNascimento,
//        @CPF(message = "O CPF é inválido")
        @NotBlank(message = "O CPF é obrigatório")
        @Size(max = 14, min = 11, message = "CPF deve estar entre 11 e 14 caracteres")
        String cpf,
        UUID idCurso
) {
}
