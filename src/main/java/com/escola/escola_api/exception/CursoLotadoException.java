package com.escola.escola_api.exception;

public class CursoLotadoException extends RuntimeException {
    public CursoLotadoException() {
        super("Curso já atingiu o limite máximo de alunos.");
    }
    public CursoLotadoException(String message) {
        super(message);
    }
}
