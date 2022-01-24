package com.damico958.desafio_tecnico.exceptions;

public class InvalidBehaviorException extends RuntimeException{
    public InvalidBehaviorException(String error){
        super(error);
    }
    public InvalidBehaviorException(String error, Throwable rootException){
        super(error, rootException);
    }
}
