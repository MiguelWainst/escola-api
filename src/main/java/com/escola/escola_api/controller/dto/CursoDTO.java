package com.escola.escola_api.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CursoDTO(
        String id,
        @NotBlank(message = "O nome não pode ser nulo.")
        String nome,
        Integer cargaHoras,
        String usuarioAtualizacao
) {
}
