package com.vynicius.estagio.repository;

import com.vynicius.estagio.model.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ClienteRepository extends JpaRepository<Cliente, Long > {
}
