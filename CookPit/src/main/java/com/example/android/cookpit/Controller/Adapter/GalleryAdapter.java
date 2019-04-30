package com.example.android.cookpit.Controller.Adapter;

import android.content.Context;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;

import com.bumptech.glide.Glide;
import com.example.android.cookpit.Model.GlideApp;
import com.example.android.cookpit.R;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;

public class GalleryAdapter extends BaseAdapter implements ListAdapter {

    private Context ctx;
    private int pos;
    private LayoutInflater inflater;
    String[] filePathColumn = {MediaStore.Images.Media.DATA};


    private ArrayList<StorageReference> mArrayUri;

    public GalleryAdapter() {
        super();
    }

    public GalleryAdapter(Context ctx, ArrayList<StorageReference> mArrayUri) {
        super();


        Log.v("TEst", "new adapter");
        this.ctx = ctx;
        this.mArrayUri = mArrayUri;


    }

    @Override
    public int getCount() {

        return mArrayUri.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        pos = position;
        View view = convertView;
        if (view == null) {

            view = inflater.from(ctx).inflate(R.layout.gv_item, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            view.setTag(viewHolder);


        }

        ViewHolder viewHolder = (ViewHolder) view.getTag();

        GlideApp.with(ctx).load(mArrayUri.get(position)).centerCrop().into(viewHolder.imageView);


        return view;
    }

    public static class ViewHolder {

        public final ImageView imageView;


        public ViewHolder(View view) {

            imageView = view.findViewById(R.id.ivGallery);
        }
    }


}
