package com.example.android.cookpit.Model.pojoClass;

import javax.annotation.Nullable;

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


    public String getName() {
        return this.name;
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

