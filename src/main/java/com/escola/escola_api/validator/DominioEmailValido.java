package com.escola.escola_api.validator;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = DominioEmailValidator.class) // 👈 Aponta para o validador do Passo 2
@Documented
public @interface DominioEmailValido {
    String message() default "O domínio do e-mail não existe ou não pode receber mensagens.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
