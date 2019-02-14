package com.example.android.cookpit.pojoClass;

/**
 * Created by alexandrelevieux on 13/12/2016.
 */

public class cookPitUser {
    public boolean user;
    public boolean admin;
    public String email;
    public String accountNumber;
    public String name;
    public long sequence;


    public long getSequence() {
        return sequence;
    }

    public void setSequence(long sequence) {
        this.sequence = sequence;
    }

    public cookPitUser() {


    }

    public cookPitUser(boolean admin, boolean user, String accountNumber, String email, String name, int sequence) {
        this.admin = admin;
        this.user = user;
        this.accountNumber = accountNumber;
        this.email = email;
        this.name = name;
        this.sequence = sequence;

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public boolean isUser() {
        return user;
    }

    public void setUser(boolean user) {
        this.user = user;
    }
}

