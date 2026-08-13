package com.escola.escola_api.controller.dto.professor;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
        oneOf = {
                ProfessorResumoDTO.class,
                ProfessorPesquisaDTO.class
        }
)
public interface ProfessorView {
}
