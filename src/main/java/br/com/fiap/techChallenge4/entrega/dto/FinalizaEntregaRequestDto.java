package br.com.fiap.techChallenge4.entrega.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class FinalizaEntregaRequestDto {

    private Long id;
    private LocalDate dataRealizada;
    private String nomeReceptor;

}
