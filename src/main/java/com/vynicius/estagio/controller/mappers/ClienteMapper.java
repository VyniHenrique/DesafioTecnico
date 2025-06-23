package com.vynicius.estagio.controller.mappers;

import com.vynicius.estagio.controller.dto.ClienteDTO;
import com.vynicius.estagio.model.Cliente;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ClienteMapper {

    Cliente toEntity(ClienteDTO dto);

    ClienteDTO toDTO(Cliente cliente);

}
