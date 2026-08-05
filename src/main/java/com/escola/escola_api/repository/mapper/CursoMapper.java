package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.curso.CursoCadastroDTO;
import com.escola.escola_api.controller.dto.curso.CursoPesquisaDTO;
import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;
import com.escola.escola_api.model.entity.Curso;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring", uses = UsuarioMapper.class)
public abstract class CursoMapper {

    public abstract Curso toEntity(CursoCadastroDTO dto);
    @Mapping(
            target = "usuarioAtualizacao",
            expression = "java(curso.getUsuarioAtualizacao() != null ? curso.getUsuarioAtualizacao() : null)"
    )
    @Mapping(
            target = "matriculaAlunos",
            expression = "java(curso.getAlunos() != null? curso.getAlunos().stream().map(aluno -> aluno.getMatricula()).toList() : null)"
    )
    public abstract CursoPesquisaDTO toDTO(Curso curso);
    public abstract CursoResumoDTO toResumoDTO(Curso curso);
    public abstract void updateEntityFromDTO(CursoCadastroDTO dtoFromUser, @MappingTarget Curso entityExistente);
}
