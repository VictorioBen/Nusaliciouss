package com.workspace.adminpanels.Model;

public class adminModel {
    String surename;
    String username;
    String password;

    public adminModel() {
        //Empty Constructor
    }

    public adminModel(String surename, String username, String password) {
        this.surename = surename;
        this.username = username;
        this.password = password;
    }
    //Getter n Setter
    public String getSurename() {
        return surename;
    }
    public void setSurename(String surename) {
        this.surename = surename;
    }

    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }

}
