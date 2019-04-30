package com.example.android.cookpit.DetailActivities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.cookpit.Fragments.Dish_detail_fragment;
import com.example.android.cookpit.Fragments.EditDishFragment;
import com.example.android.cookpit.Fragments.dummy.IngredientsFragment;
import com.example.android.cookpit.R;
import com.example.android.cookpit.Fragments.TabFragments.Tab_menu;
import com.example.android.cookpit.Model.UtilityPojo;
import com.example.android.cookpit.Model.data.KitchenContract;
import com.example.android.cookpit.Model.pojoClass.Ingredient;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.util.ArrayList;

public class EditActivity extends AppCompatActivity implements Dish_detail_fragment.Callback, IngredientsFragment.OnListFragmentInteractionListener {

    public boolean mTwoPane;
    public static final String EDITDISHFRAGMENT_TAG = "EDFTAG";
    public static final String TWOPANETAG = "TWOPANE";
    public static final String EDITDISHTAG = "EDITDISH";
    public static final String INGREDIENT_SEARCH_TAG = "SCHING";
    public boolean editDish = true;
    public ArrayList<Ingredient> ingredientSearch;
    public ActionBar mActionBar;
    public String dishName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit);
        mActionBar = getSupportActionBar();
        Uri dishUri = getIntent().getData();
        dishName = getIntent().getStringExtra("dishname");
        mActionBar.setTitle("Edit " + dishName);


        if (findViewById(R.id.edit_container) != null) {


            Bundle args = new Bundle();
            Dish_detail_fragment fragment = new Dish_detail_fragment();
            EditDishFragment editFragment = new EditDishFragment();


            mTwoPane = true;
            args.putParcelable(Tab_menu.DETAIL_URI, dishUri);
            args.putBoolean(TWOPANETAG, mTwoPane);
            args.putBoolean(EDITDISHTAG, editDish);
            fragment.setArguments(args);

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()

                        .replace(R.id.edit_dish_detail_container, fragment, EDITDISHFRAGMENT_TAG)
                        .commit();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.edit_container, editFragment, EDITDISHTAG)
                        .commit();


            }

        } else {


            Bundle args = new Bundle();
            EditDishFragment fragment = new EditDishFragment();


            mTwoPane = false;
            args.putParcelable(Tab_menu.DETAIL_URI, dishUri);
            args.putBoolean(TWOPANETAG, mTwoPane);
            args.putBoolean(EDITDISHTAG, editDish);
            fragment.setArguments(args);

            if (savedInstanceState == null) {
                getSupportFragmentManager().beginTransaction()

                        .replace(R.id.edit_dish_detail_container, fragment, EDITDISHFRAGMENT_TAG)
                        .commit();

            }
        }
    }

    public void startCameraActivity(String NameFromEditText) {

        String fileName = NameFromEditText;
        Intent intent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
        File file = new File(Environment.getExternalStorageDirectory() + "/Kitchendrive/", fileName + ".jpg");

        if (file.exists()) {
            Picasso.with(this).invalidate(file);
            Boolean isImagedeleted = file.delete();


            Log.v(String.valueOf(file), String.valueOf(isImagedeleted));

        }


        Uri outputFileUri = Uri.fromFile(file);

        intent.putExtra(MediaStore.EXTRA_OUTPUT, outputFileUri);

        startActivityForResult(intent, 0);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        updatePicture();


    }

    public void updatePicture() {

        Dish_detail_fragment editFragment = (Dish_detail_fragment) getSupportFragmentManager().findFragmentByTag(EDITDISHFRAGMENT_TAG);
        editFragment.updatePicture();


    }

    @Override
    public void onItemSelected(String dishName, String editTag) {
        if (editTag == Dish_detail_fragment.IMAGE_TAG) {
            startCameraActivity(dishName);

        }


    }

    @Override
    public void onListFragmentInteraction(Object item) {

    }

    public ArrayList<Ingredient> searchData(String text) {
        String[] selectionArgs;
        String query = text;
        query = query + "*";
        selectionArgs = new String[]{query};
        Uri Fts_Ingredient_Uri = KitchenContract.FtsIngredientEntry.buildFtsIngredientUri(text);

        Cursor c = getContentResolver().query(
                Fts_Ingredient_Uri,
                null,
                null,
                selectionArgs,
                null
        );


        ingredientSearch = UtilityPojo.getIngredient(c);

        Log.v(String.valueOf(ingredientSearch.size()), "test");
        if (ingredientSearch.size() > 0) {

            for (int i = 0; i < ingredientSearch.size(); i++) {


                Log.v(ingredientSearch.get(i).getId() + "  " + ingredientSearch.get(i).getName(), "test");

            }

        }
        c.close();
        return ingredientSearch;
    }


}