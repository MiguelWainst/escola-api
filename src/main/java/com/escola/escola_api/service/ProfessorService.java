package com.escola.escola_api.service;

import com.escola.escola_api.configuration.RegraNegocioProperties;
import com.escola.escola_api.controller.dto.professor.ProfessorCadastroDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorPesquisaDTO;
import com.escola.escola_api.controller.dto.professor.ProfessorResumoDTO;
import com.escola.escola_api.exception.CursoJaVinculadoException;
import com.escola.escola_api.exception.CursoLotadoException;
import com.escola.escola_api.exception.ProfessorLotadoException;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final ProfessorValidator validator;
    private final CursoRepository cursoRepository;
    private final SecurityService securityService;
    private final ProfessorMapper mapper;
    private final RegraNegocioProperties regras;


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

    public ProfessorPesquisaDTO obterPorMatricula(Integer matricula) {
        return professorRepository.findByMatricula(matricula).map(mapper::toDTO)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado."));
    }

    @Transactional
    public void atualizar(Integer matricula, ProfessorCadastroDTO dto) {
        Professor entity = professorRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado."));
        mapper.updateEntityFromDTO(dto, entity);
        entity.setCpf(limparCpf(dto.cpf()));
        if (dto.idCurso() != null && !dto.idCurso().isEmpty()) {
            List<Curso> cursos = cursoRepository.findAllById(dto.idCurso());
            if (cursos.size() != dto.idCurso().size())
                throw new EntityNotFoundException("Uma ou mais cursos não foram encontrados!");
            if (cursos.stream().anyMatch(curso -> entity.getCursos().stream().anyMatch(cursoExistente -> cursoExistente.getId().equals(curso.getId()))))
                throw new CursoJaVinculadoException("Professor já está vinculado a um ou mais cursos enviados.");
            entity.getCursos().addAll(cursos);
        }
        validator.validar(entity);
        entity.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
    }

    public void deletar(Integer matricula) {
        Professor entity = professorRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado."));
        professorRepository.delete(entity);
    }

    @Transactional
    public void desvincularCurso(Integer matricula, UUID idCurso) {
        Professor professor = professorRepository.findByMatricula(matricula)
                .orElseThrow(() ->new EntityNotFoundException("Professor não encontrado."));
        boolean removido = professor.getCursos().removeIf(curso -> curso.getId().equals(idCurso));
        if (!removido) throw new EntityNotFoundException("Curso não encontrado na lista do professor.");
    }

    @Transactional
    public void vincularCurso(Integer matricula, UUID idCurso) {
        Professor professor = professorRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Professor não encontrado."));
        Curso curso = cursoRepository.findById(idCurso).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado."));
        if (professor.getCursos().contains(curso))
            throw new CursoJaVinculadoException("Professor já está vinculado a este curso.");
        if (curso.getProfessores().size() >= regras.getMaxProfessoresPorCurso())
            throw new CursoLotadoException("O curso já atingiu a capacidade máxima de professores.");
        if (professor.getCursos().size() >= regras.getMaxCursosPorProfessor())
            throw new ProfessorLotadoException();
        professor.getCursos().add(curso);
    }

    private String limparCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }

}
