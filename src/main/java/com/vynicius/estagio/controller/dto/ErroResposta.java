package com.vynicius.estagio.controller.dto;

import org.springframework.http.HttpStatus;

import java.util.List;

// Objeto usado para listar os campos da requisição que houve erro(s)
public record ErroResposta(int status, String resposta, List<ErroCampo> erroCampos) {
    public static ErroResposta respostaPadrao(String mensagem){
        return new ErroResposta(HttpStatus.BAD_REQUEST.value(), mensagem, List.of());
    }
}
