package com.vynicius.estagio.repository;

import com.vynicius.estagio.model.Cliente;
import com.vynicius.estagio.model.Conta;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContaRepository extends JpaRepository<Conta, Long > {

    List<Conta> findByCliente(Cliente cliente);

    boolean existsByCliente(Cliente cliente);

}
