package com.example.android.cookpit;

import android.app.Fragment;
import android.content.ClipData;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.example.android.cookpit.Controller.Adapter.GalleryAdapter;
import com.example.android.cookpit.Model.Utility;
import com.google.firebase.storage.StorageReference;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import static android.app.Activity.RESULT_OK;

/**
 * A placeholder fragment containing a simple view.
 */
public class GalleryActivityFragment extends Fragment {
    private Button btn;
    int PICK_IMAGE_MULTIPLE = 1;

    static private GridView gvGallery;
    private GalleryAdapter galleryAdapter;
    private View v;
    private Context ctx;


    public interface Callback {
        void onImagePickerIntent();
    }

    public GalleryActivityFragment() {

    }

    public static GalleryActivityFragment newInstance(ArrayList<Uri> arrayList) {
        GalleryActivityFragment fragment = new GalleryActivityFragment();
        Bundle args = new Bundle();
        args.putParcelableArrayList("imageUri", arrayList);
        fragment.setArguments(args);
        return fragment;

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_gallery, container, false);
        btn = view.findViewById(R.id.btn);
        gvGallery = view.findViewById(R.id.gv);
        ctx = getActivity().getApplicationContext();
        setAdapter(gvGallery);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((Callback) getActivity()).onImagePickerIntent();
                //todo implement callback in activity to create image editactivity

            }
        });


        return view;

    }

    public void setAdapter(GridView view) {


        ArrayList<StorageReference> drawableContentRefs = Utility.getDrawables(ctx);

        galleryAdapter = new GalleryAdapter(ctx, drawableContentRefs);
        view.setAdapter(galleryAdapter);
        galleryAdapter.notifyDataSetChanged();
        gvGallery.setVerticalSpacing(gvGallery.getHorizontalSpacing());
        ViewGroup.MarginLayoutParams mlp = (ViewGroup.MarginLayoutParams) gvGallery
                .getLayoutParams();
        mlp.setMargins(0, gvGallery.getHorizontalSpacing(), 0, 0);


    }

}