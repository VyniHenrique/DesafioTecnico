package com.vynicius.estagio.controller.mappers;

import com.vynicius.estagio.controller.dto.ContaDTO;
import com.vynicius.estagio.model.Conta;
import com.vynicius.estagio.repository.ClienteRepository;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;

@Mapper(componentModel = "spring")
// Classe abstrata que utiliza o MapStruct(Mapeador de dados)
public abstract class ContaMapper {

    @Autowired
    protected ClienteRepository clienteRepository;


//  Formata o tipo de formatação do atributo referencia(yyyy-MM) da ContaDTO para o formato 'MM-yyyy' da classe Cliente
    String map(YearMonth value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        return value.format(formatter);
    }

//  Mapeia os dados do ClienteDTO para a classe Cliente da camada representacional
    @Mapping(target = "cliente", expression = "java( clienteRepository.findById(dto.idCliente()).orElse(null) )")
    public abstract Conta toEntity(ContaDTO dto);

}
