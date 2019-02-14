package com.example.android.cookpit.pojoClass;

/**
 * Created by alexandrelevieux on 23/01/2017.
 */
public class Dish {
    private String name;
    private String username;
    private String description;
    private int id;

    public Dish() {

    }

    public Dish(int id, String Name, String Username, String Description) {
        this.id = id;
        this.name = Name;
        this.username = Username;
        this.description = Description;

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

    public String getDescription() {
        return this.description;
    }

    public void setDescription(String Description) {
        this.description = Description;
    }


}

