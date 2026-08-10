package com.escola.escola_api.controller.dto.curso;

import jakarta.validation.constraints.*;

import java.util.List;

public record CursoCadastroDTO(
        @NotBlank(message = "O nome não pode ser nulo.")
        @Size(max = 70, message = "O nome não pode ter mais de 70 caracteres")
        String nome,
        @NotNull(message = "A carga horária é obrigatória")
        @Min(value = 1, message = "A carga horária deve ser de no mínimo 1 hora")
        @Max(value = 1000, message = "A carga horária não pode ultrapassar 1000 horas")
        Integer cargaHoras
) {
}
