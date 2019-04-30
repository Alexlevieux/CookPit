package com.example.android.cookpit.Model;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;

import com.example.android.cookpit.Model.data.KitchenDbHelper;
import com.example.android.cookpit.Model.data.KitchenContract;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import au.com.bytecode.opencsv.CSVReader;

/**
 * Created by alexandrelevieux on 30/10/2016.
 */

public class Utility {


    public static void testInsertIngredients(Context context) {
        String fileName = "ingredients";
        ContentValues[] Ingredients = new ContentValues[0];

        int IngredientsInserted;
        try {
            Ingredients = Utility.getContentValueOfCsvFile(fileName, context);
        } catch (IOException e) {
        }

        IngredientsInserted = context.getContentResolver().bulkInsert(KitchenContract.IngredientEntry.CONTENT_URI, Ingredients);
        Log.v("number of item ", String.valueOf(IngredientsInserted));
    }


    public static void testinsertMEP(Context context) {

        String fileName = "mep";
        ContentValues[] MEP = new ContentValues[0];
        try {
            MEP = Utility.getContentValueOfCsvFile(fileName, context);
        } catch (IOException e) {
        }


        for (int i = 0; i < MEP.length - 1; i++) {
            ContentValues values = MEP[i];
            Uri ingredientUri = context.getContentResolver().insert(KitchenContract.MepEntry.CONTENT_URI, values);


        }


    }

    public static void testinsertDMI(Context context) {

        String fileName = "dmi";
        ContentValues[] DMI = new ContentValues[0];
        try {
            DMI = Utility.getContentValueOfCsvFile(fileName, context);
        } catch (IOException e) {
        }


        for (int i = 0; i < DMI.length - 1; i++) {
            ContentValues values = DMI[i];
            Uri ingredientUri = context.getContentResolver().insert(KitchenContract.DishMepIngredientEntry.CONTENT_URI, values);


        }


    }

    public static Cursor getChildCursorForDishDetailAdapter(Context context, Long mep_id, String[] projection) {

        Uri uri = KitchenContract.MepIngredientEntry.CONTENT_URI;
        uri = ContentUris.withAppendedId(uri, mep_id);
        String selection = KitchenContract.MepIngredientEntry.TABLE_NAME + "." + KitchenContract.MepIngredientEntry.COLUMN_MEP_ID + " = ? ";
        String[] selectionArgs = new String[]{String.valueOf(mep_id),};
        Log.v(selection.substring(23), String.valueOf(mep_id));
        String sortOrder = KitchenContract.MepEntry.TABLE_NAME + "." + KitchenContract.MepEntry._ID + " DESC";


        return context.getContentResolver().query(
                uri,
                projection,
                selection,
                selectionArgs,
                sortOrder
        );
    }

    public static void testinsertMI(Context context) {

        String fileName = "mi";
        ContentValues[] MI = new ContentValues[0];
        try {
            MI = Utility.getContentValueOfCsvFile(fileName, context);
        } catch (IOException e) {
        }
        for (int i = 0; i < MI.length - 1; i++) {
            ContentValues values = MI[i];
            Uri ingredientUri = context.getContentResolver().insert(KitchenContract.MepIngredientEntry.CONTENT_URI, values);


        }
    }

    public static void testinsertDC(Context context) {

        String fileName = "dc";
        ContentValues[] DC = new ContentValues[0];

        try {
            DC = Utility.getContentValueOfCsvFile(fileName, context);
        } catch (IOException e) {
        }


        for (int i = 0; i < DC.length - 1; i++) {
            ContentValues values = DC[i];
            Uri DishCategoryUri = context.getContentResolver().insert(KitchenContract.DishCategoryEntry.CONTENT_URI, values);

        }

    }


    public static boolean deleteTheDatabase(Context mContext) {
        KitchenDbHelper helper = new KitchenDbHelper(mContext);
        SQLiteDatabase db = helper.getWritableDatabase();
        helper.close();

        db.close();
        boolean isdeleted = mContext.deleteDatabase(KitchenDbHelper.DATABASE_NAME);

        return isdeleted;
    }


    public static void createDatabase(Context mContext) {
        KitchenDbHelper helper = new KitchenDbHelper(
                mContext);

        SQLiteDatabase db = helper.getWritableDatabase();


        db.close();

    }

