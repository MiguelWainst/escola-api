package com.escola.escola_api.service;

import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.model.entity.Professor;
import com.escola.escola_api.repository.CursoRepository;
import com.escola.escola_api.repository.ProfessorRepository;
import com.escola.escola_api.security.SecurityService;
import com.escola.escola_api.validator.ProfessorValidator;
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

    @Transactional
    public void salvar(Professor professor, List<UUID> idCursos) {
        validator.validar(professor);
        if (idCursos != null && !idCursos.isEmpty()) {
            List<Curso> cursosEncontrados = cursoRepository.findAllById(idCursos);
            professor.setCursos(cursosEncontrados);
        }
        professor.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        professorRepository.save(professor);
    }

    public List<Professor> obterTodos() {
        return professorRepository.findAll();
    }

    public Optional<Professor> obterPorMatricula(Integer matricula) {
        return professorRepository.findByMatricula(matricula);
    }
}
