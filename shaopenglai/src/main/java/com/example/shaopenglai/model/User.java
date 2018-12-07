package com.example.shaopenglai.model;

public class User {
    private String name;
    private String verification;

    public User(String name, String verification) {
        this.name = name;
        this.verification = verification;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVerification() {
        return verification;
    }

    public void setVerification(String verification) {
        this.verification = verification;
    }
}
