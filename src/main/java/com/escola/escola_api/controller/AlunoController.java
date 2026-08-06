package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoPesquisaDTO;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.repository.mapper.AlunoMapper;
import com.escola.escola_api.service.AlunoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController implements GenericController {

    private final AlunoService alunoService;
    private final AlunoMapper mapper;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody AlunoCadastroDTO dto) {
        Aluno aluno = mapper.toEntity(dto);
        alunoService.salvar(aluno);
        URI location = gerarHeaderLocationMatricula(aluno.getMatricula());
        return ResponseEntity.created(location).build();
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
    public ResponseEntity<Void> excluir(@PathVariable Integer matricula) {
        Optional<Aluno> alunoOptional = alunoService.buscarPorId(matricula);
        if (alunoOptional.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        alunoService.excluir(alunoOptional.get());
        return ResponseEntity.noContent().build();
    }
}