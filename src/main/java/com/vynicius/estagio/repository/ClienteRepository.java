package com.vynicius.estagio.repository;

import com.vynicius.estagio.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

// Interface que utiliza o JPA para realizar as operações de persistência para o Cliente
public interface ClienteRepository extends JpaRepository<Cliente, Long > {
}
