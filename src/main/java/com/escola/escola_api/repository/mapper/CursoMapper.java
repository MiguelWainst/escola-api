package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.curso.CursoCadastroDTO;
import com.escola.escola_api.controller.dto.curso.CursoPesquisaDTO;
import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.UsuarioRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public abstract class CursoMapper {

    @Autowired
    protected UsuarioRepository usuarioRepository;

    @Autowired
    protected UsuarioMapper usuarioMapper;

    public abstract Curso toEntity(CursoCadastroDTO dto);
    @Mapping(
            target = "usuarioAtualizacao",
            expression = "java(curso.getUsuarioAtualizacao() != null ? usuarioMapper.toDTO(usuarioRepository.findById(curso.getUsuarioAtualizacao()).orElse(null)) : null)"
    )
    public abstract CursoPesquisaDTO toDTO(Curso curso);
    public abstract CursoResumoDTO toResumoDTO(Curso curso);
    public abstract void updateEntityFromDTO(CursoCadastroDTO dtoFromUser, @MappingTarget Curso entityExistente);
}
