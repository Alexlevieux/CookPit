package com.example.android.cookpit.Model.firebaseProvider;

import com.example.android.cookpit.Model.Cloud;
import com.example.android.cookpit.Model.pojoClass.sequence;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public interface FirebaseModifier {


    sequence getSequence();

    Object getObject();

    ArrayList getObjectArrayList();
}
