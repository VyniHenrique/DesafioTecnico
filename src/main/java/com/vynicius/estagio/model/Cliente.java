package com.vynicius.estagio.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "cliente")
@Data
public class Cliente {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "nome", nullable = false)
    private String nome;

    @Column(name = "cpf", nullable = false, length = 11)
    private String cpf;

    @Column(name = "telefone", nullable = false, length = 11)
    private String telefone;

    @Column(name = "email", nullable = false, length = 50)
    private String email;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
    @JsonManagedReference
    private List<Conta> contas;

}
