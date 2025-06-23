package com.vynicius.estagio.controller.dto;

// Compõe a classe ErroResposta, contendo o(s) campo(s) onde houve erro e o tipo do erro
public record ErroCampo(String campo, String erro) {
}