    public static void insertNewMenuData(Context mContext) {

        String NewmenufileName = "puremagic";
        String username = "Francois";
        String NewmenuName = "Puremagic Achill 2014";

        ContentValues[] testCSV = new ContentValues[0];
        try {
            testCSV = Utility.getContentValueOfCsvFile(NewmenufileName, mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ContentValues newMenu = new ContentValues();
        newMenu.put(KitchenContract.MenuEntry.COLUMN_MENU_NAME, NewmenuName);
        newMenu.put(KitchenContract.MenuEntry.COLUMN_USER_NAME, username);
        Uri menuUri = mContext.getContentResolver().insert(KitchenContract.MenuEntry.CONTENT_URI, newMenu);
        long menuId = ContentUris.parseId(menuUri);

        for (int i = 0; i < testCSV.length - 1; i++) {
            ContentValues values = testCSV[i];
            String courseCategory = values.getAsString(KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME);
            int coursePosition = values.getAsInteger(KitchenContract.MenuDishEntry.COLUMN_COURSE_POSITION);
            int dishPosition = values.getAsInteger(KitchenContract.MenuDishEntry.COLUMN_DISH_POSITION);

            if (Utility.doesCourseCategoryExist(courseCategory, mContext)) {

                ContentValues newCategory = new ContentValues();
                newCategory.put(KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME, courseCategory);
                Uri courseCategoryUri = mContext.getContentResolver().insert(KitchenContract.CourseCategoryEntry.CONTENT_URI, newCategory);


            }


            String dishName = values.getAsString(KitchenContract.DishEntry.COLUMN_DISH_NAME);


            ContentValues dishValue = new ContentValues();
            dishValue.put(KitchenContract.DishEntry.COLUMN_DISH_NAME, dishName);
            dishValue.put(KitchenContract.DishEntry.COLUMN_DESCRIPTION, values.getAsString(KitchenContract.DishEntry.COLUMN_DESCRIPTION));
            dishValue.put(KitchenContract.DishEntry.COLUMN_USER_NAME, "KitchenDriveAlex");
            Uri DishUri = mContext.getContentResolver().insert(KitchenContract.DishEntry.CONTENT_URI, dishValue);
            long DishRowId = ContentUris.parseId(DishUri);


            ContentValues menuDishValue = new ContentValues();
            Long CoursecategoryId = Utility.getcourseCategoryId(courseCategory, mContext);

            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_MENU_ID, menuId);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID, CoursecategoryId);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_DISH_ID, DishRowId);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_COURSE_POSITION, coursePosition);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_DISH_POSITION, dishPosition);

            Uri MenuDishUri = mContext.getContentResolver().insert(KitchenContract.MenuDishEntry.CONTENT_URI, menuDishValue);


        }


    }

    public static ArrayList<StorageReference> getDrawables(Context ctx) {

        String[] projection = new String[]{
                KitchenContract.DrawableEntry.COLUMN_DRAWABLE_URI,
                KitchenContract.DrawableEntry.COLUMN_DATE_TIME_CREATED,
                KitchenContract.DrawableEntry.COLUMN_DISH_ID,
                KitchenContract.DrawableEntry.COLUMN_PICTURE_TAGS_BLOB

        };

        Cursor c = ctx.getContentResolver().query(KitchenContract.DrawableEntry.CONTENT_URI, projection, null, null, null);
        c.moveToFirst();
        ArrayList<StorageReference> refs = new ArrayList<>();
        FirebaseStorage storage = FirebaseStorage.getInstance();


        for (int i = 0; i < c.getCount(); i++) {


            String path = c.getString(c.getColumnIndex(KitchenContract.DrawableEntry.COLUMN_DRAWABLE_URI));
            Log.v("one image ref : ", path);

            StorageReference ref = storage.getReference(path);
            refs.add(ref);
            c.moveToNext();
        }


        c.close();


        return refs;

    }

    public static void insertDrawableData(Context mContext, ArrayList<StorageReference> storageRefsPath) {


        for (int i = 0; i < storageRefsPath.size(); i++) {
            ContentValues drawableValue = new ContentValues();

            StorageReference refPath = storageRefsPath.get(i);


            String DrawableUri = refPath.getPath();
            String DrawableFileName = refPath.getName();
            int defaultDrawable = 0;
            Long drawableDateAndTime = Calendar.getInstance().getTimeInMillis();
            String drawableTagsBlob = null;

            drawableValue.put(KitchenContract.DrawableEntry.COLUMN_DRAWABLE_URI, DrawableUri);
            drawableValue.put(KitchenContract.DrawableEntry.COLUMN_FILE_NAME, DrawableFileName);
            drawableValue.put(KitchenContract.DrawableEntry.COLUMN_DEFAULT_DRAWABLE, defaultDrawable);
            drawableValue.put(KitchenContract.DrawableEntry.COLUMN_DATE_TIME_CREATED, drawableDateAndTime);
            drawableValue.put(KitchenContract.DrawableEntry.COLUMN_PICTURE_TAGS_BLOB, drawableTagsBlob);


            Uri drawableUri = mContext.getContentResolver().insert(KitchenContract.DrawableEntry.CONTENT_URI, drawableValue);


            Log.v(String.valueOf(drawableUri), " inserted");


        }


    }

    public static void insertsecondNewMenuData(Context mContext) {
        String NewmenufileName = "menucsvopium";
        String username = "Alex";
        String NewmenuName = "Opium Dinner Menu";

        ContentValues[] testCSV = new ContentValues[0];
        try {
            testCSV = Utility.getContentValueOfCsvFile(NewmenufileName, mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ContentValues newMenu = new ContentValues();

        newMenu.put(KitchenContract.MenuEntry.COLUMN_MENU_NAME, NewmenuName);
        newMenu.put(KitchenContract.MenuEntry.COLUMN_USER_NAME, username);

        Uri menuUri = mContext.getContentResolver().insert(KitchenContract.MenuEntry.CONTENT_URI, newMenu);
        long menuId = ContentUris.parseId(menuUri);

        for (int i = 0; i < testCSV.length - 1; i++) {
            ContentValues values = testCSV[i];
            String courseCategory = values.getAsString(KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME);
            int coursePosition = values.getAsInteger(KitchenContract.MenuDishEntry.COLUMN_COURSE_POSITION);
            int dishPosition = values.getAsInteger(KitchenContract.MenuDishEntry.COLUMN_DISH_POSITION);

            if (Utility.doesCourseCategoryExist(courseCategory, mContext)) {

                ContentValues newCategory = new ContentValues();
                newCategory.put(KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME, courseCategory);
                Uri courseCategoryUri = mContext.getContentResolver().insert(KitchenContract.CourseCategoryEntry.CONTENT_URI, newCategory);


            }


            String dishName = values.getAsString(KitchenContract.DishEntry.COLUMN_DISH_NAME);


            ContentValues dishValue = new ContentValues();

            dishValue.put(KitchenContract.DishEntry.COLUMN_DISH_NAME, dishName);
            dishValue.put(KitchenContract.DishEntry.COLUMN_DESCRIPTION, values.getAsString(KitchenContract.DishEntry.COLUMN_DESCRIPTION));
            dishValue.put(KitchenContract.DishEntry.COLUMN_USER_NAME, "KitchenDriveAlex");

            Uri DishUri = mContext.getContentResolver().insert(KitchenContract.DishEntry.CONTENT_URI, dishValue);
            long DishRowId = ContentUris.parseId(DishUri);


            ContentValues menuDishValue = new ContentValues();
            Long CoursecategoryId = Utility.getcourseCategoryId(courseCategory, mContext);

            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_MENU_ID, menuId);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID, CoursecategoryId);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_DISH_ID, DishRowId);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_COURSE_POSITION, coursePosition);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_DISH_POSITION, dishPosition);

            Uri MenuDishUri = mContext.getContentResolver().insert(KitchenContract.MenuDishEntry.CONTENT_URI, menuDishValue);


        }


    }

    public static void insertThirdNewMenuData(Context mContext) {
        String NewmenufileName = "fallonbyrnepeoplepark";
        String username = "Tom";
        String NewmenuName = "Fallon & Byrne People's Park";

        ContentValues[] testCSV = new ContentValues[0];
        try {
            testCSV = Utility.getContentValueOfCsvFile(NewmenufileName, mContext);
        } catch (IOException e) {
            e.printStackTrace();
        }


        ContentValues newMenu = new ContentValues();

        newMenu.put(KitchenContract.MenuEntry.COLUMN_MENU_NAME, NewmenuName);
        newMenu.put(KitchenContract.MenuEntry.COLUMN_USER_NAME, username);

        Uri menuUri = mContext.getContentResolver().insert(KitchenContract.MenuEntry.CONTENT_URI, newMenu);
        long menuId = ContentUris.parseId(menuUri);

        for (int i = 0; i < testCSV.length - 1; i++) {
            ContentValues values = testCSV[i];
            String courseCategory = values.getAsString(KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME);
            int coursePosition = values.getAsInteger(KitchenContract.MenuDishEntry.COLUMN_COURSE_POSITION);
            int dishPosition = values.getAsInteger(KitchenContract.MenuDishEntry.COLUMN_DISH_POSITION);

            if (Utility.doesCourseCategoryExist(courseCategory, mContext)) {

                ContentValues newCategory = new ContentValues();
                newCategory.put(KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME, courseCategory);
                Uri courseCategoryUri = mContext.getContentResolver().insert(KitchenContract.CourseCategoryEntry.CONTENT_URI, newCategory);


            }


            String dishName = values.getAsString(KitchenContract.DishEntry.COLUMN_DISH_NAME);


            ContentValues dishValue = new ContentValues();

            dishValue.put(KitchenContract.DishEntry.COLUMN_DISH_NAME, dishName);
            dishValue.put(KitchenContract.DishEntry.COLUMN_DESCRIPTION, values.getAsString(KitchenContract.DishEntry.COLUMN_DESCRIPTION));
            dishValue.put(KitchenContract.DishEntry.COLUMN_USER_NAME, "KitchenDriveAlex");

            Uri DishUri = mContext.getContentResolver().insert(KitchenContract.DishEntry.CONTENT_URI, dishValue);
            long DishRowId = ContentUris.parseId(DishUri);


            ContentValues menuDishValue = new ContentValues();
            Long CoursecategoryId = Utility.getcourseCategoryId(courseCategory, mContext);

            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_MENU_ID, menuId);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID, CoursecategoryId);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_DISH_ID, DishRowId);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_COURSE_POSITION, coursePosition);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_DISH_POSITION, dishPosition);

            Uri MenuDishUri = mContext.getContentResolver().insert(KitchenContract.MenuDishEntry.CONTENT_URI, menuDishValue);


        }


    }

    public static long getcourseCategoryId(String courseName, Context context) {
        String[] selectionArgs = new String[]{courseName};
        String[] projection = new String[]{
                KitchenContract.CourseCategoryEntry.TABLE_NAME + "." + KitchenContract.CourseCategoryEntry._ID,
                KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME};
        String selection = KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME + " = ? ";
        Cursor c = context.getContentResolver().query(
                KitchenContract.CourseCategoryEntry.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);

        c.moveToFirst();
        Long id = c.getLong(0);
        c.close();
        return id;
    }

    public static boolean doesCourseCategoryExist(String courseName, Context context) {
        String[] selectionArgs;
        selectionArgs = new String[]{courseName};
        String[] projection = new String[]{KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME};
        String selection = KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME + " = ? ";
        Cursor c = context.getContentResolver().query(
                KitchenContract.CourseCategoryEntry.CONTENT_URI,
                null,
                selection,
                selectionArgs,
                null);
        boolean catExist = c.getCount() == 0;
        c.close();

        return catExist;
    }


    public static ContentValues[] getContentValueOfCsvFile(String csvFileName, Context context) throws IOException {


        InputStream inputstream = context.getResources().openRawResource(context.getResources().getIdentifier(csvFileName, "raw", context.getPackageName()));
        CSVReader reader = new CSVReader(new InputStreamReader(inputstream));
        int lines = 0;
        int column = 0;
        try {
            if (lines == 0) {
                String[] columnIndex = reader.readNext();
                column = columnIndex.length;
                lines++;
            }

            while (reader.readNext() != null) lines++;
            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }


        ContentValues[] ContentValueOfCsv = new ContentValues[lines];


        try {
            int counter = 0;
            String[] file;
            InputStream stream = context.getResources().openRawResource(context.getResources().getIdentifier(csvFileName, "raw", context.getPackageName()));

            CSVReader buffer = new CSVReader(new InputStreamReader(stream));

            String[] index = new String[column];

            for (; ; ) {

                file = buffer.readNext();

                if (file != null) {


                    if (counter == 0) {

                        for (int i = 0; i < file.length; i++) {

                            String columnIndex = file[i];
                            index[i] = columnIndex;

                        }

                    } else {
                        ContentValues Row = new ContentValues();
                        for (int i = 0; i < file.length; i++) {

                            String columna = index[i];
                            String value = String.valueOf(file[i]);

                            Row.put(columna, value);
                        }

                        ContentValueOfCsv[counter - 1] = Row;
                    }
                    counter = counter + 1;
                } else break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ContentValueOfCsv;
    }

    public static boolean insertDataFromSequence(Context context, int level, DataSnapshot dataSnapshot) {


        switch (level) {
            case 0: {
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    Uri uri = KitchenContract.BASE_CONTENT_URI.buildUpon().appendPath(data.getKey()).build();
                    int count = (int) data.getChildrenCount();
                    ContentValues[] entries = new ContentValues[count];
                    Log.v(uri.getPath(), String.valueOf(count));
                    int i = 0;
                    for (DataSnapshot dataSnapshot1 : data.getChildren()) {

                        ContentValues value = new ContentValues();

                        for (DataSnapshot key : dataSnapshot1.getChildren()) {
                            value.put(key.getKey(), (String) key.getValue());
                        }

                        entries[i] = value;
                        i++;

                    }
                    int numberOfInsert = context.getContentResolver().bulkInsert(uri, entries);
                    Log.v(uri.getPath(), String.valueOf(numberOfInsert));
                }
                break;
            }

            case 1: {
                break;
            }
            case 2: {
                break;
            }
            case 3: {
                break;
            }

        }
        return true;


    }


}


