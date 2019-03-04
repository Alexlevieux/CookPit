package com.example.android.cookpit.TabFragments;

import android.content.ContentUris;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
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
import android.widget.FrameLayout;
import android.widget.ListView;

import com.example.android.cookpit.Adapter.menuAdapter;
import com.example.android.cookpit.R;
import com.example.android.cookpit.data.KitchenContract;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;


public class Tab_menu extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    private Uri backUri;
    private Uri mUri;

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private static final String SELECTED_KEY = "selected_position";
    public static final String CONTENT_DISHFRAGMENT_TAG = "CDFTAG";
    public static final String LOADER = "LOADER";
    public static final String CATEGORY = "CATEGORY";
    public static final String MENU = "MENU";

    public static final String DETAIL_URI = "URI";
    public static final String POSITION = "position";
    public String MenuName;
    public String CourseName;
    public String dishName;

    String[] MenuColumn = {
            KitchenContract.MenuEntry.TABLE_NAME + "." + KitchenContract.MenuEntry._ID,
            KitchenContract.MenuEntry.COLUMN_MENU_NAME,
            KitchenContract.MenuEntry.COLUMN_USER_NAME

    };
    String[] CourseCategoryColumn = {
            KitchenContract.CourseCategoryEntry.TABLE_NAME + "." + KitchenContract.CourseCategoryEntry._ID,
            KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME,
            KitchenContract.MenuDishEntry.COLUMN_COURSE_POSITION

    };
    String[] DishColumn = {
            KitchenContract.DishEntry.TABLE_NAME + "." + KitchenContract.DishEntry._ID,
            KitchenContract.DishEntry.COLUMN_DISH_NAME,
            KitchenContract.DishEntry.COLUMN_DESCRIPTION,
            KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID,
            KitchenContract.MenuDishEntry.COLUMN_DISH_POSITION

    };

    ListView listview;

    public Long MenuId;
    public Long CategoryId;

    private int mPosition = ListView.INVALID_POSITION;

    public static final int MENU_LOADER = 1;
    public static final int DISH_LOADER = 3;
    public static final int CATEGORY_LOADER = 2;
    private static int CURRENT_LOADER = 0;

    private static long CURRENT_MENU = 0L;


    private menuAdapter mmenuAdapter;


    public Tab_menu() {
        // Required empty public constructor
    }


    public static Tab_menu newInstance(String param1, String param2) {
        Tab_menu fragment = new Tab_menu();
        Bundle args = new Bundle();

        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    public interface Callback {
        /**
         * DetailFragmentCallback for when an item has been selected.
         */

        void onItemSelected(ArrayList<Uri> Uris, String dishName, int position, String category);
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        if (savedInstanceState == null) {
            getLoaderManager().destroyLoader(DISH_LOADER);
            getLoaderManager().destroyLoader(MENU_LOADER);
            getLoaderManager().destroyLoader(CATEGORY_LOADER);
            getLoaderManager().initLoader(MENU_LOADER, getArguments(), this);

        }

        super.onActivityCreated(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        final View rootview = inflater.inflate(R.layout.tab_menu, container, false);

        listview = (ListView) rootview.findViewById(R.id.menulistview);
        mmenuAdapter = new menuAdapter(getActivity(), null, 0);
        listview.setAdapter(mmenuAdapter);


        if (savedInstanceState != null && savedInstanceState.containsKey(SELECTED_KEY)) {
            // The listview probably hasn't even been populated yet.  Actually perform the
            // swapout in onLoadFinished.
            CURRENT_LOADER = savedInstanceState.getInt(LOADER);


            mUri = savedInstanceState.getParcelable(Tab_menu.DETAIL_URI);
            Log.v(String.valueOf(mUri), String.valueOf(CURRENT_LOADER));
            Bundle args = new Bundle();
            args.putParcelable(Tab_menu.DETAIL_URI, mUri);

            switch (CURRENT_LOADER) {

                case CATEGORY_LOADER: {
                    getLoaderManager().destroyLoader(CATEGORY_LOADER);
                    getLoaderManager().initLoader(CATEGORY_LOADER, args, this);


                    break;
                }
                case MENU_LOADER: {

                    getLoaderManager().destroyLoader(MENU_LOADER);
                    getLoaderManager().initLoader(MENU_LOADER, args, this);

                    break;
                }
            }
            MenuId = savedInstanceState.getLong(MENU);

            mPosition = savedInstanceState.getInt(SELECTED_KEY);

        }


        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {


                Cursor cursor = (Cursor) adapterView.getItemAtPosition(position);


                if (CURRENT_LOADER == CATEGORY_LOADER) {

                    if (cursor != null) {

                        Bundle args = new Bundle();
                        FrameLayout frame = (FrameLayout) rootview.findViewById(R.id.Tab_menu_content);
                        Tab_menu_content fragment = new Tab_menu_content();

                        mUri = KitchenContract.DishEntry.buildDishUriWithCourseCategoryIdandMenuId(CURRENT_MENU, cursor.getLong(cursor.getColumnIndex(KitchenContract.CourseCategoryEntry._ID)));
                        CourseName = cursor.getString(1);
                        mPosition = cursor.getPosition();
                        frame.setVisibility(View.VISIBLE);

                        args.putParcelable(Tab_menu.DETAIL_URI, mUri);
                        String category = "";
                        if (cursor != null) {
                            category = cursor.getString(cursor.getColumnIndex(KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME));
                        } else {
                            category = "Category";
                        }
                        args.putString("category", category);
                        fragment.setArguments(args);

                        getChildFragmentManager().beginTransaction()
                                .replace(R.id.Tab_menu_content, fragment, CONTENT_DISHFRAGMENT_TAG)
                                .commit();


                    }
                }


                if (CURRENT_LOADER == MENU_LOADER) {

                    if (cursor != null) {

                        Bundle args = new Bundle();

                        MenuName = cursor.getString(1);
                        mUri = KitchenContract.MenuDishEntry.buildMenuDishUri(cursor.getLong(cursor.getColumnIndex(KitchenContract.MenuEntry._ID)));
                        CURRENT_MENU = cursor.getLong(cursor.getColumnIndex(KitchenContract.MenuEntry._ID));

                        args.putParcelable(Tab_menu.DETAIL_URI, mUri);
                        onItemClicked(args);


                    }
                }
            }
        });


        return rootview;
    }

    public void updatePicture() {

        String fileName = dishName;
        File file = new File(Environment.getExternalStorageDirectory() + "/Kitchendrive/", fileName + ".jpg");

        Picasso.with(getContext()).invalidate(file);
    }

    public void onBackClicked(Bundle args) {
        switch (CURRENT_LOADER) {
            case DISH_LOADER: {
                getLoaderManager().destroyLoader(CATEGORY_LOADER);
                getLoaderManager().initLoader(CATEGORY_LOADER, args, this);


                break;
            }
            case CATEGORY_LOADER: {
                getLoaderManager().destroyLoader(MENU_LOADER);
                getLoaderManager().initLoader(MENU_LOADER, args, this);

                break;
            }
            case MENU_LOADER: {

                getLoaderManager().destroyLoader(MENU_LOADER);
                break;
            }
        }
    }

    public void onItemClicked(Bundle args) {

        if (CURRENT_LOADER == CATEGORY_LOADER) {

            getLoaderManager().destroyLoader(DISH_LOADER);
            getLoaderManager().initLoader(DISH_LOADER, args, this);

        }


        if (CURRENT_LOADER == MENU_LOADER) {

            getLoaderManager().destroyLoader(CATEGORY_LOADER);
            getLoaderManager().initLoader(CATEGORY_LOADER, args, this);

        }

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

        if (id == DISH_LOADER) {

            backUri = mUri;
            mUri = args.getParcelable(Tab_menu.DETAIL_URI);
            MenuId = Long.parseLong(mUri.getLastPathSegment());
            CategoryId = Long.parseLong(mUri.getQueryParameter(KitchenContract.DishEntry.TABLE_NAME + "." + KitchenContract.DishEntry._ID));

            String selection = KitchenContract.MenuDishEntry.TABLE_NAME + "." + KitchenContract.MenuDishEntry.COLUMN_MENU_ID + " = ? AND " + KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID + " = ? ";
            String[] selectionArgs = new String[]{Long.toString(MenuId), Long.toString(CategoryId)};
            String sortOrder = KitchenContract.MenuDishEntry.COLUMN_DISH_POSITION + " ASC";

            CURRENT_LOADER = DISH_LOADER;

            return new CursorLoader(getActivity(),
                    mUri,
                    DishColumn,
                    selection,
                    selectionArgs,
                    sortOrder
            );


        }


        if (id == CATEGORY_LOADER) {

            backUri = mUri;
            mUri = args.getParcelable(Tab_menu.DETAIL_URI);
            MenuId = ContentUris.parseId(mUri);

            Uri CourseCategoryUri = ContentUris.withAppendedId(KitchenContract.CourseCategoryEntry.CONTENT_URI, MenuId);
            String selection = KitchenContract.MenuDishEntry.TABLE_NAME + "." + KitchenContract.MenuDishEntry.COLUMN_MENU_ID + " = ? ";
            String[] selectionArgs = new String[]{Long.toString(MenuId)};
            String sortOrder = KitchenContract.MenuDishEntry.COLUMN_COURSE_POSITION + " ASC";

            CURRENT_LOADER = CATEGORY_LOADER;
            return new CursorLoader(getActivity(),
                    CourseCategoryUri,
                    CourseCategoryColumn,
                    selection,
                    selectionArgs,
                    sortOrder
            );


        }

        if (id == MENU_LOADER) {
            Uri menuUri = KitchenContract.MenuEntry.CONTENT_URI;
            String sortOrder = KitchenContract.MenuEntry.COLUMN_MENU_NAME + " ASC";

            CURRENT_LOADER = MENU_LOADER;

            return new CursorLoader(getActivity(),
                    menuUri,
                    MenuColumn,
                    null,
                    null,
                    sortOrder
            );
        }
        return null;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.


        }
        listview.clearChoices();
        mmenuAdapter.swapCursor(data);


    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mmenuAdapter.swapCursor(null);

    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        if (CURRENT_LOADER != 0 && mUri != null && MenuId != null) {

            outState.putInt(LOADER, CURRENT_LOADER);
            outState.putParcelable(Tab_menu.DETAIL_URI, mUri);
            outState.putLong(MENU, MenuId);
        }
        if (mPosition != ListView.INVALID_POSITION) {
            // If we don't need to restart the loader, and there's a desired position to restore
            // to, do so now.
            outState.putInt(SELECTED_KEY, mPosition);

        }

        super.onSaveInstanceState(outState);
    }

}

