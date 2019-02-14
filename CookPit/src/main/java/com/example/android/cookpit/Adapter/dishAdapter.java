package com.example.android.cookpit.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cookpit.R;
import com.example.android.cookpit.data.KitchenContract;

/**
 * Created by alexandrelevieux on 31/10/2016.
 */

public class dishAdapter extends CursorAdapter {


    public dishAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup parent) {
        int LayoutId = R.layout.list_item;
        View view = LayoutInflater.from(context).inflate(LayoutId, parent, false);

        ViewHolder viewHolder = new ViewHolder(view);

        view.setTag(viewHolder);

        return view;
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        viewHolder.dishNameview.setText(cursor.getString(cursor.getColumnIndex(KitchenContract.DishEntry.COLUMN_DISH_NAME)));


    }


    public static class ViewHolder {

        public final ImageView dishimageview;
        public final TextView dishNameview;


        public ViewHolder(View view) {
            dishimageview = (ImageView) view.findViewById(R.id.dish_imageview);
            dishNameview = (TextView) view.findViewById(R.id.dish_name_textview);
        }
    }


}

