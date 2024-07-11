package br.com.fiap.techChallenge4.entrega.controller;

import br.com.fiap.techChallenge4.entrega.dto.EntregadorDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregadorExibicaoDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregadorRequestDto;
import br.com.fiap.techChallenge4.entrega.service.EntregadorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/entregador")
public class EntregadorController {

    @Autowired
    private EntregadorService service;

    @PostMapping
    public EntregadorExibicaoDto criarEntregador(@RequestBody @Valid EntregadorRequestDto entregadorRequestDto) {
        return service.criarEntregador(entregadorRequestDto);
    }

    @GetMapping("{idEntregador}")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EntregadorExibicaoDto> buscarEntregador(@PathVariable Long idEntregador) {
        return ResponseEntity.ok(service.buscarEntregador(idEntregador));
    }

    @GetMapping
    public ResponseEntity<List<EntregadorExibicaoDto>> listarEntregadores() {
        return ResponseEntity.ok(service.listarEntregadores());
    }

    @DeleteMapping("{idEntregador}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirEntregador(@PathVariable Long idEntregador) {
        service.excluirEntregador(idEntregador);
    }

    @PutMapping
    public EntregadorExibicaoDto atualizarEntregador(@RequestBody EntregadorDto entregadorDto) {
        return service.atualizarEntregador(entregadorDto);
    }

}
