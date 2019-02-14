package com.example.android.cookpit.TabFragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cookpit.R;

/**
 * Created by alexandrelevieux on 09/01/2017.
 */

public class Tab_search extends Fragment {

    public Tab_search() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_ingredient, container, false);
    }

}

