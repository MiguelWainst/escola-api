package com.escola.escola_api.controller.dto.professor;

import java.util.List;
import java.util.UUID;

public record ProfessorResumoDTO (
        Integer matricula,
        String nome,
        List<UUID> cursos
) implements ProfessorView{
}
