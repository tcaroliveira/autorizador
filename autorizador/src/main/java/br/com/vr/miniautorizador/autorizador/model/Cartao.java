package br.com.vr.miniautorizador.autorizador.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;



@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "cartoes")
public class Cartao {
    @Id
    private String numeroCartao;
    private String senha;
    private BigDecimal saldo;


    public Cartao(String numeroCartao, String senha) {
        this.numeroCartao = numeroCartao;
        this.senha = senha;
        this.saldo = BigDecimal.valueOf(500.00);
    }

}