package com.example.android.cookpit.TabFragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.cookpit.Adapter.dishAdapter;
import com.example.android.cookpit.R;
import com.example.android.cookpit.data.KitchenContract;


public class Tab_dish extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String SELECTED_KEY = "selected_position";

    private String mParam1;
    private String mParam2;

    ListView listview;
    private int mPosition = ListView.INVALID_POSITION;
    public static final int DISH_LOADER = 0;

    private dishAdapter mdishAdapter;

    String[] DishColumn = {
            KitchenContract.DishEntry.TABLE_NAME + "." + KitchenContract.DishEntry._ID,
            KitchenContract.DishEntry.COLUMN_DISH_NAME,
            KitchenContract.DishEntry.COLUMN_DESCRIPTION,
            KitchenContract.DishEntry.COLUMN_DRAWABLE_ID
    };


    public Tab_dish() {
        // Required empty public constructor
    }


    public static Tab_dish newInstance(String param1, String param2) {
        Tab_dish fragment = new Tab_dish();
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
        View rootview = inflater.inflate(R.layout.tab_dish, container, false);

        listview = (ListView) rootview.findViewById(R.id.dishlistview);
        mdishAdapter = new dishAdapter(getActivity(), null, 0);
        listview.setAdapter(mdishAdapter);
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

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        getLoaderManager().initLoader(DISH_LOADER, null, this);
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

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        Uri dishUri = KitchenContract.DishEntry.CONTENT_URI;
        String sortOrder = KitchenContract.DishEntry.COLUMN_DISH_NAME + " ASC";

        return new CursorLoader(getActivity(),
                dishUri,
                DishColumn,
                null,
                null,
                sortOrder
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {

        mdishAdapter.swapCursor(data);

        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            listview.smoothScrollToPosition(mPosition);
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mdishAdapter.swapCursor(null);


    }
}

