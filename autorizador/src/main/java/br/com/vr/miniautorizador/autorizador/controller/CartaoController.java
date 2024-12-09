package br.com.vr.miniautorizador.autorizador.controller;


import br.com.vr.miniautorizador.autorizador.dto.CartaoRequest;
import br.com.vr.miniautorizador.autorizador.dto.TransacaoRequest;
import br.com.vr.miniautorizador.autorizador.service.CartaoService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/cartoes")
public class CartaoController {

    private final CartaoService service;

    public CartaoController(CartaoService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<?> criarCartao(@RequestBody CartaoRequest request) {
        try {
            return new ResponseEntity<>(service.criarCartao(request), HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }

    @GetMapping("/{numeroCartao}")
    public ResponseEntity<?> obterSaldo(@PathVariable String numeroCartao) {
        try {
            return new ResponseEntity<>(service.obterSaldo(numeroCartao), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/transacoes")
    public ResponseEntity<?> realizarTransacao(@RequestBody TransacaoRequest request) {
        try {
            service.realizarTransacao(request);
            return new ResponseEntity<>("OK", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
        }
    }
}