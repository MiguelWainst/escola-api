package com.escola.escola_api.controller.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CursoCadastroDTO(
        @NotBlank(message = "O nome não pode ser nulo.")
        @Size(max = 70, message = "O nome não pode ter mais de 70 caracteres")
        String nome,
        @Size(min = 0, max = 1000, message = "A carga horária não pode ser negativa")
        Integer cargaHoras
) {
}
