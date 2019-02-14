package com.example.android.cookpit.pojoClass;

/**
 * Created by alexandrelevieux on 23/01/2017.
 */

public class Menu {
    private int pojoId = 1;
    private String name;
    private String username;
    private int id;

    public Menu() {

    }

    public Menu(int id, String Name, String Username) {
        this.id = id;
        this.name = Name;
        this.username = Username;

    }

    public int getPojoId() {
        return this.pojoId;
    }

    public int getId() {
        return this.id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String Name) {
        this.name = Name;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String Username) {
        this.username = Username;
    }

}
