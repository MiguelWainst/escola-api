package com.escola.escola_api.controller;

import com.escola.escola_api.util.CpfGeneratorUtil;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/util")
public class UtilController {

    @GetMapping("/cpf")
    public Map<String, String> gerarCpf() {
        return Map.of("cpf", CpfGeneratorUtil.gerar());
    }
}
