package com.escola.escola_api.service;

import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.model.entity.Professor;
import com.escola.escola_api.repository.CursoRepository;
import com.escola.escola_api.repository.ProfessorRepository;
import com.escola.escola_api.security.SecurityService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ProfessorService {

    private final ProfessorRepository professorRepository;
    private final CursoRepository cursoRepository;
    private final SecurityService securityService;

    @Transactional
    public void salvar(Professor professor, List<UUID> idCursos) {
        if (idCursos != null && !idCursos.isEmpty()) {
            List<Curso> cursosEncontrados = cursoRepository.findAllById(idCursos);
            professor.setCursos(cursosEncontrados);
        }
        professor.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        professorRepository.save(professor);
    }

}
