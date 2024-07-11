package br.com.fiap.techChallenge4.entrega.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
public class PedidoDTO {

    private Long id;
    private Long client;
    private Long product;
    private Integer qtde;
    private LocalDate oderDate;
    private BigDecimal totalValue;
    private String statusPedido;

}