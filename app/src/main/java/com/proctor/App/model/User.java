package com.proctor.App.model;

import com.orm.SugarRecord;

/**
 * Created by MOHIT KUMAR on 7/13/2015.
 */
public class User extends SugarRecord {
    protected String username;
    protected String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}