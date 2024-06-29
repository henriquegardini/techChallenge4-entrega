CREATE TABLE tbl_entregas(
    id_entrega BIGINT(20) NOT NULL AUTO_INCREMENT,
    id_pedido BIGINT(20) NOT NULL,
    nome_entregador VARCHAR(100) NULL,
    status_entrega VARCHAR(20) NOT NULL,
    etapa_entrega VARCHAR(20) NOT NULL,
    data_entrega DATETIME NULL,
    PRIMARY KEY (id_entrega)
);