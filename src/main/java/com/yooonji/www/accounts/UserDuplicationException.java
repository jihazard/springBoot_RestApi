package com.yooonji.www.accounts;

public class UserDuplicationException extends RuntimeException{

    String username;

    public UserDuplicationException(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }
}
