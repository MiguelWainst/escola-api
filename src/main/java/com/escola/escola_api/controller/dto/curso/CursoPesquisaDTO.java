package com.escola.escola_api.controller.dto.curso;

import java.util.List;
import java.util.UUID;

public record CursoPesquisaDTO(
        String id,
        String nome,
        Integer cargaHoras,
        List<Integer> matriculaAlunos,
        UUID usuarioAtualizacao,
        String descricao,
        String capaUrl
) implements CursoView{
}
