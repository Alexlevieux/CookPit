package com.example.android.cookpit.Model;

import android.app.Application;
import android.content.Context;
import android.support.multidex.MultiDex;

/**
 * Created by alexandrelevieux on 18/11/2017.
 */
public class cookpit extends Application {

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

}