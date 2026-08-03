package com.escola.escola_api.controller.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record ProfessorPesquisaDTO(
        String nome,
        String email,
        LocalDate dataNascimento,
        String cpf,
        LocalDate dataContratacao,
        LocalDateTime dataAtualizacao,
        String usuarioAtualizacao,
        List<UUID> id_curso
) {
}
