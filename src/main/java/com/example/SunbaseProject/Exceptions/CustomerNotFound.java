package com.example.SunbaseProject.Exceptions;

public class CustomerNotFound extends RuntimeException{

    public CustomerNotFound(String message){
            super(message);
    }
}
