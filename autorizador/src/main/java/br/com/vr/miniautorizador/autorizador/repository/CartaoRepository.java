package br.com.vr.miniautorizador.autorizador.repository;


import br.com.vr.miniautorizador.autorizador.model.Cartao;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface CartaoRepository extends MongoRepository<Cartao, String> {
    Optional<Cartao> findByNumeroCartao(String numeroCartao);
}
