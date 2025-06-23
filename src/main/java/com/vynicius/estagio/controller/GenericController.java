package com.vynicius.estagio.controller;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;

// Classe para gerar uma URI única para o Cliente
public interface GenericController {

    default URI gerarHeaderLocation(Long id){
        return ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(id).toUri();
    }
}

