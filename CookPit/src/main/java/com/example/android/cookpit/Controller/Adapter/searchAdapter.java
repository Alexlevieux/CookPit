package com.example.android.cookpit.Controller.Adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.cookpit.R;
import com.example.android.cookpit.Model.pojoClass.Dish;
import com.example.android.cookpit.Model.pojoClass.Ingredient;
import com.example.android.cookpit.Model.pojoClass.Menu;
import com.example.android.cookpit.Model.pojoClass.Mep;

import java.util.List;

/**
 * Created by alexandhelene on 17/02/2019.
 */

public class searchAdapter extends ArrayAdapter {

    public static final int MENU = 1;
    public static final int DISH = 2;
    public static final int MEP = 3;
    public static final int INGREDIENT = 4;
    public int searchType = 0;


    LayoutInflater mInflater;
    int listItemResource;
    Context mContext;


    public searchAdapter(@NonNull Context context, int resource, @NonNull List objects, int objectType) {
        super(context, resource, objects);
        mContext = context;


    }

    public searchAdapter(@NonNull Context context, int resource, @NonNull Object[] objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(R.layout.list_item, parent, false);

        }
        Object item = getItem(position);
        Class className = item.getClass();
        TextView textView = convertView.findViewById(R.id.dish_name_textview);


        switch (className.getName()) {

            case "com.example.android.cookpit.Model.pojoClass.Menu":
                Log.v("menu", String.valueOf(position));
                Menu menu = (Menu) item;
                textView.setText(menu.getName());


                break;

            case "com.example.android.cookpit.Model.pojoClass.Dish":

                Dish dish = (Dish) item;
                textView.setText(dish.getName());

                break;


            case "com.example.android.cookpit.Model.pojoClass.Mep":

                Mep mep = (Mep) item;
                textView.setText(mep.getName());

                break;

            case "com.example.android.cookpit.Model.pojoClass.Ingredient":
                Ingredient ingredient = (Ingredient) item;
                textView.setText(ingredient.getName());

                break;
        }


        return convertView;
    }
}