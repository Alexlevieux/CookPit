package com.example.android.cookpit;

import android.database.Cursor;
import android.util.Log;

import com.example.android.cookpit.pojoClass.Dish;
import com.example.android.cookpit.pojoClass.Ingredient;
import com.example.android.cookpit.pojoClass.Menu;
import com.example.android.cookpit.pojoClass.Mep;

import java.util.ArrayList;

/**
 * Created by alexandrelevieux on 23/01/2017.
 */

public class UtilityPojo {

    public static ArrayList<Menu> getMenu(Cursor cursor) {

        ArrayList<Menu> menus;
        menus = new ArrayList<>();
        int count = cursor.getCount();
        if (cursor != null && count > 0) {
            cursor.moveToFirst();
            for (int i = 1; i <= count; i++) {

                Menu menu;

                menu = new Menu(cursor.getInt(0), cursor.getString(1), cursor.getString(2));
                menus.add(menu);
                cursor.moveToNext();


            }
        }
        return menus;
    }

    public static ArrayList<Dish> getDish(Cursor cursor) {

        ArrayList<Dish> Dishs;
        Dishs = new ArrayList<>();
        int count = cursor.getCount();
        if (cursor != null && count > 0) {
            cursor.moveToFirst();
            for (int i = 1; i <= count; i++) {

                Dish dish;
                dish = new Dish(cursor.getInt(0), cursor.getString(1), null, cursor.getString(2));
                Dishs.add(dish);
                cursor.moveToNext();


            }
        }
        return Dishs;
    }

    public static ArrayList<Mep> getMep(Cursor cursor) {

        ArrayList<Mep> Meps;
        Meps = new ArrayList<>();
        int count = cursor.getCount();
        if (cursor != null && count > 0) {
            cursor.moveToFirst();
            for (int i = 1; i <= count; i++) {

                Mep mep;
                mep = new Mep(cursor.getInt(0), cursor.getString(1), null, cursor.getString(2));
                Meps.add(mep);

                Log.v("Ingredient", mep.getName());
                cursor.moveToNext();


            }
        }
        return Meps;
    }

    public static ArrayList<Ingredient> getIngredient(Cursor cursor) {

        ArrayList<Ingredient> Ingredients;
        Ingredients = new ArrayList<>();
        int count = cursor.getCount();
        if (cursor != null && count > 0) {
            cursor.moveToFirst();
            for (int i = 1; i <= count; i++) {

                Ingredient ingredient;
                ingredient = new Ingredient(cursor.getInt(0), cursor.getString(1), null, null, null);
                Ingredients.add(ingredient);


                cursor.moveToNext();


            }
        }
        return Ingredients;
    }


}
