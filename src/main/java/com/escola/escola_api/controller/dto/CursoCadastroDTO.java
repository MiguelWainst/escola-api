package com.escola.escola_api.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record CursoCadastroDTO(
        @NotBlank(message = "O nome não pode ser nulo.")
        String nome,
        Integer cargaHoras
) {
}
