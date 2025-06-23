package com.vynicius.estagio.service;

import com.vynicius.estagio.model.Cliente;
import com.vynicius.estagio.model.Conta;
import com.vynicius.estagio.model.ContaSituacao;
import com.vynicius.estagio.repository.ContaRepository;
import com.vynicius.estagio.validator.ContaValidator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContaService {

    private final ContaRepository contaRepository;
    private final ContaValidator validator;

    public ContaService(ContaRepository contaRepository, ContaValidator validator){
        this.contaRepository = contaRepository;
        this.validator = validator;
    }

    public Conta salvar(Conta conta){

        validator.validar(conta);
        return contaRepository.save(conta);
    }

    public void deletar(Conta conta){
        conta.setSituacao(ContaSituacao.CANCELADA);
        contaRepository.save(conta);
    }

    public Optional<Conta> obterPorId(Long id){
        return contaRepository.findById(id);
    }

    public void atualizar(Conta conta){
        contaRepository.save(conta);
    }

    public List<Conta> listarContas(Cliente cliente){
        return contaRepository.findByCliente(cliente);
    }

}
