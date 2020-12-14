package com.example.connectwearable.model;

public class Event {

    private String response;
    private String transactionCode;
    private String dueDate;

    public Event() {
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setTransactionCode(String transactionCode) {
        this.transactionCode = transactionCode;
    }

    public String getDueDate() {
        return dueDate;
    }

    public void setDueDate(String dueDate) {
        this.dueDate = dueDate;
    }

    public String getResponse() {
        return response;
    }

    public String getTransactionCode() {
        return transactionCode;
    }
}
