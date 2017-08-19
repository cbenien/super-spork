package com.superspork.web;

public class UserData
{
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        UserData userData = (UserData) o;

        if (userName != null ? !userName.equals(userData.userName) : userData.userName != null) return false;
        if (email != null ? !email.equals(userData.email) : userData.email != null) return false;
        return fullName != null ? fullName.equals(userData.fullName) : userData.fullName == null;
    }

    public UserData() {
    }

    public UserData(String userId, String userName, String email, String fullName) {
        this.userId = userId;
        this.userName = userName;
        this.email = email;
        this.fullName = fullName;
    }

    @Override

    public int hashCode() {
        int result = userName != null ? userName.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (fullName != null ? fullName.hashCode() : 0);
        return result;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

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


    private String userId;
    private String userName;
    private String email;
    private String fullName;

}
