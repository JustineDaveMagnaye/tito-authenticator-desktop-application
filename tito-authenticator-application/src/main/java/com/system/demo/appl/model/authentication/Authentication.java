package com.system.demo.appl.model.authentication;

import java.sql.Timestamp;

public class Authentication {
    private int id;
    private String employeeNo;
    private String authenticationCode;
    private Timestamp createdAt;
    private Timestamp updatedAt;

    public Authentication() {
    }

    public Authentication(int id, String employeeNo, String authenticationCode, Timestamp createdAt, Timestamp updatedAt) {
        this.id = id;
        this.employeeNo = employeeNo;
        this.authenticationCode = authenticationCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmployeeNo() {
        return employeeNo;
    }

    public void setEmployeeNo(String employeeNo) {
        this.employeeNo = employeeNo;
    }

    public String getAuthenticationCode() {
        return authenticationCode;
    }

    public void setAuthenticationCode(String authenticationCode) {
        this.authenticationCode = authenticationCode;
    }

    public Timestamp getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Timestamp createdAt) {
        this.createdAt = createdAt;
    }

    public Timestamp getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Timestamp updatedAt) {
        this.updatedAt = updatedAt;
    }
}
