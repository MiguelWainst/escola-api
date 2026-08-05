package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.UsuarioCadastroDTO;
import com.escola.escola_api.controller.dto.UsuarioPesquisaDTO;
import com.escola.escola_api.model.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioCadastroDTO dto);
    UsuarioPesquisaDTO toDTO(Usuario entity);
}
