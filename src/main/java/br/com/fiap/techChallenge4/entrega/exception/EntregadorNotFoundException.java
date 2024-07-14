package br.com.fiap.techChallenge4.entrega.exception;

public class EntregadorNotFoundException extends RuntimeException {
    public EntregadorNotFoundException() {
        super("Entregador não encontrado!");
    }
}
