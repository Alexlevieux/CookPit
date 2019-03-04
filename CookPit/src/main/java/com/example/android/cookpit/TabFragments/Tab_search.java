package com.example.android.cookpit.TabFragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.cookpit.Adapter.dishAdapter;
import com.example.android.cookpit.R;
import com.example.android.cookpit.data.KitchenContract;
import com.example.android.cookpit.Adapter.searchAdapter;

import java.util.ArrayList;


/**
 * Created by alexandrelevieux on 09/01/2017.
 */

public class Tab_search extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String SELECTED_KEY = "selected_position";

    private String mParam1;
    private String mParam2;

    ListView listview;
    private int mPosition = ListView.INVALID_POSITION;
    public static final int DISH_LOADER = 0;
    public static ArrayList searchArray;
    private searchAdapter mSearchAdapter;

    String[] DishColumn = {
            KitchenContract.DishEntry.TABLE_NAME + "." + KitchenContract.DishEntry._ID,
            KitchenContract.DishEntry.COLUMN_DISH_NAME,
            KitchenContract.DishEntry.COLUMN_DESCRIPTION,
            KitchenContract.DishEntry.COLUMN_DRAWABLE_ID
    };

    public Tab_search() {
        // Required empty public constructor
    }


    public static Tab_search newInstance(String param1, String param2) {
        Tab_search fragment = new Tab_search();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootview = inflater.inflate(R.layout.tab_search, container, false);

        listview = rootview.findViewById(R.id.search_listview);

        mSearchAdapter = new searchAdapter(getContext(), R.layout.list_item, searchArray, 0);
        listview.setAdapter(mSearchAdapter);


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });

        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {

            mPosition = savedInstanceState.getInt(SELECTED_KEY);
        }
        return rootview;


    }

    public void onSearchUpdate() {

        getFragmentManager().beginTransaction().detach(this).attach(this).commit();


    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {

        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        super.onResume();

    }


}




