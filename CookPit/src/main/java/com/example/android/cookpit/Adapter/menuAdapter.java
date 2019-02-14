package com.example.android.cookpit.Adapter;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.cookpit.R;
import com.example.android.cookpit.data.KitchenContract;
import com.squareup.picasso.Picasso;

import java.io.File;

import static android.view.View.GONE;

/**
 * Created by alexandrelevieux on 31/10/2016.
 */

public class menuAdapter extends CursorAdapter {


    public menuAdapter(Context context, Cursor c, int flags) {
        super(context, c, flags);

    }

    public static class ViewHolder {

        public final ImageView imageView;
        public final TextView Nameview;


        public ViewHolder(View view) {

            imageView = (ImageView) view.findViewById(R.id.dish_icon_imageview);
            Nameview = (TextView) view.findViewById(R.id.dish_name_textview);
        }
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
    public View getView(int position, View convertView, ViewGroup parent) {

        return super.getView(position, convertView, parent);
    }

    @Override
    public void bindView(View view, Context context, Cursor cursor) {

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        if (viewHolder.imageView.getDrawable() != null) {
            viewHolder.imageView.setImageDrawable(null);

        }


        if (cursor.getColumnName(1).equals(KitchenContract.MenuEntry.COLUMN_MENU_NAME)) {

            viewHolder.Nameview.setText(cursor.getString(1));
            viewHolder.imageView.setVisibility(GONE);

        }
        if (cursor.getColumnName(1).equals(KitchenContract.DishEntry.COLUMN_DISH_NAME)) {

            String fileName = cursor.getString(1);
            File file = new File(Environment.getExternalStorageDirectory() + "/Kitchendrive/", fileName + ".jpg");
            viewHolder.Nameview.setText(cursor.getString(1));
            viewHolder.imageView.setVisibility(View.VISIBLE);
            Picasso.with(context).cancelRequest(viewHolder.imageView);

            if (file.exists()) {

                Uri dishImageUri = Uri.fromFile(file);
                Picasso.with(context).load(dishImageUri).resize(60, 50).centerCrop().onlyScaleDown().into(viewHolder.imageView);


            }

        }
        if (cursor.getColumnName(1).equals(KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME)) {
            {

                viewHolder.Nameview.setText(cursor.getString(1));
                viewHolder.imageView.setVisibility(GONE);

            }
        }
    }
}
