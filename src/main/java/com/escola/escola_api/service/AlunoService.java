package com.escola.escola_api.service;

import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.repository.AlunoRepository;
import com.escola.escola_api.security.SecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final SecurityService securityService;

    public void salvar(Aluno aluno) {
        aluno.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        alunoRepository.save(aluno);
    }

    public List<Aluno> listar() {
        return alunoRepository.findAll();
    }

    public Optional<Aluno> buscarPorId(Integer id) {
        return alunoRepository.findByMatricula(id);
    }
}
