package com.example.android.cookpit.Model;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.example.android.cookpit.Model.firebaseProvider.FirebaseModifier;
import com.example.android.cookpit.Model.firebaseProvider.firebaseProvider;
import com.example.android.cookpit.R;
import com.example.android.cookpit.Model.pojoClass.cookPitUser;
import com.example.android.cookpit.Model.pojoClass.sequence;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;


/**
 * Created by alexandrelevieux on 14/08/2017.
 */

public class Cloud {
    sequence[] sequences;
    ArrayList<FirebaseModifier> modifiers;

    TextView displayName;
    TextView userRegistration;
    Activity act;
    boolean isUserSync;
    boolean isSeqCountUpToDate;
    final FirebaseDatabase db = FirebaseDatabase.getInstance();
    DatabaseReference seq;
    FirebaseAuth mAuth;
    View progressArc;
    static public cookPitUser mUser = new cookPitUser();
    public SharedPreferences preferences;
    public SharedPreferences.Editor prefEditor;
    static private Cloud cloud;

    public DatabaseReference customerAccountRef;
    public DatabaseReference seqCount;
    public DatabaseReference sequence;
    public DatabaseReference customerDataRef;
    public DatabaseReference userAccountRef;
    public DatabaseReference userDataRef;


    public ValueEventListener listener = new ValueEventListener() {
        int i = 0;

        @Override
        public void onDataChange(DataSnapshot dataSnapshot) {

            if (i != 0) {

                mUser.setSequence((Long) dataSnapshot.getValue());
                syncUserWithFirebase();
            }
            i++;
        }

        @Override
        public void onCancelled(DatabaseError databaseError) {

        }

    };

    public void addOneEntry(Object object) {
        FirebaseModifier modifier = firebaseProvider.createOneEntry(object);
        modifiers.add(modifier);

    }

    public void insertModifiers(ArrayList<FirebaseModifier> firebaseModifiers) {
        FirebaseModifier[] modifierArray = (FirebaseModifier[]) firebaseModifiers.toArray();
        firebaseModifierTask.execute(modifierArray);
    }


