package com.vynicius.estagio.service;

import com.vynicius.estagio.model.Cliente;
import com.vynicius.estagio.repository.ClienteRepository;
import com.vynicius.estagio.repository.ContaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @Mock
    private ContaRepository contaRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        // Objeto Cliente padrão para uso nos testes
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João da Silva");
        cliente.setCpf("123.456.789-00");
    }

    @Test
    @DisplayName("Deve salvar um cliente com sucesso")
    void testSalvar() {
        // Arrange
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        Cliente clienteSalvo = clienteService.salvar(cliente);

        // Assert
        assertNotNull(clienteSalvo);
        assertEquals("João da Silva", clienteSalvo.getNome());
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("Deve atualizar um cliente com sucesso")
    void testAtualizar() {
        // Arrange
        when(clienteRepository.save(any(Cliente.class))).thenReturn(cliente);

        // Act
        clienteService.atualizar(cliente);

        // Assert
        verify(clienteRepository, times(1)).save(cliente);
    }

    @Test
    @DisplayName("Deve listar todos os clientes")
    void testListarTodos() {
        // Arrange
        when(clienteRepository.findAll()).thenReturn(List.of(cliente));

        // Act
        List<Cliente> clientes = clienteService.listarTodos();

        // Assert
        assertFalse(clientes.isEmpty());
        assertEquals(1, clientes.size());
        assertEquals(cliente, clientes.get(0));
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve retornar uma lista vazia quando não houver clientes")
    void testListarTodosVazio() {
        // Arrange
        when(clienteRepository.findAll()).thenReturn(Collections.emptyList());

        // Act
        List<Cliente> clientes = clienteService.listarTodos();

        // Assert
        assertTrue(clientes.isEmpty());
        verify(clienteRepository, times(1)).findAll();
    }

    @Test
    @DisplayName("Deve obter um cliente por ID com sucesso")
    void testObterPorId_Encontrado() {
        // Arrange
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        // Act
        Optional<Cliente> clienteEncontrado = clienteService.obterPorId(1L);

        // Assert
        assertTrue(clienteEncontrado.isPresent());
        assertEquals(cliente, clienteEncontrado.get());
        verify(clienteRepository, times(1)).findById(1L);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio para um ID de cliente inexistente")
    void testObterPorId_NaoEncontrado() {
        // Arrange
        when(clienteRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<Cliente> clienteEncontrado = clienteService.obterPorId(99L);

        // Assert
        assertFalse(clienteEncontrado.isPresent());
        verify(clienteRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Deve deletar um cliente com sucesso")
    void testDeletar() {
        // Arrange
        doNothing().when(clienteRepository).delete(any(Cliente.class));

        // Act
        clienteService.deletar(cliente);

        // Assert
        verify(clienteRepository, times(1)).delete(cliente);
    }

    @Test
    @DisplayName("Deve retornar true se o cliente possuir conta")
    void testPossuiConta_True() {
        // Arrange
        when(contaRepository.existsByCliente(cliente)).thenReturn(true);

        // Act
        boolean possuiConta = clienteService.possuiConta(cliente);

        // Assert
        assertTrue(possuiConta);
        verify(contaRepository, times(1)).existsByCliente(cliente);
    }

    @Test
    @DisplayName("Deve retornar false se o cliente não possuir conta")
    void testPossuiConta_False() {
        // Arrange
        when(contaRepository.existsByCliente(cliente)).thenReturn(false);

        // Act
        boolean possuiConta = clienteService.possuiConta(cliente);

        // Assert
        assertFalse(possuiConta);
        verify(contaRepository, times(1)).existsByCliente(cliente);
    }
}