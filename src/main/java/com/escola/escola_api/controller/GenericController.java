package com.escola.escola_api.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.UUID;

public interface GenericController {

    default URI gerarHeaderLocation(UUID id) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(id)
                .toUri();
    }

    default URI gerarHeaderLocationMatricula(Integer matricula) {
        return ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/{matricula}")
                .buildAndExpand(matricula)
                .toUri();
    }
}
