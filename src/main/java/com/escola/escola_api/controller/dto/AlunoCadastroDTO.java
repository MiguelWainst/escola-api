package com.escola.escola_api.controller.dto;

import java.time.LocalDate;
import java.util.UUID;

public record AlunoCadastroDTO(
        String nome,
        String email,
        LocalDate dataNascimento,
        String cpf,
        UUID idCurso
) {
}
