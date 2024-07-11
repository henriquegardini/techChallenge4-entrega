package br.com.fiap.techChallenge4.entrega.dto;

import br.com.fiap.techChallenge4.entrega.model.Entrega;
import br.com.fiap.techChallenge4.entrega.model.EtapaEntrega;
import br.com.fiap.techChallenge4.entrega.model.StatusEntrega;

import java.time.LocalDate;

public record EntregaExibicaoDto(
        Long id,
        Long idPedido,
        Long idEntregador,
        StatusEntrega status,
        EtapaEntrega etapa,
        LocalDate dataEstimada,
        LocalDate dataRealizada,
        String nomeReceptor
) {
    public EntregaExibicaoDto(Entrega entrega) {
        this(
                entrega.getId(),
                entrega.getIdPedido(),
                entrega.getIdEntregador(),
                entrega.getStatus(),
                entrega.getEtapa(),
                entrega.getDataEstimada(),
                entrega.getDataRealizada(),
                entrega.getNomeReceptor()
        );
    }
}
