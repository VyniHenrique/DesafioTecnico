package com.vynicius.estagio.service;

import com.vynicius.estagio.model.Cliente;
import com.vynicius.estagio.repository.ClienteRepository;
import com.vynicius.estagio.repository.ContaRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClienteService {

    private ClienteRepository clienteRepository;
    private ContaRepository contaRepository;

    public ClienteService (ClienteRepository clienteRepository, ContaRepository contaRepository){
        this.clienteRepository = clienteRepository;
        this.contaRepository = contaRepository;
    }

    public Cliente salvar(Cliente cliente){
        return clienteRepository.save(cliente);
    }

    public void atualizar(Cliente cliente){
        clienteRepository.save(cliente);

    }

    public List<Cliente> listarTodos (){
        return clienteRepository.findAll();
    }

    public Optional<Cliente> obterPorId (Long id){
        return clienteRepository.findById(id);
    }

    public void deletar(Cliente cliente){
        clienteRepository.delete(cliente);
    }

    public boolean possuiConta(Cliente cliente){
        return contaRepository.existsByCliente(cliente);
    }
}
