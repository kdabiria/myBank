package com.example.bank;

public class User {
    String id;
    String fname;
    String lname;
    String username;
//    String password;
    String balance;
    String email;

    public User(String id, String fname, String lname, String username, String balance, String email) {
        this.id = id;
        this.fname = fname;
        this.lname = lname;
        this.username = username;
//        this.password = password;
        this.balance = balance;
        this.email = email;
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

//    public String getPassword() {
//        return password;
//    }

    public String getBalance() {
        return balance;
    }

    public String getEmail() {
        return email;
    }
}
