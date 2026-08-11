package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.curso.CursoCadastroDTO;
import com.escola.escola_api.controller.dto.curso.CursoPesquisaDTO;
import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;
import com.escola.escola_api.model.entity.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    Curso toEntity(CursoCadastroDTO dto);
    @Mapping(
            target = "matriculaAlunos",
            expression = "java(curso.getAlunos() != null ? curso.getAlunos().stream().map(aluno -> aluno.getMatricula()).toList() : null)"
    )
    CursoPesquisaDTO toPesquisaDTO(Curso curso);
    CursoResumoDTO toResumoDTO(Curso curso);
    void updateEntityFromDTO(CursoCadastroDTO dtoFromUser, @MappingTarget Curso entityExistente);
}
