package br.com.fiap.techChallenge4.entrega.dto;

import br.com.fiap.techChallenge4.entrega.model.Entrega;
import br.com.fiap.techChallenge4.entrega.model.EtapaEntrega;
import br.com.fiap.techChallenge4.entrega.model.Pedido;
import br.com.fiap.techChallenge4.entrega.model.StatusEntrega;

import java.time.LocalDate;

public record EntregaExibicaoDto(
        Long idEntrega,
        Long idPedido,
        String nomeEntregador,
        StatusEntrega statusEntrega,
        EtapaEntrega etapaEntrega,
        LocalDate dataEntrega
//        Pedido pedido
) {
    public EntregaExibicaoDto(Entrega entrega) {
        this(
                entrega.getIdEntrega(),
                entrega.getIdPedido(),
                entrega.getNomeEntregador(),
                entrega.getStatusEntrega(),
                entrega.getEtapaEntrega(),
                entrega.getDataEntrega()
//                entrega.getPedido()
        );
    }
}
