package com.escola.escola_api.controller.dto.aluno;

import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;

public record AlunoResumoDTO(
        Integer matricula,
        String nome,
        String nomeCurso
) {
}
