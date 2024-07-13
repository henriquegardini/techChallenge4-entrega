package br.com.fiap.techChallenge4.entrega.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private Long id;
    private Long client;
    private List<ProdutoDTO> product;
    private Integer qtde;
    private LocalDate oderDate;
    private BigDecimal totalValue;
    private String status;

}
