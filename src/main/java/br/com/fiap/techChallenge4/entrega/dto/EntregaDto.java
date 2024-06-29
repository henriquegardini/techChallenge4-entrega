package br.com.fiap.techChallenge4.entrega.dto;

import br.com.fiap.techChallenge4.entrega.model.EtapaEntrega;
import br.com.fiap.techChallenge4.entrega.model.StatusEntrega;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class EntregaDto {

    private Long idEntrega;
    private Long idPedido;
    private String nomeEntregador;
    private StatusEntrega statusEntrega;
    private EtapaEntrega etapaEntrega;
    private LocalDate dataEntrega;

}
