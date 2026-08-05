package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.AlunoPesquisaDTO;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.repository.CursoRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

@Mapper(componentModel = "spring", uses = CursoMapper.class)
public abstract class AlunoMapper {

    @Autowired
    protected CursoRepository cursoRepository;

    @Mapping(
            target = "curso",
            expression = "java(dto.idCurso() != null ? cursoRepository.findById(dto.idCurso()).orElse(null) : null)"
    )
    public abstract Aluno toEntity(AlunoCadastroDTO dto);

    public abstract AlunoPesquisaDTO toDTO(Aluno aluno);
}
