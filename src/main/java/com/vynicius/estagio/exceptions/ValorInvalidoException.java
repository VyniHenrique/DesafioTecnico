package com.vynicius.estagio.exceptions;

//Exception criada para ser lançada quando o usuário tentar cadastrar uma conta com o valor menor que 0
public class ValorInvalidoException extends RuntimeException {
    public ValorInvalidoException(String message) {
        super(message);
    }
}
