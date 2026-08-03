package com.escola.escola_api.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProfessorCadastroDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Max(value = 70, message = "O nome não pode ter mais de 70 caracteres")
        String nome,
        @Email
        @NotBlank(message = "O email é obrigatório")
        String email,
        LocalDate dataNascimento,
        @CPF(message = "O CPF é obrigatório")
        String cpf,
        List<UUID>id_curso
) {
}
