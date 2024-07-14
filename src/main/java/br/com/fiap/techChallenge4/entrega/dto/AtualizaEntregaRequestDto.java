package br.com.fiap.techChallenge4.entrega.dto;

import br.com.fiap.techChallenge4.entrega.model.EtapaEntrega;
import br.com.fiap.techChallenge4.entrega.model.StatusEntrega;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class AtualizaEntregaRequestDto {

    private Long id;
    private StatusEntrega status;
    private EtapaEntrega etapa;
    private LocalDate dataEstimada;
    private LocalDate dataRealizada;
    private String nomeReceptor;

}
