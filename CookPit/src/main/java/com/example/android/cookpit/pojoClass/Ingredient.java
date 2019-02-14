package com.example.android.cookpit.pojoClass;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by alexandrelevieux on 23/01/2017.
 */
public class Ingredient {

    private String name;
    private String catA;
    private String catB;
    private String catC;
    private int id;

    public Ingredient() {

    }

    public Ingredient(int id, String Name, String CatA, String CatB, String CatC) {

        this.id = id;
        this.name = Name;
        this.catA = CatA;
        this.catB = CatB;
        this.catC = CatC;

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

    public String getCatA() {
        return this.catA;
    }

    public void setCatA(String catAName) {
        this.catA = catAName;
    }

    public String getCatB() {
        return this.catB;
    }

    public void setCatB(String catBName) {
        this.catB = catBName;
    }

    public String getCatC() {
        return this.catC;
    }

    public void setCatC(String catCName) {
        this.catC = catCName;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("name", name);
        result.put("catA", catA);
        result.put("catB", catB);
        result.put("catC", catC);

        return result;
    }

}
