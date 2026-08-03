package com.escola.escola_api.controller.dto;

import jakarta.validation.constraints.NotBlank;

import java.time.LocalDate;

public record ProfessorCadastroDTO(
        @NotBlank(message = "O nome é obrigatório")
        String nome,
        @NotBlank(message = "O email é obrigatório")
        String email,
        LocalDate dataNascimento,
        String cpf
) {
}
