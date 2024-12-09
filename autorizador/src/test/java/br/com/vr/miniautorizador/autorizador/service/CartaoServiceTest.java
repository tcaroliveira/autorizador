package br.com.vr.miniautorizador.autorizador.service;

import br.com.vr.miniautorizador.autorizador.dto.CartaoRequest;
import br.com.vr.miniautorizador.autorizador.dto.TransacaoRequest;
import br.com.vr.miniautorizador.autorizador.exception.CartaoInexistenteException;
import br.com.vr.miniautorizador.autorizador.model.Cartao;
import br.com.vr.miniautorizador.autorizador.repository.CartaoRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartaoServiceTest {

    @Mock
    private CartaoRepository repository;

    @InjectMocks
    private CartaoService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarCartao_sucesso() {
        CartaoRequest request = new CartaoRequest("123456789", "1234");
        Cartao cartao = new Cartao(request.getNumeroCartao(), request.getSenha());

        when(repository.findByNumeroCartao(request.getNumeroCartao())).thenReturn(Optional.empty());
        when(repository.save(any(Cartao.class))).thenReturn(cartao);

        Cartao result = service.criarCartao(request);

        assertEquals(request.getNumeroCartao(), result.getNumeroCartao());
        assertEquals(request.getSenha(), result.getSenha());
        assertEquals(BigDecimal.valueOf(500.00), result.getSaldo());
        verify(repository, times(1)).save(any(Cartao.class));
    }

    @Test
    void criarCartao_cartaoJaExiste() {
        CartaoRequest request = new CartaoRequest("123456789", "1234");

        when(repository.findByNumeroCartao(request.getNumeroCartao())).thenReturn(Optional.of(new Cartao()));

        assertThrows(IllegalArgumentException.class, () -> service.criarCartao(request));
        verify(repository, never()).save(any(Cartao.class));
    }

    @Test
    void obterSaldo_sucesso() {
        String numeroCartao = "123456789";
        Cartao cartao = new Cartao(numeroCartao, "1234");
        cartao.setSaldo(BigDecimal.valueOf(100.00));

        when(repository.findByNumeroCartao(numeroCartao)).thenReturn(Optional.of(cartao));

        BigDecimal saldo = service.obterSaldo(numeroCartao);

        assertEquals(BigDecimal.valueOf(100.00), saldo);
    }

    @Test
    void obterSaldo_cartaoNaoEncontrado() {
        String numeroCartao = "123456789";

        when(repository.findByNumeroCartao(numeroCartao)).thenReturn(Optional.empty());

        assertThrows(CartaoInexistenteException.class, () -> service.obterSaldo(numeroCartao));
    }

    @Test
    void realizarTransacao_sucesso() {
        String numeroCartao = "123456789";
        TransacaoRequest request = new TransacaoRequest(numeroCartao, "1234", BigDecimal.valueOf(50.00));
        Cartao cartao = new Cartao(numeroCartao, "1234");
        cartao.setSaldo(BigDecimal.valueOf(100.00));

        when(repository.findByNumeroCartao(numeroCartao)).thenReturn(Optional.of(cartao));

        service.realizarTransacao(request);

        assertEquals(BigDecimal.valueOf(50.00), cartao.getSaldo());
        verify(repository, times(1)).save(cartao);
    }

    @Test
    void realizarTransacao_senhaInvalida() {
        String numeroCartao = "123456789";
        TransacaoRequest request = new TransacaoRequest(numeroCartao, "4321", BigDecimal.valueOf(50.00));
        Cartao cartao = new Cartao(numeroCartao, "1234");
        cartao.setSaldo(BigDecimal.valueOf(100.00));

        when(repository.findByNumeroCartao(numeroCartao)).thenReturn(Optional.of(cartao));

        assertThrows(IllegalArgumentException.class, () -> service.realizarTransacao(request));
        verify(repository, never()).save(cartao);
    }

    @Test
    void realizarTransacao_saldoInsuficiente() {
        String numeroCartao = "123456789";
        TransacaoRequest request = new TransacaoRequest(numeroCartao, "1234", BigDecimal.valueOf(150.00));
        Cartao cartao = new Cartao(numeroCartao, "1234");
        cartao.setSaldo(BigDecimal.valueOf(100.00));

        when(repository.findByNumeroCartao(numeroCartao)).thenReturn(Optional.of(cartao));

        assertThrows(IllegalArgumentException.class, () -> service.realizarTransacao(request));
        verify(repository, never()).save(cartao);
    }
}