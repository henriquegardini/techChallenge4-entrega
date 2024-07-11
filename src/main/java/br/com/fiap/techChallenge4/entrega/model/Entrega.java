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
    @Column(name = "id")
    private Long id;

    @NotNull
    @Positive
    @Column(name = "id_pedido")
    private Long idPedido;

    @Column(name = "id_entregador")
    private Long idEntregador;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEntrega status;

    @Enumerated(EnumType.STRING)
    @Column(name = "etapa")
    private EtapaEntrega etapa;

    @Column(name = "data_estimada")
    private LocalDate dataEstimada;

    @Column(name = "data_realizada")
    private LocalDate dataRealizada;

    @Column(name = "nome_receptor")
    private String nomeReceptor;

}
