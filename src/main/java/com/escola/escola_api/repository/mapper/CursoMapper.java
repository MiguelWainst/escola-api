package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.CursoCadastroDTO;
import com.escola.escola_api.controller.dto.CursoPesquisaDTO;
import com.escola.escola_api.model.entity.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    Curso toEntity(CursoCadastroDTO dto);
    CursoPesquisaDTO toDTO(Curso curso);
    void updateEntityFromDTO(CursoCadastroDTO dtoFromUser, @MappingTarget Curso entityExistente);
}
