package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.professor.ProfessorCadastroDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorPesquisaDTO;
import com.escola.escola_api.model.entity.Professor;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = {CursoMapper.class})
public interface ProfessorMapper {

    @Mapping(target = "cpf", expression = "java(dto.cpf() != null ? dto.cpf().replaceAll(\"\\\\D\", \"\") : null)")
    Professor toEntity(ProfessorCadastroDTO dto);
    ProfessorPesquisaDTO toDTO(Professor professor);
    void updateEntityFromDTO(ProfessorCadastroDTO dtoFromUser, @MappingTarget Professor entityExistente);
}
