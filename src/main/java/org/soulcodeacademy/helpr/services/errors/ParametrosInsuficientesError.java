package org.soulcodeacademy.helpr.services.errors;

// Error de ausencia de parametros
public class ParametrosInsuficientesError extends RuntimeException{
    public ParametrosInsuficientesError(String message) {
        super(message);
    }
}
