package br.com.fiap.techChallenge4.entrega.dto;

import br.com.fiap.techChallenge4.entrega.model.Entrega;
import br.com.fiap.techChallenge4.entrega.model.Entregador;
import br.com.fiap.techChallenge4.entrega.model.EtapaEntrega;
import br.com.fiap.techChallenge4.entrega.model.StatusEntrega;

import java.time.LocalDate;

public record EntregadorExibicaoDto(
        Long id,
        String nome,
        Integer quantidadeEntregas,
        Integer cepInicial,
        Integer cepFinal
) {
    public EntregadorExibicaoDto(Entregador entregador) {
        this(
                entregador.getId(),
                entregador.getNome(),
                entregador.getQuantidadeEntregas(),
                entregador.getCepInicial(),
                entregador.getCepFinal()
        );
    }
}
