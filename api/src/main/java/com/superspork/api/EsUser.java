package com.superspork.api;

import java.util.Date;

public class EsUser
{
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public EsUser(String userName, String email, String fullName, Date creationDate) {
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
        this.creationDate = creationDate;
    }

    public EsUser() {
    }

    private String userName;
    private String email;
    private String fullName;

    private Date creationDate;
}
