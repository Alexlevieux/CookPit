package com.example.android.cookpit;

import android.content.ClipData;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.app.Activity;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.example.android.cookpit.Controller.Adapter.GalleryAdapter;
import com.example.android.cookpit.Model.Cloud;
import com.example.android.cookpit.Model.Utility;
import com.example.android.cookpit.Model.pojoClass.cookPitUser;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.ArrayList;
import java.util.List;

public class GalleryActivity extends AppCompatActivity implements GalleryActivityFragment.Callback {
    int PICK_IMAGE_MULTIPLE = 1;
    View gridView;
    String imageEncoded;
    List<String> imagesEncodedList;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery);


        getFragmentManager().beginTransaction()
                .add(new GalleryActivityFragment(), "galleryFragment")
                .commit();

    }

    public interface Callback {
        void onGalleryOpening();

        void onImageadded(ArrayList<Uri> ImageUris);

    }

    @Override
    public void onImagePickerIntent() {

        Intent intent = new Intent();
        intent.setType("image/*");
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_MULTIPLE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        ArrayList<Uri> mArrayUri;
        if (requestCode == PICK_IMAGE_MULTIPLE && resultCode == RESULT_OK && null != data) {

            mArrayUri = getImageUriArray(data);


            String accountCode = getAccountCode();
            insertImageToFireStorageAndDrawable(mArrayUri, accountCode);


            getFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment, GalleryActivityFragment.newInstance(mArrayUri))
                    .commit();


        } else {
            Toast.makeText(this, "You haven't picked Image",
                    Toast.LENGTH_LONG).show();
        }

    }

    public String getAccountCode() {
        String accountCode = null;

        if (Cloud.getCloud().mUser != null) {
            cookPitUser user = Cloud.getCloud().mUser;
            accountCode = user.accountNumber;


        }
        return accountCode;


    }

    public void insertImageToFireStorageAndDrawable(final ArrayList<Uri> mArrayUri, String accountCode) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference picturesRef = storageRef.child(accountCode + "/Pictures");


        ArrayList<StorageReference> storageRefsPath = new ArrayList<>();


        for (int i = 0; i < mArrayUri.size(); i++) {

            StorageReference pictureRef = picturesRef.child(mArrayUri.get(i).getLastPathSegment());

            UploadTask uploadTask = pictureRef.putFile(mArrayUri.get(i));
            storageRefsPath.add(pictureRef);
// Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                    ArrayList<StorageReference> refs = new ArrayList<>();
                    refs.add(taskSnapshot.getStorage());

                    Utility.insertDrawableData(getApplicationContext(), refs);
                    getFragmentManager()
                            .beginTransaction()
                            .replace(R.id.fragment, new GalleryActivityFragment())
                            .commit();


                }
            });


        }

    }

    public void downloadImageFromFireStorage(ArrayList<Uri> mArrayUri, String accountCode) {
        FirebaseStorage storage = FirebaseStorage.getInstance();
        StorageReference storageRef = storage.getReference();
        StorageReference picturesRef = storageRef.child(accountCode + "/Pictures");


        for (int i = 0; i < mArrayUri.size(); i++) {

            StorageReference pictureRef = picturesRef.child(mArrayUri.get(i).getLastPathSegment());
            UploadTask uploadTask = pictureRef.putFile(mArrayUri.get(i));

// Register observers to listen for when the download is done or if it fails
            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                }
            }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    // taskSnapshot.getMetadata() contains file metadata such as size, content-type, etc.
                    // ...
                }
            });


        }


    }

    public ArrayList<Uri> getImageUriArray(Intent data) {
        Log.v("result OK and data", "test");
        String[] filePathColumn = {MediaStore.Images.Media.DATA};
        imagesEncodedList = new ArrayList<String>();
        ArrayList<Uri> imageArrayUri = new ArrayList<>();
        if (data.getData() != null) {

            Uri mImageUri = data.getData();


            // Get the cursor
            Cursor cursor = getContentResolver().query(mImageUri,
                    filePathColumn, null, null, null);
            // Move to first row
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            imageEncoded = cursor.getString(columnIndex);
            cursor.close();


            imageArrayUri.add(mImageUri);

        } else {
            if (data.getClipData() != null) {
                ClipData mClipData = data.getClipData();

                Log.v("LOG_TAG", String.valueOf(mClipData.getItemCount()));
                for (int i = 0; i < mClipData.getItemCount(); i++) {

                    ClipData.Item item = mClipData.getItemAt(i);
                    Uri uri = item.getUri();


                    // Get the cursor
                    Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);

                    // Move to first row
                    cursor.moveToFirst();

                    int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                    imageEncoded = cursor.getString(columnIndex);


                    imagesEncodedList.add(imageEncoded);


                    cursor.close();

                    imageArrayUri.add(uri);
                }
            }

        }

        return imageArrayUri;
    }

}
