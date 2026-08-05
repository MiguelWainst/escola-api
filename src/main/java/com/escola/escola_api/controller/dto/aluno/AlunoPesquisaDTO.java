package com.escola.escola_api.controller.dto.aluno;

import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;

public record AlunoPesquisaDTO(
        String matricula,
        String nome,
        String email,
        String cpf,
        CursoResumoDTO curso
) {
}
