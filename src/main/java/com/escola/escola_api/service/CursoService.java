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

    public CursoPesquisaDTO buscarPorId(UUID id) {
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

    public void delete(Curso curso) {
        cursoRepository.delete(curso);
    }

    private String limparCpf(String cpf) {
        return cpf.replaceAll("[^0-9]", "");
    }

}
