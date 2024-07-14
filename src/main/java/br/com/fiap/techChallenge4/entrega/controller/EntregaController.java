package br.com.fiap.techChallenge4.entrega.controller;

import br.com.fiap.techChallenge4.entrega.dto.AtualizaEntregaRequestDto;
import br.com.fiap.techChallenge4.entrega.dto.CriarEntregaRequestDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregaExibicaoDto;
import br.com.fiap.techChallenge4.entrega.dto.EntregaResponseDTO;
import br.com.fiap.techChallenge4.entrega.dto.FinalizaEntregaRequestDto;
import br.com.fiap.techChallenge4.entrega.exception.EntregaNotFoundException;
import br.com.fiap.techChallenge4.entrega.service.EntregaService;
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
@RequestMapping("/entrega")
public class EntregaController {

    @Autowired
    private EntregaService service;

    @PostMapping
    public ResponseEntity<EntregaResponseDTO> criarEntrega(@RequestBody @Valid CriarEntregaRequestDto entrega) {
        return ResponseEntity.ok(service.criarEntrega(entrega));
    }

    @GetMapping("{idEntrega}")
    public EntregaResponseDTO buscarEntrega(@PathVariable Long idEntrega) {
        return service.buscarEntrega(idEntrega);
    }

    @GetMapping
    public ResponseEntity<List<EntregaExibicaoDto>> listarEntregas() {
        return ResponseEntity.ok(service.listarEntregas());
    }

    @DeleteMapping("{idEntrega}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirEntrega(@PathVariable Long idEntrega) {
        service.excluirEntrega(idEntrega);
    }

    @PutMapping
    public void atualizarEntrega(@RequestBody AtualizaEntregaRequestDto entrega) {
        service.atualizarEntrega(entrega);
    }

    @PostMapping("/finalizar")
    public void finalizaEntrega(@RequestBody @Valid FinalizaEntregaRequestDto entrega) {
        service.finalizaEntrega(entrega);
    }

}
