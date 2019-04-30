package com.example.android.cookpit.Model.firebaseProvider;

import android.graphics.Picture;
import android.graphics.drawable.Drawable;
import android.widget.Switch;

import com.example.android.cookpit.Model.Cloud;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class firebaseProvider {

    final static private String FIREBASE_DRAWABLE = "FirebaseDrawable";


    public static FirebaseModifier createOneEntry(Object object) {

        Class<?> type = object.getClass();
        String className = type.getName();
        FirebaseModifier firebaseModifier = null;


        switch (className) {
            case FIREBASE_DRAWABLE: {
                Picture picture = (Picture) object;
                firebaseModifier = new FirebaseDrawable(picture);

                break;
            }

        }


        return firebaseModifier;
    }

    public static void createBulkEntry(ArrayList object) {

        FirebaseModifier firebaseModifier = null;


    }

    public static void insertMultipleEntry(Object... object) {

        ArrayList<FirebaseModifier> firebaseModifier = null;


    }

}
