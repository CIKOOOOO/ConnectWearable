package com.example.connectwearable.model;

public class Smartwatch {

    private String name, nodesID;
    private boolean choose;

    public Smartwatch(String name, String nodesID) {
        this.name = name;
        this.nodesID = nodesID;
        setChoose(false);
    }

    public boolean isChoose() {
        return choose;
    }

    public void setChoose(boolean choose) {
        this.choose = choose;
    }

    public String getName() {
        return name;
    }

    public String getNodesID() {
        return nodesID;
    }
}
