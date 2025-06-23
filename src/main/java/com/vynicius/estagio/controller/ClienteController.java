package com.vynicius.estagio.controller;

import com.vynicius.estagio.controller.dto.ClienteDTO;
import com.vynicius.estagio.controller.dto.ContaDTO;
import com.vynicius.estagio.controller.mappers.ClienteMapper;
import com.vynicius.estagio.controller.mappers.ContaMapper;
import com.vynicius.estagio.model.Cliente;
import com.vynicius.estagio.model.Conta;
import com.vynicius.estagio.service.ClienteService;
import com.vynicius.estagio.service.ContaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

//Classe que controla as requisições da API para o endpoint /clientes/...
@RestController
@RequestMapping("clientes")
@Tag(name = "Clientes")
public class ClienteController implements GenericController{

    private final ClienteService clienteservice;
    private final ContaService contaService;
    private final ClienteMapper clienteMapper;
    private final ContaMapper contaMapper;

    public ClienteController(ClienteService clienteservice, ClienteMapper clienteMapper, ContaService contaService, ContaMapper contaMapper){
        this.clienteservice = clienteservice;
        this.contaService = contaService;
        this.clienteMapper = clienteMapper;
        this.contaMapper = contaMapper;
    }

//  Requisição do tipo POST para criar um Cliente
    @PostMapping
    @Operation(summary = "Cadastrar", description = "Cadastra um novo Cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Cadastrado com sucesso"),
            @ApiResponse(responseCode = "422", description = "Erro de validação")
    })
    public ResponseEntity<Void> cadastrar (@RequestBody ClienteDTO clienteDto){

        Cliente cliente = clienteMapper.toEntity(clienteDto);

        clienteservice.salvar(cliente);

        URI location = gerarHeaderLocation(cliente.getId());

        return ResponseEntity.created(location).build();
    }

//  Requisição do tipo GET para listar os Clientes
    @GetMapping
    @Operation(summary = "Listar", description = "Lista todos os Clientes salvos na base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Operação realizada com sucesso")
    })
    public ResponseEntity<List<ClienteDTO>> listarTodos(){

        List<Cliente> resultado = clienteservice.listarTodos();

        List<ClienteDTO> listaClientes = resultado.stream().map(cliente -> new ClienteDTO(
                cliente.getId(),
                cliente.getNome(),
                cliente.getCpf(),
                cliente.getTelefone(),
                cliente.getEmail())).collect(Collectors.toList());

        return ResponseEntity.ok(listaClientes);
    }

//  Requisição do tipo PUT para atualizar um Cliente
    @PutMapping("/{id}")
    @Operation(summary = "Atualizar", description = "Atualiza um Cliente salvo na base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente atualizado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado na base de dados")
    })
    public ResponseEntity<Void> atualizar (@PathVariable("id") Long id, @RequestBody ClienteDTO clienteDto){

        Optional<Cliente> clienteOptional = clienteservice.obterPorId(id);

        if (clienteOptional.isPresent()){
            Cliente cliente = clienteOptional.get();
            cliente.setNome(clienteDto.nome());
            cliente.setCpf(clienteDto.cpf());
            cliente.setTelefone(clienteDto.telefone());
            cliente.setEmail(clienteDto.email());

            clienteservice.atualizar(cliente);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

//  Requisição do tipo DELETE para deletar um Cliente
    @DeleteMapping("/{id}")
    @Operation(summary = "Deletar", description = "Deleta um cliente da base de dados")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Cliente deletado com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado na base de dados")
    })
    public ResponseEntity<Void> deletar (@PathVariable("id") Long id){

        Optional<Cliente> clienteOptional = clienteservice.obterPorId(id);

        if (clienteOptional.isPresent()){
            Cliente cliente = clienteOptional.get();
            clienteservice.deletar(cliente);

            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.notFound().build();
    }

//  Requisição do tipo POST para criar uma conta
    @PostMapping("{idCliente}/contas")
    @Operation(summary = "Criar", description = "Cria uma conta ligada a um cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Conta criada"),
            @ApiResponse(responseCode = "422", description = "Erro de validação")
    })
    public ResponseEntity<Void> salvarConta(@PathVariable("idCliente") Long id, @RequestBody @Valid ContaDTO contaDTO){

        Conta conta = contaMapper.toEntity(contaDTO);

        URI location = gerarHeaderLocation(conta.getId());

        contaService.salvar(conta);
        return ResponseEntity.created(location).build();
    }

//  Requisição do tipo POST para criar uma nova Conta
    @GetMapping("{idCliente}/contas")
    @Operation(summary = "Listar", description = "Lista todas as contas ligadas ao id de um Cliente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista as contas ligadas ao Cliente"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    public ResponseEntity<List<Conta>> listarContas(@PathVariable("idCliente") Long id){

        Optional<Cliente> clienteOptional = clienteservice.obterPorId(id);
        if (clienteOptional.isPresent()){
            Cliente cliente = clienteOptional.get();

            List<Conta> lista = contaService.listarContas(cliente);
            return ResponseEntity.ok(lista);
        }
        return ResponseEntity.notFound().build();
    }


}