    private AsyncTask firebaseModifierTask = new AsyncTask() {
        @Override
        protected void onPreExecute() {


            progressArc.setVisibility(View.VISIBLE);
            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] objects) {
            final FirebaseModifier[] modifiers = (FirebaseModifier[]) objects;

            customerDataRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    customerAccountRef.runTransaction(new Transaction.Handler() {
                        @NonNull
                        @Override
                        public Transaction.Result doTransaction(@NonNull MutableData mutableData) {
                            for (FirebaseModifier modifier : modifiers) {
                                com.example.android.cookpit.Model.pojoClass.sequence seq = modifier.getSequence();
                                ArrayList<Object> objects = modifier.getObjectArrayList();
                                Object object;
                                DatabaseReference refContent;


                                if (objects == null) {
                                    object = modifier.getObject();


                                    modifier.getReference()

                                } else {

                                }


                            }
                        }

                        @Override
                        public void onComplete(@Nullable DatabaseError databaseError, boolean b, @Nullable DataSnapshot dataSnapshot) {


                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
        }
    };

    private AsyncTask taskStart = new AsyncTask() {


        @Override
        protected void onPreExecute() {

            progressArc.setVisibility(View.VISIBLE);
            isUserSync = false;


            super.onPreExecute();
        }

        @Override
        protected Object doInBackground(Object[] params) {


            final DatabaseReference users = db.getReference("users");
            final DatabaseReference newUsers = users.child("newUsers");
            final FirebaseUser user = mAuth.getCurrentUser();
            final DatabaseReference uid = users.child(user.getUid());
            uid.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.hasChildren()) {
                        mUser.setEmail(dataSnapshot.child("email").getValue().toString());
                        mUser.setName(dataSnapshot.child("name").getValue().toString());
                        mUser.setAccountNumber(dataSnapshot.child("accountNumber").getValue().toString());
                        mUser.setSequence(-1);
                        mUser.setUser((boolean) dataSnapshot.child("user").getValue());
                        mUser.setAdmin((boolean) dataSnapshot.child("admin").getValue());
                        isUserSync = true;
                    }

                    if (mUser.getEmail() == null) {

                        newUsers.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                Log.v("test", "new users");

                                newUsers.runTransaction(new Transaction.Handler() {

                                    @Override
                                    public Transaction.Result doTransaction(MutableData mutableData) {

                                        if (mutableData.getValue() == null) {

                                            return Transaction.success(mutableData);
                                        } else {


                                            for (MutableData child : mutableData.getChildren()) {
                                                if (user.getEmail().equals(child.child("email").getValue())) {


                                                    mUser.setAdmin((boolean) (child.child("admin").getValue()));
                                                    mUser.setUser((boolean) (child.child("user").getValue()));
                                                    mUser.setAccountNumber((child.child("accountNumber").getValue().toString()));
                                                    mUser.setEmail((child.child("email").getValue().toString()));
                                                    mUser.setSequence(-1);
                                                    mUser.setName(user.getDisplayName());

                                                    uid.setValue(mUser);
                                                    child.setValue(null);
                                                    return Transaction.success(mutableData);

                                                }

                                            }

                                            return Transaction.success(mutableData);

                                        }


                                    }


                                    @Override
                                    public void onComplete(final DatabaseError databaseError, boolean b, DataSnapshot dataSnapshot) {

                                        if (mUser.getEmail() != null) {

                                            isUserSync = true;

                                        } else {


                                            mUser.setEmail(user.getEmail());
                                            mUser.setName(user.getDisplayName());
                                            mUser.setAccountNumber("acc01");
                                            mUser.setSequence(-1);
                                            mUser.setUser(false);
                                            mUser.setAdmin(false);

                                            uid.setValue(mUser);
                                            isUserSync = true;

                                        }


                                    }


                                });


                            }


                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }

                        });


                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });


            synchronized (this) {
                while (!isUserSync) {
                    try {
                        this.wait(200);
                    } catch (InterruptedException e) {
                    }


                }

                final DatabaseReference accounts = db.getReference("Customer account/Ireland/Free trial");
                final DatabaseReference accountSequence = accounts.child(mUser.accountNumber).child("seqCount");
                accountSequence.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        mUser.setSequence((long) dataSnapshot.getValue());
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                synchronized (this) {
                    while (mUser.getSequence() == -1) {
                        try {
                            this.wait(200);
                        } catch (InterruptedException e) {
                        }
                    }
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(Object o) {

            boolean firstUser = true;
            if (mUser != null) {
                setDatabaseReferences();
                firstUser = syncUserWithFirebase();
            }
            setSeqCountListener();

            if (firstUser) progressArc.setVisibility(View.GONE);
            Log.v(" search stopped", "test");


            updateUserMenu();
            super.onPostExecute(o);

        }
    };

    private void setDatabaseReferences() {
        cloud.customerAccountRef = FirebaseDatabase.getInstance().getReference("Customer account/Ireland/Free trial/" + Cloud.mUser.accountNumber);
        cloud.seqCount = customerAccountRef.child("seqCount");
        cloud.sequence = customerAccountRef.child("sequence");
        cloud.customerDataRef = FirebaseDatabase.getInstance().getReference("Customer Data/" + Cloud.mUser.accountNumber);
        cloud.userDataRef = FirebaseDatabase.getInstance().getReference("Users Data/" + mAuth.getUid());
        cloud.modifiers = new ArrayList<>();
        launchFirebaseModifierTask();
    }


    public interface cloudLoadCallBack {

        void onSequenceLoaded(sequence[] sequence);

        void onDataInserted();


    }

    private Cloud() {

    }

    static public Cloud startCloud(FirebaseAuth Auth, View cloudView, Activity activity) {
        cloud = new Cloud();
        cloud.act = activity;
        cloud.progressArc = cloudView;
        cloud.mAuth = Auth;

        cloud.preferences = activity.getApplicationContext().getSharedPreferences("cookPit", Context.MODE_PRIVATE);
        cloud.prefEditor = cloud.preferences.edit();


        return cloud;
    }

    static public Cloud getCloud() {
        if (cloud == null) {
            cloud = new Cloud();
        }
        return cloud;
    }

    public void defineCookPitUser() {

        taskStart.execute();


        return;

    }

    public boolean syncUserWithFirebase() {

        final int ADMIN_FIRST_USE = 0;
        final int ADMIN_UP_TO_DATE = 1;
        final int ADMIN_PUSH_SYNC = 2;
        final int ADMIN_PULL_SYNC = 3;
        final int USER_FIRST_USE = 10;
        final int USER_UP_TO_DATE = 11;
        final int USER_PULL_SYNC = 12;
        final int NOT_REGISTER_USER = 13;


        int SYNC_FLAG = -1;
        long seqUser = mUser.getSequence();
        long seq = preferences.getLong("sequenceNumber", Long.valueOf(0));

        if (mUser.getEmail().equals(preferences.getString("email", "cookpit@cookpit.ie")) && mUser.getAccountNumber().equals(preferences.getString("account", null))) {


            Log.v("sequence preference", String.valueOf(seq));
            Log.v("sequence firebase", String.valueOf(seqUser));


            if (mUser.isAdmin()) {
                if (seqUser == seq) SYNC_FLAG = ADMIN_UP_TO_DATE;
                if (seq > seqUser) SYNC_FLAG = ADMIN_PUSH_SYNC;
                if (seq < seqUser) SYNC_FLAG = ADMIN_PULL_SYNC;

            }
            if (mUser.isUser()) {
                if (seqUser == seq) SYNC_FLAG = USER_UP_TO_DATE;
                if (seq > seqUser) SYNC_FLAG = USER_PULL_SYNC;
                if (seq < seqUser) SYNC_FLAG = USER_PULL_SYNC;

            }

        } else {
            if (mUser.isUser()) {
                SYNC_FLAG = USER_FIRST_USE;


            }
            if (mUser.isAdmin()) {
                SYNC_FLAG = ADMIN_FIRST_USE;


            }
        }
        if (seqUser == -1) {
            SYNC_FLAG = NOT_REGISTER_USER;
        }

        switch (SYNC_FLAG) {
            case ADMIN_FIRST_USE: {
                Log.v("case", String.valueOf(ADMIN_FIRST_USE));
                progressArc.setVisibility(View.VISIBLE);
                cleanDatabase();

                ((cloudLoadCallBack) act).onDataInserted();

                DatabaseReference allData = db.getReference("Customer data").child(mUser.getAccountNumber());
                allData.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Utility.insertDataFromSequence(act.getApplicationContext(), 0, dataSnapshot);
                        progressArc.setVisibility(View.GONE);
                        ((cloudLoadCallBack) act).onDataInserted();


                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                saveUserOnSharedPreference();


                return false;

            }
            case ADMIN_UP_TO_DATE: {
                Log.v("case", String.valueOf(ADMIN_UP_TO_DATE));
                saveUserOnSharedPreference();
                progressArc.setVisibility(View.GONE);

                return true;

            }
            case ADMIN_PUSH_SYNC: {
                Log.v("case", String.valueOf(ADMIN_PUSH_SYNC));
                saveUserOnSharedPreference();
                progressArc.setVisibility(View.GONE);

                return true;

            }
            case ADMIN_PULL_SYNC: {
                Log.v("case", String.valueOf(ADMIN_PULL_SYNC));
                loadSequences(seq, seqUser);
                saveUserOnSharedPreference();

                return true;

            }
            case USER_FIRST_USE: {
                Log.v("case", String.valueOf(USER_FIRST_USE));
                cleanDatabase();
                progressArc.setVisibility(View.VISIBLE);
                ((cloudLoadCallBack) act).onDataInserted();

                DatabaseReference allData = db.getReference("Customer data").child(mUser.getAccountNumber());
                allData.addListenerForSingleValueEvent(new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        Utility.insertDataFromSequence(act.getApplicationContext(), 0, dataSnapshot);

                        ((cloudLoadCallBack) act).onDataInserted();
                        progressArc.setVisibility(View.GONE);

                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
                saveUserOnSharedPreference();


                return false;

            }
            case USER_UP_TO_DATE: {
                Log.v("case", String.valueOf(USER_UP_TO_DATE));
                saveUserOnSharedPreference();
                progressArc.setVisibility(View.GONE);

                return true;

            }
            case USER_PULL_SYNC: {
                Log.v("case", String.valueOf(USER_PULL_SYNC));
                loadSequences(seq, seqUser);
                saveUserOnSharedPreference();
                progressArc.setVisibility(View.GONE);

                return true;

            }
            case NOT_REGISTER_USER: {
                Log.v("case", String.valueOf(NOT_REGISTER_USER));
                saveUserOnSharedPreference();
                progressArc.setVisibility(View.GONE);

                return true;


            }
            case -1: {
                Log.v("case", String.valueOf(-1));
                saveUserOnSharedPreference();
                progressArc.setVisibility(View.GONE);

                return true;

            }


        }

        return true;
    }

    public void saveUserOnSharedPreference() {
        prefEditor.putLong("sequenceNumber", mUser.getSequence());
        prefEditor.putString("email", mUser.getEmail());
        prefEditor.putString("account", mUser.getAccountNumber());
        prefEditor.putString("name", mUser.getName());
        prefEditor.putBoolean("user", mUser.isUser());
        prefEditor.putBoolean("admin", mUser.isAdmin());
        prefEditor.apply();
    }

    public boolean cleanDatabase() {
        boolean isdeleted = Utility.deleteTheDatabase(act.getApplicationContext());
        Utility.createDatabase(act.getApplicationContext());
        return isdeleted;
    }

    public void updateUserMenu() {
        displayName = act.findViewById(R.id.display_name);
        userRegistration = act.findViewById(R.id.user_registration);
        String user = "Chef";
        String admin = "Chef Admin";
        if (mUser.isAdmin()) {
            userRegistration.setText(admin);
        }
        if (mUser.isUser()) {
            userRegistration.setText(user);
        }
        if (!mUser.isUser() && !mUser.isAdmin()) {
            userRegistration.setText("Unregistered");
        }
        String Name = mUser.getName();

        displayName.setText(Name);

    }

    public void loadSequences(final long prefSeq, final long firebaseSeq) {
        int numberOfSequence = (int) (firebaseSeq - prefSeq);
        sequences = new sequence[numberOfSequence];
        DatabaseReference ref = db.getReference("Customer account/Ireland/Free trial").child(mUser.accountNumber).child("sequence");

        ref.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int i = 0;
                for (DataSnapshot data : dataSnapshot.getChildren()) {

                    String idString = data.getKey();
                    long longId = 0;
                    if (idString != null) {
                        longId = Long.parseLong(idString);
                    }

                    if (longId > prefSeq && longId <= firebaseSeq) {

                        sequence msequence = data.getValue(sequence.class);


                        sequences[i] = msequence;
                        i++;
                        Log.v("number of sequences", String.valueOf(i));

                    }

                }

                ((cloudLoadCallBack) act).onSequenceLoaded(sequences);


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });


    }

    public void setSeqCountListener() {
        seq = db.getReference("Customer account/Ireland/Free trial").child(mUser.accountNumber).child("seqCount");

        seq.addValueEventListener(listener);

    }

    public void insertSequencesToSqlite() {

        for (sequence seq : sequences) {

            long level = seq.getLevel();
            String rootA = seq.getRootA();
            long rootB = seq.getRootB();
            String rootC = seq.getRootC();
            Log.v("level", String.valueOf(level));
            switch ((int) level) {
                case 0: {
                    cleanDatabase();
                    progressArc.setVisibility(View.VISIBLE);
                    ((cloudLoadCallBack) act).onDataInserted();

                    DatabaseReference allData = db.getReference("Customer data").child(mUser.getAccountNumber());
                    allData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Utility.insertDataFromSequence(act.getApplicationContext(), 0, dataSnapshot);

                            ((cloudLoadCallBack) act).onDataInserted();
                            progressArc.setVisibility(View.GONE);

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    break;
                }
                case 1: {
                    DatabaseReference allData = db.getReference("Customer data").child(mUser.getAccountNumber()).child(rootA);
                    allData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Utility.insertDataFromSequence(act.getApplicationContext(), 1, dataSnapshot);
                            ((cloudLoadCallBack) act).onDataInserted();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    break;
                }
                case 2: {
                    DatabaseReference allData = db.getReference("Customer data").child(mUser.getAccountNumber()).child(rootA).child(String.valueOf(rootB));
                    allData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Utility.insertDataFromSequence(act.getApplicationContext(), 2, dataSnapshot);
                            ((cloudLoadCallBack) act).onDataInserted();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    break;
                }
                case 3: {
                    DatabaseReference allData = db.getReference("Customer data").child(mUser.getAccountNumber()).child(rootA).child(String.valueOf(rootB)).child(rootC);
                    allData.addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            Utility.insertDataFromSequence(act.getApplicationContext(), 3, dataSnapshot);
                            ((cloudLoadCallBack) act).onDataInserted();

                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                    break;
                }
            }

        }
    }

    public void close() {
        if (seq != null) {
            seq.removeEventListener(listener);
        }
    }
}
