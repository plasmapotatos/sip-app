package com.example.firebasemessagingtrial2;

import java.lang.reflect.Array;
import java.util.ArrayList;
/*
    User class stores all of the user's information.
 */
public class User {
    public String username, email;

    public User() {

    }

    public User(String username, String email) {
        this.username = username;
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}