package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.curso.CursoCadastroDTO;
import com.escola.escola_api.controller.dto.curso.CursoPesquisaDTO;
import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;
import com.escola.escola_api.model.entity.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class CursoMapper {

    @Mapping(target = "alunos", ignore = true)
    public abstract Curso toEntity(CursoCadastroDTO dto);
    @Mapping(
            target = "matriculaAlunos",
            expression = "java(curso.getAlunos() != null ? curso.getAlunos().stream().map(Aluno::getMatricula).toList() : null)"
    )
    public abstract CursoPesquisaDTO toPesquisaDTO(Curso curso);
    public abstract CursoResumoDTO toResumoDTO(Curso curso);
    public abstract void updateEntityFromDTO(CursoCadastroDTO dtoFromUser, @MappingTarget Curso entityExistente);
}
