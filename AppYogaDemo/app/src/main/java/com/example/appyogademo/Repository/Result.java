package com.example.appyogademo.Repository;

public class Result {
    private boolean status;
    private String message;

    public Result(boolean status, String message) {
        this.status = status;
        this.message = message;
    }

    public boolean isStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}