package com.vynicius.estagio.validator;

import com.vynicius.estagio.exceptions.ValorInvalidoException;
import com.vynicius.estagio.model.Conta;
import com.vynicius.estagio.model.ContaSituacao;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class ContaValidator {

    public void validar(Conta conta){
        if (conta.getValor().compareTo(BigDecimal.ZERO) < 0){
            throw new ValorInvalidoException("O valor da conta deve ser maior que 0");
        }

        if (conta.getSituacao() == ContaSituacao.CANCELADA){
            throw new ValorInvalidoException("A conta não pode ser criada com o status 'CANCELADA'");
        }
    }
}
