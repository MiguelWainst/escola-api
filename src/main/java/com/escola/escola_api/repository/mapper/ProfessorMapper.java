package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.professor.ProfessorCadastroDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorPesquisaDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorResumoDTO;
import com.escola.escola_api.model.entity.Professor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CursoMapper.class})
public interface ProfessorMapper {

    Professor toEntity(ProfessorCadastroDTO dto);

    @Mapping(
            target = "cursos",
            expression = "java(professor.getCursos() != null ? professor.getCursos().stream().map(Curso::getId).toList() : null)"
    )
    ProfessorResumoDTO toResumoDTO(Professor professor);
    ProfessorPesquisaDTO toDTO(Professor professor);
    void updateEntityFromDTO(ProfessorCadastroDTO dtoFromUser, @MappingTarget Professor entityExistente);
}
