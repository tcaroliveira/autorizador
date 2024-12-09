package br.com.vr.miniautorizador.autorizador.service;


import br.com.vr.miniautorizador.autorizador.dto.CartaoRequest;
import br.com.vr.miniautorizador.autorizador.dto.TransacaoRequest;
import br.com.vr.miniautorizador.autorizador.exception.CartaoInexistenteException;
import br.com.vr.miniautorizador.autorizador.model.Cartao;
import br.com.vr.miniautorizador.autorizador.repository.CartaoRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CartaoService {
    private final CartaoRepository repository;

    public CartaoService(CartaoRepository repository) {
        this.repository = repository;
    }

    public Cartao criarCartao(CartaoRequest request) {
        if (repository.findByNumeroCartao(request.getNumeroCartao()).isPresent()) {
            throw new IllegalArgumentException("Cartão já existe!");
        }
        Cartao cartao = new Cartao(request.getNumeroCartao(), request.getSenha());
        return repository.save(cartao);
    }

    public BigDecimal obterSaldo(String numeroCartao) {
        Cartao cartao = repository.findByNumeroCartao(numeroCartao).orElseThrow(() -> new CartaoInexistenteException("Cartão não encontrado."));
        return cartao.getSaldo();
    }

    public void realizarTransacao(TransacaoRequest request) {
        Cartao cartao = repository.findByNumeroCartao(request.getNumeroCartao()).orElseThrow(() -> new CartaoInexistenteException("Cartão não encontrado."));

        if (!cartao.getSenha().equals(request.getSenhaCartao())) {
            throw new IllegalArgumentException("Senha inválida.");
        }

        if (cartao.getSaldo().compareTo(request.getValor()) < 0) {
            throw new IllegalArgumentException("Saldo insuficiente.");
        }

        cartao.setSaldo(cartao.getSaldo().subtract(request.getValor()));
        repository.save(cartao);
    }
}
