package com.example.connectwearable.model;

public class Event {

    private String response;
    private String transactionCode;
    private String dueDate;

    public Event() {
    }

    public String getDueDate() {
        return dueDate;
    }

    public String getResponse() {
        return response;
    }

    public String getTransactionCode() {
        return transactionCode;
    }
}
