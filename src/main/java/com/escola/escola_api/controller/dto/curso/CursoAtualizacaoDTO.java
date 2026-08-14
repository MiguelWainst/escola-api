package com.escola.escola_api.controller.dto.curso;

import com.escola.escola_api.validator.ValidConst;
import jakarta.validation.constraints.*;

public record CursoAtualizacaoDTO(
        @Size(max = ValidConst.NOME_MAX_CURSO, min = ValidConst.NOME_MIN_CURSO, message = "O nome deve ter entre {min} e {max} caracteres")
        String nome,
        @Min(value = ValidConst.CARGA_HORA_MIN, message = "A carga horária deve ser de no mínimo {value} hora")
        @Max(value = ValidConst.CARGA_HORA_MAX, message = "A carga horária não pode ultrapassar {value} horas")
        Integer cargaHoras
) {
}
