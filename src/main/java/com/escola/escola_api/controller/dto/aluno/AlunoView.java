package com.escola.escola_api.controller.dto.aluno;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        oneOf = {
                AlunoResumoDTO.class,
                AlunoPesquisaDTO.class
        }
)
public interface AlunoView {
}
