package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.professor.ProfessorCadastroDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorPesquisaDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorResumoDTO;
import com.escola.escola_api.model.entity.Professor;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface ProfessorMapper {

    @Mapping(target = "cursos", ignore = true)
    Professor toEntity(ProfessorCadastroDTO dto);
    @Mapping(
            target = "cursos",
            expression = "java(professor.getCursos() != null ? professor.getCursos().stream().map(Curso::getId).toList() : null)"
    )
    ProfessorResumoDTO toResumoDTO(Professor professor);
    ProfessorPesquisaDTO toDTO(Professor professor);
    @Mapping(target = "cursos", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(ProfessorCadastroDTO dtoFromUser, @MappingTarget Professor entityExistente);
}
