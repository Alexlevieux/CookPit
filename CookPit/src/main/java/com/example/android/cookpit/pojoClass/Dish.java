package com.example.android.cookpit.pojoClass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexandrelevieux on 23/01/2017.
 */
public class Dish implements Parcelable {
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

    protected Dish(Parcel in) {
        name = in.readString();
        username = in.readString();
        description = in.readString();
        id = in.readInt();
    }

    public static final Creator<Dish> CREATOR = new Creator<Dish>() {
        @Override
        public Dish createFromParcel(Parcel in) {
            return new Dish(in);
        }

        @Override
        public Dish[] newArray(int size) {
            return new Dish[size];
        }
    };

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


    @Override
    public int describeContents() {

        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(description);
        dest.writeInt(id);
    }
}

