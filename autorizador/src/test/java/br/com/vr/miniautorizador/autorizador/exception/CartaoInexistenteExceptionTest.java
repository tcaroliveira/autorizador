package br.com.vr.miniautorizador.autorizador.exception;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CartaoInexistenteExceptionTest {

    @Test
    void criarExcecaoComMensagem() {
        String mensagem = "Cartão não encontrado";

        CartaoInexistenteException exception = new CartaoInexistenteException(mensagem);

        assertEquals(mensagem, exception.getMessage());
    }

    @Test
    void herancaDeRuntimeException() {
        CartaoInexistenteException exception = new CartaoInexistenteException("Cartão não encontrado");

        assertTrue(exception instanceof RuntimeException);
    }
}