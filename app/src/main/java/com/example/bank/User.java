package com.example.bank;

public class User {
    String id;
    String fname;
    String lname;
    String username;
    String password;
    String balance;

    public User(String id, String fname, String lname, String username, String password, String balance) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
        this.password = password;
        this.balance = balance;
    }

    public String getId() {
        return id;
    }

    public String getFname() {
        return fname;
    }

    public String getLname() {
        return lname;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public String getBalance() {
        return balance;
    }
}
