package com.escola.escola_api.model.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "professores")
@EntityListeners(AuditingEntityListener.class)
@Getter
@Setter
@NoArgsConstructor
public class Professor {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "professor_seq_gen")
    @SequenceGenerator(
            name = "professor_seq_gen",
            sequenceName = "professores_seq",
            allocationSize = 1
    )
    private Integer matricula;

    private String nome;

    private String email;

    private LocalDate dataNascimento;

    private String cpf;

    @CreatedDate
    private LocalDate dataContratacao;

    @LastModifiedDate
    private LocalDateTime dataAtualizacao;

    private UUID usuarioAtualizacao;

    @ManyToMany
    @JoinTable(
            name = "professores_cursos",
            joinColumns = @JoinColumn(name = "matricula_professor", referencedColumnName = "matricula"),
            inverseJoinColumns = @JoinColumn(name = "id_curso", referencedColumnName = "id")
    )
    private List<Curso> cursos;
}
