package com.elavarasanno3.amazonclone.model;

public class SignInRequest {
    private String emailId;
    private String password;

    public SignInRequest() {
        // Default constructor
    }

    public SignInRequest(String emailId, String password) {
        this.emailId = emailId;
        this.password = password;
    }

    // Getters and setters
    public String getEmailId() {
        return emailId;
    }

    public void setEmail(String emailId) {
        this.emailId = emailId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
