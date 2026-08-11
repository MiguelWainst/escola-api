package com.escola.escola_api.controller;

import com.escola.escola_api.controller.dto.usuario.UsuarioCadastroDTO;
import com.escola.escola_api.model.entity.Usuario;
import com.escola.escola_api.service.UsuarioService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.net.URI;

@RestController
@RequiredArgsConstructor
@RequestMapping("/usuarios")
public class UsuarioController implements GenericController{

    private final UsuarioService usuarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public URI cadastrarUsuario(@RequestBody @Valid UsuarioCadastroDTO dto) {
        Usuario usuario = usuarioService.salvar(dto);
        return gerarHeaderLocation(usuario.getId());
    }
}
