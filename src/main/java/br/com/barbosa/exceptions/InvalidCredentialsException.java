package br.com.barbosa.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException() {
        super("Credenciais inválidas.");
    }

    public InvalidCredentialsException(String message) {
        super(message);
    }
}
