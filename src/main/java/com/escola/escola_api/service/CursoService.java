package com.escola.escola_api.service;

import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.CursoRepository;
import com.escola.escola_api.security.SecurityService;
import com.escola.escola_api.validator.CursoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CursoValidator validator;
    private final SecurityService securityService;

    public void salvar(Curso curso){
        validator.validar(curso);
        curso.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        cursoRepository.save(curso);
    }

    public List<Curso> buscarTodos() {
        return cursoRepository.findAll();
    }

    public Optional<Curso> buscarPorId(UUID id) {
        return cursoRepository.findById(id);
    }

    public void atualizar(Curso curso) {
        validator.validar(curso);
        curso.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        cursoRepository.save(curso);
    }

    public void delete(Curso curso) {
        cursoRepository.delete(curso);
    }
}
