package com.example.connectwearable.model;

public class DeviceAuthenticate {

    private String authenticate_id;
    private String authenticate_code;
    private String issue_date;
    private String smartwatch_nodes_id;
    private String smartphone_nodes_id;
    private String active;
    private String user_id;
    private String remove_date;

    public DeviceAuthenticate() {
    }

    public String getAuthenticate_id() {
        return authenticate_id;
    }

    public void setAuthenticate_id(String authenticate_id) {
        this.authenticate_id = authenticate_id;
    }

    public String getAuthenticate_code() {
        return authenticate_code;
    }

    public void setAuthenticate_code(String authenticate_code) {
        this.authenticate_code = authenticate_code;
    }

    public String getIssue_date() {
        return issue_date;
    }

    public void setIssue_date(String issue_date) {
        this.issue_date = issue_date;
    }

    public String getSmartwatch_nodes_id() {
        return smartwatch_nodes_id;
    }

    public void setSmartwatch_nodes_id(String smartwatch_nodes_id) {
        this.smartwatch_nodes_id = smartwatch_nodes_id;
    }

    public String getSmartphone_nodes_id() {
        return smartphone_nodes_id;
    }

    public void setSmartphone_nodes_id(String smartphone_nodes_id) {
        this.smartphone_nodes_id = smartphone_nodes_id;
    }

    public String getActive() {
        return active;
    }

    public void setActive(String active) {
        this.active = active;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getRemove_date() {
        return remove_date;
    }

    public void setRemove_date(String remove_date) {
        this.remove_date = remove_date;
    }
}
