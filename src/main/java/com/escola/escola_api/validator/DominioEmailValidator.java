package com.escola.escola_api.validator;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;

public class DominioEmailValidator implements ConstraintValidator<DominioEmailValido, String> {

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || !email.contains("@")) {
            return false;
        }
        String dominio = email.substring(email.indexOf("@") + 1).trim();
        if (dominio.isEmpty()) {
            return false;
        }
        return temRegistroMX(dominio);
    }

    private boolean temRegistroMX(String dominio) {
        try {
            InitialDirContext ctx = new InitialDirContext();
            Attributes attribute = ctx.getAttributes("dns:/" + dominio, new String[]{"MX"});
            return attribute != null && attribute.get("MX") != null;
        } catch (NamingException e) {
            return false;
        }
    }
}
