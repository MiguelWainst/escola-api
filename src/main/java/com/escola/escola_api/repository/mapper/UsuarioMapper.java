package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.UsuarioDTO;
import com.escola.escola_api.model.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioDTO dto);
    UsuarioDTO toDTO(Usuario entity);
}
