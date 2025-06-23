package com.vynicius.estagio.service;

import com.vynicius.estagio.model.Cliente;
import com.vynicius.estagio.model.Conta;
import com.vynicius.estagio.model.ContaSituacao;
import com.vynicius.estagio.repository.ContaRepository;
import com.vynicius.estagio.validator.ContaValidator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ContaServiceTest {

    @Mock
    private ContaRepository contaRepository;

    @Mock
    private ContaValidator validator;

    @InjectMocks
    private ContaService contaService;

    private Conta conta;
    private Cliente cliente;

    @BeforeEach
    void setUp() {
        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNome("João da Silva");

        conta = new Conta();
        conta.setId(10L);
        conta.setCliente(cliente);
        conta.setValor(new BigDecimal("1000.00"));
        conta.setSituacao(ContaSituacao.PENDENTE);
    }

    @Test
    @DisplayName("Deve salvar uma conta com sucesso após validação")
    void testSalvarComSucesso() {
        // Arrange
        doNothing().when(validator).validar(any(Conta.class)); // Simula que a validação passou
        when(contaRepository.save(any(Conta.class))).thenReturn(conta);

        // Act
        Conta contaSalva = contaService.salvar(conta);

        // Assert
        assertNotNull(contaSalva);
        assertEquals(conta.getId(), contaSalva.getId());

        // Verifica se o validador e o repositório foram chamados na ordem correta
        verify(validator, times(1)).validar(conta);
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    @DisplayName("Deve lançar exceção e não salvar se a validação falhar")
    void testSalvar_ComFalhaNaValidacao() {
        // Arrange
        // Simula que a validação falhou, lançando uma exceção
        doThrow(new IllegalArgumentException("Regra de negócio violada"))
                .when(validator).validar(any(Conta.class));

        // Act & Assert
        // Verifica se a exceção esperada é lançada pelo método salvar
        assertThrows(IllegalArgumentException.class, () -> {
            contaService.salvar(conta);
        });

        // Garante que o método save NUNCA foi chamado se a validação falhou
        verify(contaRepository, never()).save(any(Conta.class));
    }

    @Test
    @DisplayName("Deve 'deletar' uma conta mudando sua situação para CANCELADA")
    void testDeletar() {
        // Arrange
        // Nenhuma configuração específica necessária, pois o método não retorna valor

        // Act
        contaService.deletar(conta);

        // Assert
        // Verifica se a situação da conta foi alterada para CANCELADA
        assertEquals(ContaSituacao.CANCELADA, conta.getSituacao());
        // Verifica se o repositório foi chamado para salvar a conta com a nova situação
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    @DisplayName("Deve obter uma conta por ID quando ela existir")
    void testObterPorId_Encontrado() {
        // Arrange
        when(contaRepository.findById(10L)).thenReturn(Optional.of(conta));

        // Act
        Optional<Conta> resultado = contaService.obterPorId(10L);

        // Assert
        assertTrue(resultado.isPresent());
        assertEquals(conta, resultado.get());
        verify(contaRepository, times(1)).findById(10L);
    }

    @Test
    @DisplayName("Deve retornar Optional vazio para um ID de conta inexistente")
    void testObterPorId_NaoEncontrado() {
        // Arrange
        when(contaRepository.findById(anyLong())).thenReturn(Optional.empty());

        // Act
        Optional<Conta> resultado = contaService.obterPorId(99L);

        // Assert
        assertFalse(resultado.isPresent());
        verify(contaRepository, times(1)).findById(99L);
    }

    @Test
    @DisplayName("Deve atualizar uma conta com sucesso")
    void testAtualizar() {
        // Arrange
        // Nenhuma configuração necessária para o mock, apenas verificaremos a chamada

        // Act
        contaService.atualizar(conta);

        // Assert
        verify(contaRepository, times(1)).save(conta);
    }

    @Test
    @DisplayName("Deve listar todas as contas de um cliente específico")
    void testListarContas() {
        // Arrange
        when(contaRepository.findByCliente(cliente)).thenReturn(List.of(conta));

        // Act
        List<Conta> contas = contaService.listarContas(cliente);

        // Assert
        assertFalse(contas.isEmpty());
        assertEquals(1, contas.size());
        assertEquals(conta, contas.get(0));
        verify(contaRepository, times(1)).findByCliente(cliente);
    }
}