package br.com.fiap.techChallenge4.entrega.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Pedido {

    private Long id;
    private Long client;
    private Long product;
    private Integer qtde;
    private LocalDate oderDate;
    private BigDecimal totalValue;
    private String statusPedido;

}
