package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.CursoDTO;
import com.escola.escola_api.model.entity.Curso;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CursoMapper {

    Curso toEntity(CursoDTO dto);
    CursoDTO toDTO(Curso curso);
}
