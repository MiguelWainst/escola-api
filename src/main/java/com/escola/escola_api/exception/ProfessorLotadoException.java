package com.escola.escola_api.exception;

public class ProfessorLotadoException extends RuntimeException {
    public ProfessorLotadoException(String message) {
        super(message);
    }
    public ProfessorLotadoException() {
        super("Este professor já está lecionando mais de 5 cursos.");
    }
}
