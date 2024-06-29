CREATE TABLE tbl_entregadores(
    id_entregador BIGINT(20) NOT NULL AUTO_INCREMENT,
    nome_entregador VARCHAR(100) NOT NULL,
    cep_entrega VARCHAR(20) NOT NULL,
    PRIMARY KEY (id_entregador)
);