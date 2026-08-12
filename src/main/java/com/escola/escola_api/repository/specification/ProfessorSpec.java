package com.escola.escola_api.repository.specification;

import com.escola.escola_api.model.entity.Professor;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class ProfessorSpec {

    public static Specification<Professor> comFiltros(String nome, Integer matricula, String cpf, UUID usuarioAtualizacao) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (nome != null && !nome.isBlank()) predicates.add(cb.like(cb.lower(root.get("nome")), "%" + nome.toLowerCase() + "%"));
            if (matricula != null) predicates.add(cb.equal(root.get("matricula"), matricula));
            if (cpf != null && !cpf.isBlank()) predicates.add(cb.equal(root.get("cpf"), cpf));
            if (usuarioAtualizacao != null) predicates.add(cb.equal(root.get("usuarioAtualizacao"), usuarioAtualizacao));

            return  cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
