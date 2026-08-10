package com.escola.escola_api.service;

import com.escola.escola_api.controller.dto.curso.CursoCadastroDTO;
import com.escola.escola_api.controller.dto.curso.CursoPesquisaDTO;
import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;
import com.escola.escola_api.exception.AlunoComCursoException;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.AlunoRepository;
import com.escola.escola_api.repository.CursoRepository;
import com.escola.escola_api.repository.mapper.CursoMapper;
import com.escola.escola_api.security.SecurityService;
import com.escola.escola_api.validator.CursoValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final AlunoRepository alunoRepository;
    private final CursoValidator validator;
    private final SecurityService securityService;
    private final CursoMapper mapper;

    @Transactional
    public Curso salvar(CursoCadastroDTO dto){
        Curso curso = mapper.toEntity(dto);
        validator.validar(curso);
        curso.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        Curso save = cursoRepository.save(curso);
        if (dto.matriculaAlunos() != null && !dto.matriculaAlunos().isEmpty()) {
            List<Aluno> possiveisAlunos = alunoRepository.findAllById(dto.matriculaAlunos());
            if (possiveisAlunos.size() != dto.matriculaAlunos().size())
                throw new EntityNotFoundException("Um ou mais alunos não existem");
            if (possiveisAlunos.stream().anyMatch(aluno -> aluno.getCurso() != null))
                throw new AlunoComCursoException("Um ou mais alunos já estão matriculados em um curso.");
            curso.setAlunos(possiveisAlunos);
            possiveisAlunos.forEach(aluno -> aluno.setCurso(curso));
        }
        return save;
    }

    public List<CursoResumoDTO> buscarTodos() {
        return cursoRepository.findAll().stream().map(mapper::toResumoDTO).toList();
    }

    public CursoPesquisaDTO buscarPorId(UUID id) {
        return cursoRepository.findById(id)
                .map(mapper::toPesquisaDTO)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado."));
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
