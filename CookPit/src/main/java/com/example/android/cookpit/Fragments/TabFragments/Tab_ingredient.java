package com.example.android.cookpit.Fragments.TabFragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.cookpit.R;


/**
 * A simple {@link Fragment} subclass.
 */
public class Tab_ingredient extends Fragment {


    public Tab_ingredient() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.tab_ingredient, container, false);
    }

}
