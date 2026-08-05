package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoPesquisaDTO;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.repository.mapper.AlunoMapper;
import com.escola.escola_api.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController {

    private final AlunoService alunoService;
    private final AlunoMapper mapper;

    @PostMapping
    public ResponseEntity<?> salvar(@RequestBody AlunoCadastroDTO dto) {
        alunoService.salvar(mapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<List<AlunoPesquisaDTO>> listar() {
        List<AlunoPesquisaDTO> dtos = alunoService.listar()
                .stream()
                .map(mapper::toDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<AlunoPesquisaDTO> buscarPorId(@PathVariable Integer matricula) {
        return alunoService.buscarPorId(matricula)
                .map(mapper::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<AlunoPesquisaDTO> atualizar(@RequestBody AlunoCadastroDTO dto, @PathVariable Integer matricula) {
        return alunoService.buscarPorId(matricula)
                .map(entity -> {
                    Aluno aluno = mapper.updateEntityFromDTO(dto, entity);
                    alunoService.atualizar(aluno);
                    return ResponseEntity.ok(mapper.toDTO(aluno));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<?> excluir(@PathVariable Integer matricula) {
        return alunoService.buscarPorId(matricula)
                .map(aluno -> {
                    alunoService.excluir(aluno);
                    return aluno;
                })
                .map(aluno -> ResponseEntity.ok().build())
                .orElse(ResponseEntity.notFound().build());
    }
}