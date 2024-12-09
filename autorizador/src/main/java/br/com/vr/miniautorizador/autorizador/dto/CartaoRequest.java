package br.com.vr.miniautorizador.autorizador.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class CartaoRequest {
    private String numeroCartao;
    private String senha;
}