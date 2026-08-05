package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.usuario.UsuarioCadastroDTO;
import com.escola.escola_api.controller.dto.usuario.UsuarioPesquisaDTO;
import com.escola.escola_api.model.entity.Usuario;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    Usuario toEntity(UsuarioCadastroDTO dto);
    UsuarioPesquisaDTO toDTO(Usuario entity);
}
