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
public abstract class ContaMapper {

    @Autowired
    protected ClienteRepository clienteRepository;


    String map(YearMonth value) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-yyyy");
        return value.format(formatter);
    }

    @Mapping(target = "cliente", expression = "java( clienteRepository.findById(dto.idCliente()).orElse(null) )")
    public abstract Conta toEntity(ContaDTO dto);

}
