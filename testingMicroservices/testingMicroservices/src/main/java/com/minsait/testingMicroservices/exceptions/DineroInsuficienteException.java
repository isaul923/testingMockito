package com.minsait.testingMicroservices.exceptions;

public class DineroInsuficienteException extends RuntimeException{

    public DineroInsuficienteException(String mensaje){
        super(mensaje);
    }
}
