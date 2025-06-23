package com.vynicius.estagio.controller;

import com.vynicius.estagio.controller.dto.ContaDTO;
import com.vynicius.estagio.controller.mappers.ContaMapper;
import com.vynicius.estagio.model.Conta;
import com.vynicius.estagio.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//Classe que controla as requisições da API para o endpoint /contas/...
@RestController
@RequestMapping("/contas")
@Tag(name = "Contas")
public class ContaController {

    private final ContaService contaService;
    private final ContaMapper contaMapper;

    public ContaController(ContaService contaService, ContaMapper contaMapper){
        this.contaService = contaService;
        this.contaMapper = contaMapper;
    }

//  Requisição do tipo PUT para atualizar uma Conta
    @PutMapping("{id}")
    @Operation(summary = "Atualizar", description = "Atuzaliza as informações de uma conta")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<Object> atualizarConta(@PathVariable("id") Long id, @RequestBody @Valid ContaDTO contaDTO){

        return contaService.obterPorId(id).map(conta -> {
                Conta contaAux = contaMapper.toEntity(contaDTO);
                conta.setValor(contaAux.getValor());
                conta.setReferencia(contaAux.getReferencia());
                conta.setSituacao(contaAux.getSituacao());
                conta.setCliente(contaAux.getCliente());

                contaService.atualizar(conta);
            return ResponseEntity.noContent().build();}).orElseGet( () -> ResponseEntity.notFound().build());


        }

//  Requisição do tipo DELETE para atualizar a situação da Conta para 'CANCELADA'
    @DeleteMapping("{id}")
    @Operation(summary = "Cancelar", description = "Atualiza o status da conta para 'CANCELADA' ")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cancelada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Conta não encontrada")
    })
    public ResponseEntity<Object> deletarConta(@PathVariable("id") @Valid Long id){

        return contaService.obterPorId(id).map(conta -> {
            contaService.deletar(conta);
            return ResponseEntity.noContent().build();
        }).orElseGet( () -> ResponseEntity.notFound().build());
    }

}
