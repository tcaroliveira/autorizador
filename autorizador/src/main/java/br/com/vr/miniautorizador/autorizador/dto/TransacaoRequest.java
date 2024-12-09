package br.com.vr.miniautorizador.autorizador.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;


@Getter
@Setter
@AllArgsConstructor
public class TransacaoRequest {
    private String numeroCartao;
    private String senhaCartao;
    private BigDecimal valor;
}