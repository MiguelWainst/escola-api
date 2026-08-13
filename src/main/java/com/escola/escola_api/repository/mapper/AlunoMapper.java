package com.escola.escola_api.repository.mapper;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoPesquisaDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoResumoDTO;
import com.escola.escola_api.model.entity.Aluno;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface AlunoMapper {

    @Mapping(target = "curso", ignore = true)
    Aluno toEntity(AlunoCadastroDTO dto);
    AlunoPesquisaDTO toDTO(Aluno aluno);
    @Mapping(target = "nomeCurso", expression = "java(aluno.getCurso() != null ? aluno.getCurso().getNome() : null)")
    AlunoResumoDTO toResumoDTO(Aluno aluno);
    @Mapping(target = "curso", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateEntityFromDTO(AlunoCadastroDTO dtoFromUser, @MappingTarget Aluno entityExistente);
}
