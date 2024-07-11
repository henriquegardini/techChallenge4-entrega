package br.com.fiap.techChallenge4.entrega.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EntregadorRequestDto {

    private Long id;
    private String nome;
    private Integer quantidadeEntregas;
    private Integer cepInicial;
    private Integer cepFinal;

}
