package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.aluno.AlunoCadastroDTO;
import com.escola.escola_api.model.entity.Aluno;
import com.escola.escola_api.service.AlunoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.URI;

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

//    @GetMapping
//    public ResponseEntity<List<AlunoPesquisaDTO>> listar() {
//        List<AlunoPesquisaDTO> dtos = alunoService.listar()
//                .stream()
//                .map(mapper::toDTO)
//                .toList();
//        return ResponseEntity.ok(dtos);
//    }
//
//    @GetMapping("/{matricula}")
//    public ResponseEntity<AlunoPesquisaDTO> buscarPorId(@PathVariable Integer matricula) {
//        return alunoService.buscarPorId(matricula)
//                .map(mapper::toDTO)
//                .map(ResponseEntity::ok)
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @PutMapping("/{matricula}")
//    public ResponseEntity<AlunoPesquisaDTO> atualizar(@RequestBody @Valid AlunoCadastroDTO dto, @PathVariable Integer matricula) {
//        return alunoService.buscarPorId(matricula)
//                .map(entity -> {
//                    Aluno aluno = mapper.updateEntityFromDTO(dto, entity);
//                    alunoService.atualizar(aluno);
//                    return ResponseEntity.ok(mapper.toDTO(aluno));
//                })
//                .orElse(ResponseEntity.notFound().build());
//    }
//
//    @DeleteMapping("/{matricula}")
//    public ResponseEntity<Void> excluir(@PathVariable Integer matricula) {
//        Optional<Aluno> alunoOptional = alunoService.buscarPorId(matricula);
//        if (alunoOptional.isEmpty()) {
//            return ResponseEntity.notFound().build();
//        }
//        alunoService.excluir(alunoOptional.get());
//        return ResponseEntity.noContent().build();
//    }
}