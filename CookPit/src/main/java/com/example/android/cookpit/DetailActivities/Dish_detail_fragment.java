package com.example.android.cookpit.DetailActivities;


import android.content.ContentUris;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cookpit.Adapter.DishDetailAdapter;
import com.example.android.cookpit.R;
import com.example.android.cookpit.TabFragments.Tab_menu;
import com.example.android.cookpit.data.KitchenContract;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class Dish_detail_fragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {


    public Dish_detail_fragment() {
        // Required empty public constructor
    }

    String[] DMIprojection = new String[]{
            KitchenContract.DishMepIngredientEntry.TABLE_NAME + "." + KitchenContract.DishMepIngredientEntry.COLUMN_DISH_ID,
            KitchenContract.MepEntry.TABLE_NAME + "." + KitchenContract.MepEntry._ID,
            KitchenContract.MepEntry.COLUMN_MEP_NAME,
            KitchenContract.IngredientEntry.TABLE_NAME + "." + KitchenContract.IngredientEntry._ID,
            KitchenContract.IngredientEntry.COLUMN_INGREDIENT_NAME,
            KitchenContract.DishMepIngredientEntry.COLUMN_QUANTITY,
    };

    private static final int COLUMN_DISH_ID = 0;
    private static final int COLUMN_DISH_NAME = 1;
    private static final int COLUMN_DISH_DESCRIPTION = 2;

    String[] dishInfoprojection = new String[]{
            KitchenContract.DishEntry.TABLE_NAME + "." + KitchenContract.DishEntry._ID,
            KitchenContract.DishEntry.COLUMN_DISH_NAME,
            KitchenContract.DishEntry.COLUMN_DESCRIPTION,
            KitchenContract.DishEntry.COLUMN_DRAWABLE_ID,
            KitchenContract.DishEntry.COLUMN_USER_NAME,

    };


    private static final int DISH_INFO_LOADER = 1;
    private static final int INGREDIENT_MEP_LOADER = 2;
    public int ACTUAL_LOADER = 0;


    public Uri DishUri;
    public Uri DMIUri;
    public ArrayList<Uri> dishListUri;
    public Uri dishItemUri;
    private static int position = 0;
    public int nbrOfPages;

    public Long DishId;
    private DishDetailAdapter mDishDetailadapter;

    TextView DishName;
    String DishNameString;
    TextView Description;
    CoordinatorLayout frame;
    FloatingActionButton fab;
    ImageView dishImage;
    ImageView chevronLeft;
    ImageView chevronRight;
    View editContainer;


    private boolean editDish;

    public static final String DESCRIPTION_TAG = "DESCRIPTION";
    public static final String IMAGE_TAG = "DESCRIPTION";


    public static Dish_detail_fragment newInstance(Bundle args, int index) {
        // TODO Auto-generated method stub

        Dish_detail_fragment fragment = new Dish_detail_fragment();
        fragment.position = index;
        fragment.setArguments(args);

        return fragment;
    }

    public interface Callback {

        public void onItemSelected(String dishName, String EditTag);
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.fragment_detail_dish, container, false);

        if (getArguments() != null) {

            Bundle args = getArguments();
            ExpandableListView listView = (ExpandableListView) view.findViewById(R.id.Dish_detail_expandable_listview);


            editDish = args.getBoolean(EditActivity.EDITDISHTAG);
            mDishDetailadapter = new DishDetailAdapter(null, getContext());
            listView.setFocusable(false);
            listView.setAdapter(mDishDetailadapter);
            frame = (CoordinatorLayout) view.findViewById(R.id.detail_dish_fragment);
            DishName = (TextView) view.findViewById(R.id.dish_detail_dish_name_textview);

            Description = (TextView) view.findViewById(R.id.dish_detail_description_textview);
            dishImage = (ImageView) view.findViewById(R.id.dish_imageview);
            chevronLeft = (ImageView) view.findViewById(R.id.dish_chevron_left);
            chevronRight = (ImageView) view.findViewById(R.id.dish_chevron_right);
            editContainer = (View) view.findViewById(R.id.edit_container);

            if (editDish) {
                DishUri = args.getParcelable(Tab_menu.DETAIL_URI);
                DishId = ContentUris.parseId(DishUri);

                dishImage.setVisibility(View.VISIBLE);
                chevronLeft.setVisibility(View.GONE);
                chevronRight.setVisibility(View.GONE);


            }

            if (!editDish) {

                dishListUri = args.getParcelableArrayList(Tab_menu.DETAIL_URI);
                nbrOfPages = dishListUri.size();
                position = args.getInt(Tab_menu.POSITION);

                if (position == 0) {
                    chevronLeft.setVisibility(View.GONE);
                }

                if (position + 1 == nbrOfPages) {
                    chevronRight.setVisibility(View.GONE);
                }


                DishUri = dishListUri.get(position);
                DishId = ContentUris.parseId(DishUri);
                fab = (FloatingActionButton) view.findViewById(R.id.fab);
                fab.setVisibility(View.VISIBLE);

                fab.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(getActivity(), EditActivity.class);

                        intent.setData(DishUri);
                        intent.putExtra("dishname", DishNameString);
                        startActivity(intent);
                    }
                });
            }

            ACTUAL_LOADER = DISH_INFO_LOADER;
            getLoaderManager().destroyLoader(DISH_INFO_LOADER);
            getLoaderManager().destroyLoader(INGREDIENT_MEP_LOADER);
            getLoaderManager().initLoader(DISH_INFO_LOADER, getArguments(), this);


        }
        return view;
    }

    public void updatePicture() {

        String fileName = DishName.getText().toString();
        File file = new File(Environment.getExternalStorageDirectory() + "/Kitchendrive/", fileName + ".jpg");

        if (file.exists()) {

            Uri dishImageUri = Uri.fromFile(file);
            dishImage.setPadding(0, 0, 0, 0);
            Picasso.with(getActivity()).invalidate(file);
            Picasso.with(getActivity()).load(dishImageUri).fit().into(dishImage);

        }

    }

    public void populateDishInfo(Cursor cursor) {
        if (cursor.getCount() > 0) {
            cursor.moveToFirst();
            DishName.setText(cursor.getString(cursor.getColumnIndex(KitchenContract.DishEntry.COLUMN_DISH_NAME)));
            DishNameString = DishName.getText().toString();
            if (editDish) {
                getActivity().setTitle("Edit " + DishName.getText());
            } else {

            }
            DishName.setVisibility(View.VISIBLE);
            Description.setText(cursor.getString(cursor.getColumnIndex(KitchenContract.DishEntry.COLUMN_DESCRIPTION)));

            ACTUAL_LOADER = INGREDIENT_MEP_LOADER;

            getLoaderManager().destroyLoader(INGREDIENT_MEP_LOADER);
            getLoaderManager().destroyLoader(DISH_INFO_LOADER);
            cursor.close();
            getLoaderManager().initLoader(INGREDIENT_MEP_LOADER, getArguments(), this);
            frame.setVisibility(View.VISIBLE);

            String fileName = DishName.getText().toString();
            File file = new File(Environment.getExternalStorageDirectory() + "/Kitchendrive/", fileName + ".jpg");

            if (file.exists()) {
                Uri dishImageUri = Uri.fromFile(file);
                dishImage.setPadding(0, 0, 0, 0);
                Picasso.with(getActivity()).load(dishImageUri).resize(600, 500).centerCrop().into(dishImage);
            }


        }

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {


        super.onActivityCreated(savedInstanceState);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);
    }


    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

        switch (id) {

            case DISH_INFO_LOADER: {


                String selection = KitchenContract.DishEntry.TABLE_NAME + "." + KitchenContract.DishEntry._ID + " = ? ";
                String[] selectionargs = new String[]{String.valueOf(DishId)};

                if (DishId != null) {
                    dishItemUri = KitchenContract.DishEntry.CONTENT_URI.buildUpon().appendPath("item").build();
                    dishItemUri = ContentUris.withAppendedId(dishItemUri, DishId);

                }


                ACTUAL_LOADER = DISH_INFO_LOADER;
                return new CursorLoader(getActivity(),
                        dishItemUri,
                        dishInfoprojection,
                        selection,
                        selectionargs,
                        null
                );

            }


            case INGREDIENT_MEP_LOADER: {

                if (DishId != null) {
                    DMIUri = ContentUris.withAppendedId(KitchenContract.DishMepIngredientEntry.CONTENT_URI, DishId);
                }

                String selection = KitchenContract.DishMepIngredientEntry.TABLE_NAME + "." + KitchenContract.DishMepIngredientEntry.COLUMN_DISH_ID + " = ? ";
                String[] selectionargs = new String[]{String.valueOf(DishId)};
                String sortOrder = KitchenContract.MepEntry.TABLE_NAME + "." + KitchenContract.MepEntry._ID + " DESC";


                ACTUAL_LOADER = INGREDIENT_MEP_LOADER;
                return new CursorLoader(getActivity(),
                        DMIUri,
                        DMIprojection,
                        selection,
                        selectionargs,
                        sortOrder
                );

            }

        }
        return null;
    }


    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor data) {


        switch (ACTUAL_LOADER) {

            case INGREDIENT_MEP_LOADER: {

                mDishDetailadapter.setGroupCursor(data);

                break;
            }

            case DISH_INFO_LOADER: {

                populateDishInfo(data);
                break;
            }

        }

    }


    @Override
    public void onLoaderReset(Loader<Cursor> loader) {

        mDishDetailadapter.setGroupCursor(null);

    }
}




