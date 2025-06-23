package com.vynicius.estagio.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @Info(
                title = "Desafio Técnico - API",
                summary = "Documentação da API de clientes para o desafio técnico."

        )
)
public class OpeApiConfiguration {
}
