package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.controller.dto.aluno.AlunoPesquisaDTO;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/alunos")
@RequiredArgsConstructor
public class AlunoController implements GenericController {

    private final AlunoService alunoService;

    @PostMapping
    public ResponseEntity<Void> salvar(@RequestBody @Valid AlunoCadastroDTO dto) {
        Aluno entity = alunoService.salvar(dto);
        URI location = gerarHeaderLocationMatricula(entity.getMatricula());
        return ResponseEntity.created(location).build();
    }

    @GetMapping
    public ResponseEntity<List<AlunoPesquisaDTO>> listar() {
        return ResponseEntity.ok(alunoService.listar());
    }

    @GetMapping("/{matricula}")
    public ResponseEntity<AlunoPesquisaDTO> buscarPorId(@PathVariable Integer matricula) {
        return alunoService.buscarPorMatricula(matricula)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{matricula}")
    public ResponseEntity<Void> atualizar(@RequestBody @Valid AlunoCadastroDTO dto, @PathVariable Integer matricula) {
        alunoService.atualizar(dto, matricula);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/{matricula}")
    public ResponseEntity<Void> excluir(@PathVariable Integer matricula) {
        alunoService.excluir(matricula);
        return ResponseEntity.noContent().build();
    }
}