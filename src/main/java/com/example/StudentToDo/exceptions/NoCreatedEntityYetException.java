package com.example.StudentToDo.exceptions;

import lombok.Builder;

public class NoCreatedEntityYetException extends RuntimeException{

    public NoCreatedEntityYetException(String message){
        super(message);
    }
}
