package com.vynicius.estagio.controller.mappers;

import com.vynicius.estagio.controller.dto.ClienteDTO;
import com.vynicius.estagio.model.Cliente;
import org.mapstruct.Mapper;

// Interface que utiliza o MapStruct(Mapeador de dados)
@Mapper(componentModel = "spring")
public interface ClienteMapper {

//  Mapeia os dados do ClienteDTO para o Cliente da camada representacional
    Cliente toEntity(ClienteDTO dto);
}
