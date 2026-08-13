package com.escola.escola_api.service;

import com.escola.escola_api.controller.dto.curso.CursoCadastroDTO;
import com.escola.escola_api.controller.dto.curso.CursoPesquisaDTO;
import com.escola.escola_api.controller.dto.curso.CursoResumoDTO;
import com.escola.escola_api.exception.CursoComVinculoException;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.CursoRepository;
import com.escola.escola_api.repository.mapper.CursoMapper;
import com.escola.escola_api.security.SecurityService;
import com.escola.escola_api.validator.CursoValidator;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class CursoService {

    private final CursoRepository cursoRepository;
    private final CursoValidator validator;
    private final SecurityService securityService;
    private final CursoMapper mapper;

    @Transactional
    public Curso salvar(CursoCadastroDTO dto){
        Curso curso = mapper.toEntity(dto);
        validator.validar(curso);
        curso.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        return cursoRepository.save(curso);
    }

    public List<CursoResumoDTO> buscarTodos() {
        return cursoRepository.findAll().stream().map(mapper::toResumoDTO).toList();
    }

    public CursoResumoDTO buscarPorId(UUID id) {
        return cursoRepository.findById(id)
                .map(mapper::toResumoDTO)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado."));
    }

    public CursoPesquisaDTO buscarPorIdAdmin(UUID id) {
        return cursoRepository.findById(id)
                .map(mapper::toPesquisaDTO)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado."));
    }

    @Transactional
    public void atualizar(UUID id, CursoCadastroDTO dto) {
        Curso cursoDoBanco = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado."));
        mapper.updateEntityFromDTO(dto, cursoDoBanco);
        cursoDoBanco.setUsuarioAtualizacao(securityService.obterUsuarioLogado().getId());
        validator.validar(cursoDoBanco);
    }

    @Transactional
    public void deletar(UUID id) {
        Curso curso = cursoRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Curso não encontrado."));
        if (!curso.getAlunos().isEmpty() || !curso.getProfessores().isEmpty()) {
            throw new CursoComVinculoException("Este curso possuí 1 ou mais professores/alunos cadastrados! " +
                    "Remova-os para prosseguir.");
        }
        cursoRepository.delete(curso);
    }
}
