package com.escola.escola_api.controller.dto.aluno;

import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;

import java.util.UUID;

public record AlunoPesquisaDTO(
        Integer matricula,
        String nome,
        String email,
        String cpf,
        CursoResumoDTO curso,
        UUID idUsuario
) implements AlunoView {
}
