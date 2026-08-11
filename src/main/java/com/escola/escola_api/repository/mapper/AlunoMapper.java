package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoPesquisaDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoResumoDTO;
import com.escola.escola_api.model.entity.Aluno;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    @Mapping(target = "curso", ignore = true)
    Aluno toEntity(AlunoCadastroDTO dto);
    AlunoPesquisaDTO toDTO(Aluno aluno);
    @Mapping(target = "nomeCurso", expression = "java(aluno.getCurso() != null ? aluno.getCurso().getNome() : null)")
    AlunoResumoDTO toResumoDTO(Aluno aluno);
    @Mapping(target = "curso", ignore = true)
    void updateEntityFromDTO(AlunoCadastroDTO dtoFromUser, @MappingTarget Aluno entityExistente);
}
