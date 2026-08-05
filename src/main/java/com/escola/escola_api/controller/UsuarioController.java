package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.usuario.UsuarioCadastroDTO;
import com.escola.escola_api.repository.mapper.UsuarioMapper;
import com.escola.escola_api.service.UsuarioService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController {

    private final UsuarioService usuarioService;
    private final UsuarioMapper mapper;

    @PostMapping
    public ResponseEntity<Void> cadastrarUsuario(@RequestBody UsuarioCadastroDTO dto) {
        usuarioService.salvar(mapper.toEntity(dto));
        return ResponseEntity.ok().build();
    }
}
