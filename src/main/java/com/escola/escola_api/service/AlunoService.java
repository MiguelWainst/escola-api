package com.escola.escola_api.service;

import com.escola.escola_api.configuration.RegraNegocioProperties;
import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoPesquisaDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoResumoDTO;
import com.escola.escola_api.exception.AlunoComCursoException;
import com.escola.escola_api.exception.CursoLotadoException;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.model.entity.Curso;
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
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final CursoRepository cursoRepository;
    private final SecurityService securityService;
    private final AlunoValidator validator;
    private final AlunoMapper mapper;
    private final RegraNegocioProperties regras;

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

    public List<AlunoPesquisaDTO> listarAdmin() {
        return alunoRepository.findAll()
                .stream()
                .map(mapper::toDTO)
                .toList();
    }

    public List<AlunoResumoDTO> listarResumo() {
        return alunoRepository.findAll()
                .stream()
                .map(mapper::toResumoDTO)
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

    @Transactional
    public void vincular(Integer matricula, UUID curso) {
        Aluno aluno = alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado!"));
        if (aluno.getCurso() != null) {
            throw new AlunoComCursoException("Este aluno já está vinculado a um curso!");
        }
        Curso cursoEncontrado = cursoRepository.findById(curso).orElseThrow(() -> new EntityNotFoundException("Curso não encontrado!"));
        if (cursoEncontrado.getAlunos().size() < regras.getMaxAlunosPorCurso()) {
            aluno.setCurso(cursoEncontrado);
            return;
        }
        throw new CursoLotadoException();
    }

    private String limparCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }
}
