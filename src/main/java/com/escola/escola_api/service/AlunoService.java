package com.escola.escola_api.service;

import com.escola.escola_api.configuration.RegraNegocioProperties;
import com.escola.escola_api.controller.dto.aluno.*;
import com.escola.escola_api.exception.AcessoNegadoException;
import com.escola.escola_api.exception.CursoLotadoException;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.AlunoRepository;
import com.escola.escola_api.repository.CursoRepository;
import com.escola.escola_api.repository.mapper.AlunoMapper;
import com.escola.escola_api.repository.specification.AlunoSpec;
import com.escola.escola_api.security.SecurityService;
import com.escola.escola_api.validator.AlunoValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public Aluno salvar(AlunoCadastroDTO dto, UUID idUsuario) {
        Aluno aluno = mapper.toEntity(dto);
        aluno.setCpf(limparCpf(dto.cpf()));
        if (dto.idCurso() != null) {
            aluno.setCurso(cursoRepository.findById(dto.idCurso())
                    .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado.")));
        }
        validator.validar(aluno);
        aluno.setIdUsuario(idUsuario);
        return alunoRepository.save(aluno);
    }

    public Page<AlunoView> listar(Integer matricula, String nome, String cpf, UUID usuarioAtualizacao, Pageable pageable) {
        boolean isAdmin = securityService.isAdmin();
        if (!isAdmin && (cpf != null || usuarioAtualizacao != null))
            throw new AcessoNegadoException("Apenas administradores podem filtrar por esses campos.");
        Specification<Aluno> spec = isAdmin
                ? AlunoSpec.comFiltros(matricula, nome, cpf, usuarioAtualizacao)
                : AlunoSpec.comFiltros(null, nome, null, null);
        Page<Aluno> resultado = alunoRepository.findAll(spec, pageable);
        return isAdmin ? resultado.map(mapper::toDTO) : resultado.map(mapper::toResumoDTO);
    }

    public AlunoPesquisaDTO buscarPorMatriculaAdmin(Integer matricula) {
        return alunoRepository.findByMatricula(matricula)
                .map(mapper::toDTO).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado."));
    }

    public AlunoResumoDTO buscarPorMatriculaPublico(Integer matricula) {
        return alunoRepository.findByMatricula(matricula)
                .map(mapper::toResumoDTO).orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado."));
    }

    @Transactional
    public void atualizar(Integer matricula, AlunoAtualizacaoDTO dto) {
        Aluno aluno = alunoRepository.findByMatricula(matricula)
                .orElseThrow(() -> new EntityNotFoundException("Aluno não encontrado!"));
        mapper.updateEntityFromDTO(dto, aluno);
        if (dto.cpf() != null && !dto.cpf().isBlank())aluno.setCpf(limparCpf(dto.cpf()));
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
        Curso cursoEncontrado = cursoRepository.findById(curso)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado!"));
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
