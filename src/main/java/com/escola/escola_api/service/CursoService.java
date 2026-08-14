package com.escola.escola_api.service;

import com.escola.escola_api.controller.dto.curso.*;
import com.escola.escola_api.exception.AcessoNegadoException;
import com.escola.escola_api.exception.CursoComVinculoException;
import com.escola.escola_api.model.entity.Curso;
import com.escola.escola_api.repository.CursoRepository;
import com.escola.escola_api.repository.mapper.CursoMapper;
import com.escola.escola_api.repository.specification.CursoSpec;
import com.escola.escola_api.security.SecurityService;
import com.escola.escola_api.validator.CursoValidator;
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

    public Page<CursoView> listar(UUID id, String nome, Integer cargaHoras, UUID usuarioAtualizacao, Pageable pageable) {
        boolean isAdmin = securityService.isAdmin();
        if (!isAdmin && (id != null || usuarioAtualizacao != null))
            throw new AcessoNegadoException("Apenas administradores podem filtrar por esses campos.");
        Specification<Curso> spec = isAdmin
                ? CursoSpec.comFiltros(id, nome, cargaHoras, usuarioAtualizacao)
                : CursoSpec.comFiltros(null, nome, cargaHoras, null);
        Page<Curso> resultado = cursoRepository.findAll(spec, pageable);
        return isAdmin ? resultado.map(mapper::toPesquisaDTO) : resultado.map(mapper::toResumoDTO);
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
    public void atualizar(UUID id, CursoAtualizacaoDTO dto) {
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
