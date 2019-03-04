package com.example.android.cookpit.pojoClass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexandrelevieux on 23/01/2017.
 */

public class Menu implements Parcelable {
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

    protected Menu(Parcel in) {
        pojoId = in.readInt();
        name = in.readString();
        username = in.readString();
        id = in.readInt();
    }

    public static final Creator<Menu> CREATOR = new Creator<Menu>() {
        @Override
        public Menu createFromParcel(Parcel in) {
            return new Menu(in);
        }

        @Override
        public Menu[] newArray(int size) {
            return new Menu[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(pojoId);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeInt(id);
    }
}
