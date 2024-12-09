package br.com.vr.miniautorizador.autorizador.exception;

public class CartaoInexistenteException extends RuntimeException {
    public CartaoInexistenteException(String message) {
        super(message);
    }
}
