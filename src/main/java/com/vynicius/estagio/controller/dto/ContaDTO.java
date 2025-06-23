package com.vynicius.estagio.controller.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.vynicius.estagio.model.ContaSituacao;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.YearMonth;

@Schema(name = "Conta")
public record ContaDTO (

        Long id,

        @NotNull(message = "O campo 'referencia' deve ser preenchido.")
        @JsonFormat(pattern = "MM-yyyy")
        YearMonth referencia,

        @NotNull(message = "O campo 'valor' deve ser preenchido.")
        BigDecimal valor,

        @NotNull(message = "O campo 'situação' deve ser preenchido.")
        ContaSituacao situacao,

        @NotNull(message = "O campo 'idCliente' deve ser preenchido.")
        Long idCliente
        ){
}
