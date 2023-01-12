package com.clan.reportsService.exceptions.general;

public class AlreadyExisistingException extends Exception{

    public AlreadyExisistingException() {
    }

    public AlreadyExisistingException(String message) {
        super(message);
    }

    public AlreadyExisistingException(String message, Throwable cause) {
        super(message, cause);
    }
}
