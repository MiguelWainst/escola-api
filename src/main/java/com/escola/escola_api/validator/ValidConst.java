package com.escola.escola_api.validator;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public final class ValidConst {
    public static final int NOME_MIN = 2;
    public static final int NOME_MAX = 100;
    public static final int NOME_MIN_CURSO = 10;
    public static final int NOME_MAX_CURSO = 180;
    public static final int CPF_MIN = 11;
    public static final int CPF_MAX = 14;
    public static final int CARGA_HORA_MAX = 300;
    public static final int CARGA_HORA_MIN = 1;
    public static final int TEMPO_EXPIRACAO_JWT = 3600000;
    public static final int USUARIO_MAX = 30;
    public static final int USUARIO_MIN = 3;
    public static final int SENHA_MAX = 50;
    public static final int SENHA_MIN = 8;

}
