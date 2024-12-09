package br.com.vr.miniautorizador.autorizador.controller;


import br.com.vr.miniautorizador.autorizador.service.CartaoService;
import br.com.vr.miniautorizador.autorizador.dto.CartaoRequest;
import br.com.vr.miniautorizador.autorizador.dto.TransacaoRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CartaoControllerTest {

    @Mock
    private CartaoService service;

    @InjectMocks
    private CartaoController controller;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void criarCartao_sucesso() {
        CartaoRequest request = new CartaoRequest("123456789", "1234");

        when(service.criarCartao(request)).thenReturn(null); // Ajuste para o retorno esperado

        ResponseEntity<?> response = controller.criarCartao(request);

        assertEquals(201, response.getStatusCodeValue());
        verify(service, times(1)).criarCartao(request);
    }

    @Test
    void obterSaldo_sucesso() {
        String numeroCartao = "123456789";
        BigDecimal saldoEsperado = BigDecimal.valueOf(500.00);

        when(service.obterSaldo(numeroCartao)).thenReturn(saldoEsperado);

        ResponseEntity<?> response = controller.obterSaldo(numeroCartao);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(saldoEsperado, response.getBody());
        verify(service, times(1)).obterSaldo(numeroCartao);
    }

    @Test
    void obterSaldo_cartaoNaoEncontrado() {
        String numeroCartao = "123456789";

        when(service.obterSaldo(numeroCartao)).thenThrow(new RuntimeException("Cartão não encontrado"));

        ResponseEntity<?> response = controller.obterSaldo(numeroCartao);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
        verify(service, times(1)).obterSaldo(numeroCartao);
    }

    @Test
    void realizarTransacao_sucesso() {
        TransacaoRequest request = new TransacaoRequest("123456789", "1234", BigDecimal.valueOf(50.00));

        doNothing().when(service).realizarTransacao(request);

        ResponseEntity<?> response = controller.realizarTransacao(request);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("OK", response.getBody());
        verify(service, times(1)).realizarTransacao(request);
    }

    @Test
    void realizarTransacao_falha() {
        TransacaoRequest request = new TransacaoRequest("123456789", "1234", BigDecimal.valueOf(150.00));

        doThrow(new IllegalArgumentException("Saldo insuficiente")).when(service).realizarTransacao(request);

        ResponseEntity<?> response = controller.realizarTransacao(request);

        assertEquals(422, response.getStatusCodeValue());
        assertEquals("Saldo insuficiente", response.getBody());
        verify(service, times(1)).realizarTransacao(request);
    }
}
