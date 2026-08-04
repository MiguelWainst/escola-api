package com.escola.escola_api.validator;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import javax.naming.NamingException;
import javax.naming.directory.Attributes;
import javax.naming.directory.InitialDirContext;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class DominioEmailValidator implements ConstraintValidator<DominioEmailValido, String> {

    private static final Set<String> DOMINIOS_FAMOSOS = Set.of(
            "gmail.com", "hotmail.com", "outlook.com", "yahoo.com",
            "yahoo.com.br", "icloud.com", "live.com", "bol.com.br"
    );

    private static final Cache<String, Boolean> DOMINIO_CACHE = Caffeine.newBuilder()
            .maximumSize(1000)
            .expireAfterWrite(24, TimeUnit.HOURS)
            .build();

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        if (email == null || !email.contains("@")) {
            return false;
        }
        String dominio = email.substring(email.indexOf("@") + 1).trim();
        if (dominio.isEmpty()) return false;
        if (DOMINIOS_FAMOSOS.contains(dominio)) return true;
        return DOMINIO_CACHE.get(dominio, this::temRegistroMX);
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
