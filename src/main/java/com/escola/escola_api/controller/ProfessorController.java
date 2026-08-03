package com.escola.escola_api.controller;

import com.escola.escola_api.service.ProfessorService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/professores")
@RequiredArgsConstructor
public class ProfessorController {

    private final ProfessorService professorService;

}
