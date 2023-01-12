package com.clan.reportsService.exceptions.general;

public class DataNotValidException extends Exception{

    public DataNotValidException() {
    }

    public DataNotValidException(String message) {
        super(message);
    }

    public DataNotValidException(String message, Throwable cause) {
        super(message, cause);
    }
}
