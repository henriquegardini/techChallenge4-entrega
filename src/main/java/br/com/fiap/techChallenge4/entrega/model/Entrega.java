package br.com.fiap.techChallenge4.entrega.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Entity
@Table(name = "tbl_entregas")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
public class Entrega {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrega")
    private Long idEntrega;

    @Column(name = "id_pedido")
    @NotNull
    @Positive
    private Long idPedido;

    @Column(name = "nome_entregador")
    private String nomeEntregador;

    @Enumerated(EnumType.STRING)
    @Column(name = "status_entrega")
    private StatusEntrega statusEntrega;

    @Enumerated(EnumType.STRING)
    @Column(name = "etapa_entrega")
    private EtapaEntrega etapaEntrega;

    @Column(name = "data_entrega")
    private LocalDate dataEntrega;

//    private Pedido pedido;

}
