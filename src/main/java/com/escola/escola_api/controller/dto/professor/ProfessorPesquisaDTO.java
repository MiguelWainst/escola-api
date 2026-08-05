package com.escola.escola_api.controller.dto.professor;

import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record ProfessorPesquisaDTO(
        String nome,
        String email,
        LocalDate dataNascimento,
        String cpf,
        LocalDate dataContratacao,
        LocalDateTime dataAtualizacao,
        String usuarioAtualizacao,
        List<CursoResumoDTO> cursos
) {
}
