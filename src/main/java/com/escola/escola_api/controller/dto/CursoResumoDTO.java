package com.escola.escola_api.controller.dto;

import java.util.UUID;

public record CursoResumoDTO(
        UUID id,
        String nome,
        Integer cargaHoras
) {
}
