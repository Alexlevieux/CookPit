package com.example.android.cookpit.TabFragments;

import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.cookpit.Adapter.menuAdapter;
import com.example.android.cookpit.data.KitchenContract;
import com.example.android.cookpit.R;

import java.util.ArrayList;


public class Tab_menu_content extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {

    String[] DishColumn = {
            KitchenContract.DishEntry.TABLE_NAME + "." + KitchenContract.DishEntry._ID,
            KitchenContract.DishEntry.COLUMN_DISH_NAME,
            KitchenContract.DishEntry.COLUMN_DESCRIPTION,
            KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID

    };
    private static final int DISH_LOADER = 3;
    private com.example.android.cookpit.Adapter.menuAdapter menuAdapter;
    public ListView listview;

    public ArrayList<Uri> dishListUri;


    public Tab_menu_content() {
        // Required empty public constructor
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {


        Uri mUri = args.getParcelable(Tab_menu.DETAIL_URI);
        Long MenuId = Long.parseLong(mUri.getLastPathSegment());
        Long CategoryId = Long.parseLong(mUri.getQueryParameter(KitchenContract.DishEntry.TABLE_NAME + "." + KitchenContract.DishEntry._ID));
        String selection = KitchenContract.MenuDishEntry.TABLE_NAME + "." + KitchenContract.MenuDishEntry.COLUMN_MENU_ID + " = ? AND " + KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID + " = ? ";
        String[] selectionArgs = new String[]{Long.toString(MenuId), Long.toString(CategoryId)};

        return new CursorLoader(getActivity(),
                mUri,
                DishColumn,
                selection,
                selectionArgs,
                null
        );

    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        data.moveToFirst();
        dishListUri = new ArrayList<Uri>();


        for (int i = 0; i < data.getCount(); i++) {


            data.moveToPosition(i);
            Long dishId = data.getLong(0);
            Uri dishUri = KitchenContract.DishEntry.buildDishUri(dishId);
            dishListUri.add(dishUri);


        }

        menuAdapter.swapCursor(data);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        menuAdapter.swapCursor(null);

    }

    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */
        public void onItemSelected(Uri MenuDishUri, String dishName);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View rootview = inflater.inflate(R.layout.tab_menu_content, container, false);


        listview = (ListView) rootview.findViewById(R.id.tab_menu_content_listview);
        menuAdapter = new menuAdapter(getActivity(), null, 0);
        Bundle args = getArguments();
        final String category = args.getString("category");

        getLoaderManager().initLoader(DISH_LOADER, args, this);
        listview.setAdapter(menuAdapter);
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Cursor cursor = (Cursor) parent.getItemAtPosition(position);

                if (cursor != null) {

                    String dishName = (cursor.getString(cursor.getColumnIndex(KitchenContract.DishEntry.COLUMN_DISH_NAME)));
                    ((Tab_menu.Callback) getActivity())
                            .onItemSelected(dishListUri, dishName, position, category);

                }

            }
        });
        // Inflate the layout for this fragment
        return rootview;
    }

    public void onPageScroll(int position) {

        listview.setItemChecked(position, true);
        listview.smoothScrollToPosition(position);

    }


}
