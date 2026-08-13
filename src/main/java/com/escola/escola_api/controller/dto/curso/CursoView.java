package com.escola.escola_api.controller.dto.curso;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        oneOf = {
                CursoResumoDTO.class,
                CursoPesquisaDTO.class
        }
)
public interface CursoView {
}
