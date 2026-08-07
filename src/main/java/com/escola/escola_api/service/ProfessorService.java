package com.escola.escola_api.service;

import com.escola.escola_api.controller.dto.professor.ProfessorCadastroDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorPesquisaDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorResumoDTO;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.model.entity.Professor;
import com.escola.escola_api.repository.CursoRepository;
import com.escola.escola_api.repository.ProfessorRepository;
import com.escola.escola_api.repository.mapper.ProfessorMapper;
import com.escola.escola_api.security.SecurityService;
import com.escola.escola_api.validator.ProfessorValidator;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorValidator validator;
    private final CursoRepository cursoRepository;
    private final SecurityService securityService;
    private final ProfessorMapper mapper;

    @Transactional
    public Professor salvar(ProfessorCadastroDTO dto) {
        Professor entity = mapper.toEntity(dto);
        entity.setCpf(limparCpf(dto.cpf()));
        validator.validar(entity);
        if (dto.idCurso() != null && !dto.idCurso().isEmpty()) {
            List<Curso> cursosEncontrados = cursoRepository.findAllById(dto.idCurso());
            if (cursosEncontrados.size() != dto.idCurso().size()){
                throw new EntityNotFoundException("1 ou mais cursos não existem.");
            }
            entity.setCursos(cursosEncontrados);
        }
        entity.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        return professorRepository.save(entity);
    }

    public List<ProfessorResumoDTO> listar() {
        return professorRepository.findAll()
                .stream()
                .map(mapper::toResumoDTO)
                .toList();
    }

    public Optional<ProfessorPesquisaDTO> obterPorMatricula(Integer matricula) {
        return professorRepository.findByMatricula(matricula).map(mapper::toDTO);
    }

    @Transactional
    public void atualizar(Integer matricula, ProfessorCadastroDTO dto) {
        Professor entity = professorRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado"));
        mapper.updateEntityFromDTO(dto, entity);
        validator.validar(entity);
        entity.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
    }

    public void deletar(Professor professor) {
        if (professor == null) {
            throw new IllegalArgumentException("O professor não pode ser nulo");
        }
        professorRepository.delete(professor);
    }

    private String limparCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }

}
