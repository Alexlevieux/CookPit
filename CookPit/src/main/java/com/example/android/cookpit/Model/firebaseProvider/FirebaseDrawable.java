package com.example.android.cookpit.Model.firebaseProvider;

import android.graphics.Picture;

import com.example.android.cookpit.Model.pojoClass.sequence;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class FirebaseDrawable implements FirebaseModifier {


    sequence seq;
    Picture picture;
    ArrayList<Picture> pictures;


    public FirebaseDrawable(Picture pic) {
        seq = new sequence();
        seq.setLevel(2);
        seq.setRootA("Pictures");
        seq.setRootB(0);
        picture = pic;


    }

    public FirebaseDrawable(ArrayList<Picture> pics) {
        seq = new sequence();
        seq.setLevel(1);
        seq.setRootA("Pictures");

        pictures = pics;


    }

    @Override
    public sequence getSequence() {
        return seq;
    }

    @Override
    public Object getObject() {
        return picture;

    }

    @Override
    public ArrayList getObjectArrayList() {
        return pictures;


    }
}
