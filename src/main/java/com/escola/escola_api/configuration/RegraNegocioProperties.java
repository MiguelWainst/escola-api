package com.escola.escola_api.configuration;

import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@ConfigurationProperties(prefix = "regras-negocio")
public class RegraNegocioProperties {

    @Min(1)
    private int maxAlunosPorCurso;

    @Min(1)
    private int maxCursosPorProfessor;

    @Min(1)
    private int maxProfessoresPorCurso;
}
