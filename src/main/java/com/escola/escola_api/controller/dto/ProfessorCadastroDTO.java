package com.escola.escola_api.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProfessorCadastroDTO(
        @NotBlank(message = "O nome é obrigatório")
        @Size(max = 70, message = "O nome não pode ter mais de 70 caracteres")
        String nome,
        @NotBlank(message = "O email é obrigatório")
        String email,
        LocalDate dataNascimento,
        String cpf,
        List<UUID> idCurso
) {
}
