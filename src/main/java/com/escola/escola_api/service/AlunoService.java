package com.escola.escola_api.service;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoPesquisaDTO;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.repository.AlunoRepository;
import com.escola.escola_api.repository.CursoRepository;
import com.escola.escola_api.repository.mapper.AlunoMapper;
import com.escola.escola_api.security.SecurityService;
import com.escola.escola_api.validator.AlunoValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final SecurityService securityService;
    private final AlunoValidator validator;
    private final AlunoMapper mapper;

    @Transactional
    public Aluno salvar(AlunoCadastroDTO dto) {
        Aluno aluno = mapper.toEntity(dto);
        if (dto.idCurso() != null) {
            aluno.setCurso(cursoRepository.findById(dto.idCurso())
                    .orElseThrow(() -> new EntityNotFoundException("Curso não econtrado.")));
        }
        aluno.setCpf(limparCpf(dto.cpf()));
        validator.validar(aluno);
        aluno.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        return alunoRepository.save(aluno);
    }

    public List<AlunoPesquisaDTO> listar() {
        return alunoRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public AlunoPesquisaDTO buscarPorMatricula(Integer matricula) {
        return alunoRepository.findByMatricula(matricula)
                .map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado."));
    }

    @Transactional
    public void atualizar(Integer matricula, AlunoCadastroDTO dto) {
        Aluno aluno = alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado!"));
        mapper.updateEntityFromDTO(dto, aluno);
        if (dto.idCurso() != null) {
            aluno.setCurso(cursoRepository.findById(dto.idCurso())
                    .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado.")));
        }
        aluno.setCpf(limparCpf(dto.cpf()));
        validator.validar(aluno);
        aluno.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
    }

    @Transactional
    public void excluir(Integer matricula) {
        Aluno aluno = alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado!"));
        alunoRepository.delete(aluno);
    }

    @Transactional
    public void desvincular(Integer matricula) {
        Aluno aluno = alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado!"));
        aluno.setCurso(null);
    }

    private String limparCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }
}
