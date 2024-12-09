package br.com.vr.miniautorizador.autorizador.automacao;

import br.com.vr.miniautorizador.autorizador.dto.CartaoRequest;
import br.com.vr.miniautorizador.autorizador.model.Cartao;
import br.com.vr.miniautorizador.autorizador.service.CartaoService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class CartaoServiceAutomacaoTest {
    @Autowired
    private CartaoService service;

    @Test
    void criarCartaoTest() {
        CartaoRequest request = new CartaoRequest("6549873025634501", "1234");
        Cartao cartao = service.criarCartao(request);
        assertEquals(request.getNumeroCartao(), cartao.getNumeroCartao());
    }
}