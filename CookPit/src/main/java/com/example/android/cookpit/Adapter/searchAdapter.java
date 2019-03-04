package com.example.android.cookpit.Adapter;

import android.content.Context;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Switch;
import android.widget.TextView;

import com.example.android.cookpit.R;
import com.example.android.cookpit.pojoClass.Dish;
import com.example.android.cookpit.pojoClass.Ingredient;
import com.example.android.cookpit.pojoClass.Menu;
import com.example.android.cookpit.pojoClass.Mep;

import java.util.ArrayList;
import java.util.List;
import java.util.zip.Inflater;

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
        Class className = getItem(position).getClass();

        switch (className.getName()) {

            case "com.example.android.cookpit.pojoClass.Menu":
                Log.v("menu", String.valueOf(position));
                Menu menu = (Menu) getItem(position);
                ((TextView) convertView.findViewById(R.id.dish_name_textview))
                        .setText(menu.getName());


                break;

            case "com.example.android.cookpit.pojoClass.Dish":

                Dish dish = (Dish) getItem(position);
                ((TextView) convertView.findViewById(R.id.dish_name_textview))
                        .setText(dish.getName());

                break;


            case "com.example.android.cookpit.pojoClass.Mep":

                Mep mep = (Mep) getItem(position);
                ((TextView) convertView.findViewById(R.id.dish_name_textview))
                        .setText(mep.getName());

                break;

            case "com.example.android.cookpit.pojoClass.Ingredient":
                Ingredient ingredient = (Ingredient) getItem(position);
                ((TextView) convertView.findViewById(R.id.dish_name_textview))
                        .setText(ingredient.getName());

                break;
        }


        return convertView;
    }
}