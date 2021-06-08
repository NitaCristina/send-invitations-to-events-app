package com.example.finalproject.exceptions;

public class InvalidInvitationStatusException extends RuntimeException{

    private String message;

    public InvalidInvitationStatusException(String message) {
        super(message);
    }
}
