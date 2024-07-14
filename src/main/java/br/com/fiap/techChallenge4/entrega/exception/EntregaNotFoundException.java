package br.com.fiap.techChallenge4.entrega.exception;

public class EntregaNotFoundException extends RuntimeException {
    public EntregaNotFoundException() {
        super("Entrega não encontrada!");
    }
}
