package com.escola.escola_api.model.entity;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.JdbcTypeCode;

import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "usuarios")
@Data
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String username;

    private String email;

    private String senha;

    @JdbcTypeCode(java.sql.Types.ARRAY)
    @Column(columnDefinition = "varchar[]")
    private List<String> roles;
}
