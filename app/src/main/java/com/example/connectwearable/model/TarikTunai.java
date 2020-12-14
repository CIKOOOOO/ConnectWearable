package com.example.connectwearable.model;

public class TarikTunai {

    private String tarik_tunai_id;
    private String transaction_code;
    private String issue_date;
    private String account_number;
    private Double amount;
    private String end_date;

    public TarikTunai() {
    }

    public String getTarik_tunai_id() {
        return tarik_tunai_id;
    }

    public void setTarik_tunai_id(String tarik_tunai_id) {
        this.tarik_tunai_id = tarik_tunai_id;
    }

    public String getTransaction_code() {
        return transaction_code;
    }

    public void setTransaction_code(String transaction_code) {
        this.transaction_code = transaction_code;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getAccount_number() {
        return account_number;
    }

    public void setAccount_number(String account_number) {
        this.account_number = account_number;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
    }

    public String getEnd_date() {
        return end_date;
    }

    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
}
