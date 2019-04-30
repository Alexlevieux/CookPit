package com.example.android.cookpit.Model.pojoClass;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by alexandrelevieux on 23/01/2017.
 */
public class Mep implements Parcelable {
    private int pojoId = 3;
    private String name;
    private String username;
    private String description;
    private int id;

    public Mep() {

    }

    public Mep(int id, String Name, String Username, String Description) {

        this.id = id;
        this.name = Name;
        this.username = Username;
        this.description = Description;

    }

    protected Mep(Parcel in) {
        pojoId = in.readInt();
        name = in.readString();
        username = in.readString();
        description = in.readString();
        id = in.readInt();
    }

    public static final Creator<Mep> CREATOR = new Creator<Mep>() {
        @Override
        public Mep createFromParcel(Parcel in) {
            return new Mep(in);
        }

        @Override
        public Mep[] newArray(int size) {
            return new Mep[size];
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
        dest.writeInt(pojoId);
        dest.writeString(name);
        dest.writeString(username);
        dest.writeString(description);
        dest.writeInt(id);
    }
}
