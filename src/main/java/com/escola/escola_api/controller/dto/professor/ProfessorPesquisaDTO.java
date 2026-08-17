package com.escola.escola_api.controller.dto.professor;

import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

public record ProfessorPesquisaDTO(
        Integer matricula,
        String nome,
        String email,
        LocalDate dataNascimento,
        String cpf,
        LocalDate dataContratacao,
        String usuarioAtualizacao,
        List<CursoResumoDTO> cursos,
        UUID idUsuario
) implements ProfessorView {
}
