package com.vynicius.estagio.controller.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import org.hibernate.validator.constraints.br.CPF;

@Schema(name = "Autor")
public record ClienteDTO(

        Long id,

        @NotBlank(message = "O campo 'nome' deve ser preenchido.")
        String nome,

        @NotBlank(message = "O campo 'cpf' deve ser preenchido.")
        @CPF
        String cpf,

        String telefone,

        String email) {



}
