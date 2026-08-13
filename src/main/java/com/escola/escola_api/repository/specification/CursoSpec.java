package com.escola.escola_api.repository.specification;

import com.escola.escola_api.model.entity.Curso;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CursoSpec {
    public static Specification<Curso> comFiltros(UUID id, String nome, Integer cargaHoras, UUID usuarioAtualizacao) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();
            if (id != null) predicates.add(cb.equal(root.get("id"), id));
            if (nome != null && !nome.isBlank()) predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            if (cargaHoras != null) predicates.add(cb.equal(root.get("caraHoras"), cargaHoras));
            if (usuarioAtualizacao != null) predicates.add(cb.equal(root.get("usuarioAtualizacao"), usuarioAtualizacao));
            return cb.and(predicates.toArray(Predicate[]::new));
        };
    }
}
