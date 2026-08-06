package com.escola.escola_api.service;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoPesquisaDTO;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.repository.AlunoRepository;
import com.escola.escola_api.repository.mapper.AlunoMapper;
import com.escola.escola_api.security.SecurityService;
import com.escola.escola_api.validator.AlunoValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final SecurityService securityService;
    private final AlunoValidator validator;
    private final AlunoMapper mapper;

    public Aluno salvar(AlunoCadastroDTO dto) {
        Aluno aluno = mapper.toEntity(dto);
        aluno.setCpf(limparCpf(dto.cpf()));
        validator.validar(aluno);
        aluno.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        return alunoRepository.save(aluno);
    }

    public List<AlunoPesquisaDTO> listar() {
        return alunoRepository.findAll().stream().map(mapper::toDTO).toList();
    }

    public Optional<Aluno> buscarPorId(Integer id) {
        return alunoRepository.findByMatricula(id);
    }

    public void atualizar(Aluno aluno) {
        if (aluno.getMatricula() == null) {
            throw new IllegalArgumentException("A matrícula do aluno não pode ser nula");
        }
        validator.validar(aluno);
        aluno.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        alunoRepository.save(aluno);
    }

    public void excluir(Aluno aluno) {
        alunoRepository.delete(aluno);
    }

    private String limparCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }
}
