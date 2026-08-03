package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.ProfessorCadastroDTO;
import com.escola.escola_api.controller.dto.ProfessorPesquisaDTO;
import com.escola.escola_api.model.entity.Professor;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {CursoMapper.class})
public interface ProfessorMapper {

    Professor toEntity(ProfessorCadastroDTO dto);
    ProfessorPesquisaDTO toDTO(Professor professor);
    void updateEntityFromDTO(ProfessorCadastroDTO dtoFromUser, @org.mapstruct.MappingTarget Professor entityExistente);
}
