package com.example.android.cookpit.data;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteQueryBuilder;
import android.database.sqlite.SQLiteStatement;
import android.net.Uri;
import android.util.Log;

import java.util.ArrayList;

/**
 * Created by alexandrelevieux on 20/10/2016.
 */

public class cookpitProvider extends ContentProvider {


    public static final UriMatcher sUriMatcher = buildUriMatcher();


    static final int MENU = 100;
    static final int MENU_WITH_NAME_OR_DISH_NAME_OR_DISH_CATEGORY_NAME_OR_MEP_NAME_OR_INGREDIENT_NAME = 101;

    static final int DISH = 200;
    static final int DISH_WITH_NAME_OR_DISH_CATEGORY_NAME_OR_MEP_NAME_OR_INGREDIENT_NAME = 201;
    static final int DISH_WITH_COURSE_CATEGORY_AND_MENU = 202;
    static final int DISH_WITH_ID = 203;

    static final int MEP = 300;
    static final int MEP_WITH_NAME_OR_MEP_NAME_OR_INGREDIENT_NAME = 301;
    static final int MEP_AND_INGREDIENT_WITH_DISH = 302;
    static final int MEP_AND_INGREDIENT_WITH_MEP = 303;

    static final int INGREDIENT = 400;
    static final int INGREDIENT_WITH_ID = 401;
    static final int INGREDIENT_WITH_CATEGORY = 402;
    static final int INGREDIENT_WITH_CATEGORY_AND_SUBCATEGORY = 403;
    static final int INGREDIENT_WITH_CATEGORY_AND_SUBCATEGORY_AND_DETAILCATEGORY = 404;
    static final int INGREDIENT_WITH_NAME = 405;

    static final int MENU_DISH = 500;

    static final int DISH_MEP_INGREDIENTS = 600;

    static final int MEP_INGREDIENT = 700;

    static final int MESURE = 800;

    static final int COURSE_CATEGORY = 900;
    static final int COURSE_CATEGORY_WITH_MENU_ID = 901;

    static final int DISH_CATEGORY = 1000;

    static final int INGREDIENT_CATEGORY = 1100;

    static final int INGREDIENT_SUB_CATEGORY = 1200;

    static final int INGREDIENT_DETAIL_CATEGORY = 1300;

    static final int FTS_MENU = 1400;
    static final int FTS_DISH = 1410;
    static final int FTS_MEP = 1420;
    static final int FTS_INGREDIENT = 1430;


    static UriMatcher buildUriMatcher() {

        final UriMatcher matcher = new UriMatcher(UriMatcher.NO_MATCH);
        final String authority = KitchenContract.CONTENT_AUTHORITY;

        matcher.addURI(authority, KitchenContract.PATH_FTS_MENU + "/*", FTS_MENU);
        matcher.addURI(authority, KitchenContract.PATH_FTS_DISH + "/*", FTS_DISH);
        matcher.addURI(authority, KitchenContract.PATH_FTS_MEP + "/*", FTS_MEP);
        matcher.addURI(authority, KitchenContract.PATH_FTS_INGREDIENT + "/*", FTS_INGREDIENT);

        matcher.addURI(authority, KitchenContract.PATH_MENU, MENU);
        matcher.addURI(authority, KitchenContract.PATH_MENU + "/*", MENU_WITH_NAME_OR_DISH_NAME_OR_DISH_CATEGORY_NAME_OR_MEP_NAME_OR_INGREDIENT_NAME);

        matcher.addURI(authority, KitchenContract.PATH_DISH, DISH);
        matcher.addURI(authority, KitchenContract.PATH_DISH + "/*", DISH_WITH_COURSE_CATEGORY_AND_MENU);
        matcher.addURI(authority, KitchenContract.PATH_DISH + "/*/*", DISH_WITH_ID);


        matcher.addURI(authority, KitchenContract.PATH_MEP, MEP);
        matcher.addURI(authority, KitchenContract.PATH_MEP + "/*", MEP_WITH_NAME_OR_MEP_NAME_OR_INGREDIENT_NAME);

        matcher.addURI(authority, KitchenContract.PATH_INGREDIENT, INGREDIENT);
        matcher.addURI(authority, KitchenContract.PATH_INGREDIENT + "/#", INGREDIENT_WITH_ID);
        matcher.addURI(authority, KitchenContract.PATH_INGREDIENT + "/" + KitchenContract.PATH_INGREDIENT_CATEGORY + "/#", INGREDIENT_WITH_CATEGORY);
        matcher.addURI(authority, KitchenContract.PATH_INGREDIENT + "/" + KitchenContract.PATH_INGREDIENT_SUBCATEGORY + "/#", INGREDIENT_WITH_CATEGORY_AND_SUBCATEGORY);
        matcher.addURI(authority, KitchenContract.PATH_INGREDIENT + "/" + KitchenContract.PATH_INGREDIENT_DETAIL_CATEGORY + "/#", INGREDIENT_WITH_CATEGORY_AND_SUBCATEGORY_AND_DETAILCATEGORY);
        matcher.addURI(authority, KitchenContract.PATH_INGREDIENT + "/*", INGREDIENT_WITH_NAME);

        matcher.addURI(authority, KitchenContract.PATH_MENU_DISH, MENU_DISH);

        matcher.addURI(authority, KitchenContract.PATH_DISH_MEP_INGREDIENT, DISH_MEP_INGREDIENTS);
        matcher.addURI(authority, KitchenContract.PATH_DISH_MEP_INGREDIENT + "/#", MEP_AND_INGREDIENT_WITH_DISH);

        matcher.addURI(authority, KitchenContract.PATH_MEP_INGREDIENT, MEP_INGREDIENT);
        matcher.addURI(authority, KitchenContract.PATH_MEP_INGREDIENT + "/#", MEP_AND_INGREDIENT_WITH_MEP);

        matcher.addURI(authority, KitchenContract.PATH_MESURE, MESURE);
        matcher.addURI(authority, KitchenContract.PATH_COURSE_CATEGORY, COURSE_CATEGORY);
        matcher.addURI(authority, KitchenContract.PATH_COURSE_CATEGORY + "/#", COURSE_CATEGORY_WITH_MENU_ID);
        matcher.addURI(authority, KitchenContract.PATH_DISH_CATEGORY, DISH_CATEGORY);
        matcher.addURI(authority, KitchenContract.PATH_INGREDIENT_CATEGORY, INGREDIENT_CATEGORY);
        matcher.addURI(authority, KitchenContract.PATH_INGREDIENT_SUBCATEGORY, INGREDIENT_SUB_CATEGORY);
        matcher.addURI(authority, KitchenContract.PATH_INGREDIENT_DETAIL_CATEGORY, INGREDIENT_DETAIL_CATEGORY);

        return matcher;
    }

    @Override
    public boolean onCreate() {
        KitchenDbHelper mOpenHelper;
        mOpenHelper = new KitchenDbHelper(getContext());
        return true;
    }


    @Override
    public String getType(Uri uri) {
        KitchenDbHelper mOpenHelper;
        mOpenHelper = new KitchenDbHelper(getContext());

        final int match = sUriMatcher.match(uri);

        switch (match) {

            case FTS_MENU:
                return KitchenContract.FtsMenuEntry.CONTENT_TYPE;
            case FTS_DISH:
                return KitchenContract.FtsDishEntry.CONTENT_TYPE;
            case FTS_MEP:
                return KitchenContract.FtsMepEntry.CONTENT_TYPE;
            case FTS_INGREDIENT:
                return KitchenContract.FtsIngredientEntry.CONTENT_TYPE;

            case MENU:
                return KitchenContract.MenuEntry.CONTENT_TYPE;
            case MENU_WITH_NAME_OR_DISH_NAME_OR_DISH_CATEGORY_NAME_OR_MEP_NAME_OR_INGREDIENT_NAME:
                return KitchenContract.MenuEntry.CONTENT_TYPE;

            case DISH:
                return KitchenContract.DishEntry.CONTENT_TYPE;
            case DISH_WITH_NAME_OR_DISH_CATEGORY_NAME_OR_MEP_NAME_OR_INGREDIENT_NAME:
                return KitchenContract.DishEntry.CONTENT_TYPE;
            case DISH_WITH_COURSE_CATEGORY_AND_MENU:
                return KitchenContract.DishEntry.CONTENT_TYPE;
            case DISH_WITH_ID:
                return KitchenContract.DishEntry.CONTENT_TYPE;


            case MEP:
                return KitchenContract.MepEntry.CONTENT_TYPE;
            case MEP_WITH_NAME_OR_MEP_NAME_OR_INGREDIENT_NAME:
                return KitchenContract.MepEntry.CONTENT_TYPE;
            case MEP_AND_INGREDIENT_WITH_DISH:
                return KitchenContract.DishMepIngredientEntry.CONTENT_TYPE;
            case MEP_AND_INGREDIENT_WITH_MEP:
                return KitchenContract.MepEntry.CONTENT_TYPE;

            case INGREDIENT:
                return KitchenContract.IngredientEntry.CONTENT_TYPE;
            case INGREDIENT_WITH_NAME:
                return KitchenContract.IngredientEntry.CONTENT_TYPE;
            case INGREDIENT_WITH_ID:
                return KitchenContract.IngredientEntry.CONTENT_ITEM_TYPE;
            case INGREDIENT_WITH_CATEGORY:
                return KitchenContract.IngredientEntry.CONTENT_TYPE;
            case INGREDIENT_WITH_CATEGORY_AND_SUBCATEGORY:
                return KitchenContract.IngredientEntry.CONTENT_TYPE;
            case INGREDIENT_WITH_CATEGORY_AND_SUBCATEGORY_AND_DETAILCATEGORY:
                return KitchenContract.IngredientEntry.CONTENT_TYPE;

            case MENU_DISH:
                return KitchenContract.MenuDishEntry.CONTENT_TYPE;

            case DISH_MEP_INGREDIENTS:
                return KitchenContract.DishMepIngredientEntry.CONTENT_TYPE;

            case MEP_INGREDIENT:
                return KitchenContract.MepIngredientEntry.CONTENT_TYPE;

            case MESURE:
                return KitchenContract.MesureEntry.CONTENT_TYPE;

            case COURSE_CATEGORY:
                return KitchenContract.CourseCategoryEntry.CONTENT_TYPE;
            case COURSE_CATEGORY_WITH_MENU_ID:
                return KitchenContract.CourseCategoryEntry.CONTENT_TYPE;


            case DISH_CATEGORY:
                return KitchenContract.DishCategoryEntry.CONTENT_TYPE;

            case INGREDIENT_CATEGORY:
                return KitchenContract.IngredientCategoryEntry.CONTENT_TYPE;

            case INGREDIENT_SUB_CATEGORY:
                return KitchenContract.IngredientSubCategoryEntry.CONTENT_TYPE;

            case INGREDIENT_DETAIL_CATEGORY:
                return KitchenContract.IngredientDetailCategoryEntry.CONTENT_TYPE;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

    }

    private static final SQLiteQueryBuilder courseCategoryWithMenuIDQueryBuilder;

    static {
        courseCategoryWithMenuIDQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        courseCategoryWithMenuIDQueryBuilder.setTables(
                KitchenContract.CourseCategoryEntry.TABLE_NAME + " INNER JOIN " +
                        KitchenContract.MenuDishEntry.TABLE_NAME +
                        " ON " + KitchenContract.CourseCategoryEntry.TABLE_NAME +
                        "." + KitchenContract.CourseCategoryEntry._ID +
                        " = " + KitchenContract.MenuDishEntry.TABLE_NAME +
                        "." + KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID);
    }


    private static final SQLiteQueryBuilder dishWithCourseCategoryAndMenuIDQueryBuilder;

    static {
        dishWithCourseCategoryAndMenuIDQueryBuilder = new SQLiteQueryBuilder();

        //This is an inner join which looks like
        //weather INNER JOIN location ON weather.location_id = location._id
        dishWithCourseCategoryAndMenuIDQueryBuilder.setTables(
                KitchenContract.DishEntry.TABLE_NAME + " INNER JOIN " +
                        KitchenContract.MenuDishEntry.TABLE_NAME +
                        " ON " + KitchenContract.DishEntry.TABLE_NAME +
                        "." + KitchenContract.DishEntry._ID +
                        " = " + KitchenContract.MenuDishEntry.TABLE_NAME +
                        "." + KitchenContract.MenuDishEntry.COLUMN_DISH_ID);
    }

    private static final SQLiteQueryBuilder IngredientandMepwithDishIDQueryBuilder;

    static {

        IngredientandMepwithDishIDQueryBuilder = new SQLiteQueryBuilder();
        IngredientandMepwithDishIDQueryBuilder.setTables(
                KitchenContract.DishMepIngredientEntry.TABLE_NAME +
                        " LEFT OUTER JOIN " +
                        KitchenContract.MepEntry.TABLE_NAME +
                        " ON " +
                        KitchenContract.MepEntry.TABLE_NAME + " . " + KitchenContract.MepEntry._ID +
                        " = " +
                        KitchenContract.DishMepIngredientEntry.TABLE_NAME + "." + KitchenContract.DishMepIngredientEntry.COLUMN_MEP_ID +
                        " LEFT OUTER JOIN " +
                        KitchenContract.IngredientEntry.TABLE_NAME +
                        " ON " +
                        KitchenContract.IngredientEntry.TABLE_NAME + " . " + KitchenContract.IngredientEntry._ID +
                        " = " +
                        KitchenContract.DishMepIngredientEntry.TABLE_NAME + "." + KitchenContract.DishMepIngredientEntry.COLUMN_INGREDIENT_ID);
    }

    private static final SQLiteQueryBuilder IngredientandMepwithMepIDQueryBuilder;

    static {

        IngredientandMepwithMepIDQueryBuilder = new SQLiteQueryBuilder();
        IngredientandMepwithMepIDQueryBuilder.setTables(
                KitchenContract.MepIngredientEntry.TABLE_NAME +
                        " LEFT OUTER JOIN " +
                        KitchenContract.MepEntry.TABLE_NAME +
                        " ON " +
                        KitchenContract.MepEntry.TABLE_NAME + " . " + KitchenContract.MepEntry._ID +
                        " = " +
                        KitchenContract.MepIngredientEntry.TABLE_NAME + "." + KitchenContract.MepIngredientEntry.COLUMN_SUB_MEP_ID +
                        " LEFT OUTER JOIN " +
                        KitchenContract.IngredientEntry.TABLE_NAME +
                        " ON " +
                        KitchenContract.IngredientEntry.TABLE_NAME + " . " + KitchenContract.IngredientEntry._ID +
                        " = " +
                        KitchenContract.MepIngredientEntry.TABLE_NAME + "." + KitchenContract.MepIngredientEntry.COLUMN_INGREDIENT_ID);
    }

    @Override
    public Cursor query(Uri uri, String[] projection, String selection, String[] selectionArgs, String sortOrder) {
        KitchenDbHelper mOpenHelper;
        mOpenHelper = new KitchenDbHelper(getContext());
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();
        // Here's the switch statement that, given a URI, will determine what kind of request it is,
        // and query the database accordingly.
        Cursor retCursor;
        switch (sUriMatcher.match(uri)) {

            case FTS_MENU: {
                String value = selectionArgs[0];
                Log.v(value, "testmenu");
                retCursor = mOpenHelper.getReadableDatabase().rawQuery("SELECT docid,* FROM " + KitchenContract.FtsMenuEntry.TABLE_NAME + " WHERE " + KitchenContract.FtsMenuEntry.TABLE_NAME + " MATCH ? ", selectionArgs);

                break;
            }
            case FTS_DISH: {
                String value = selectionArgs[0];
                Log.v(value, "testDish");
                retCursor = mOpenHelper.getReadableDatabase().rawQuery("SELECT docid,* FROM " + KitchenContract.FtsDishEntry.TABLE_NAME + " WHERE " + KitchenContract.FtsDishEntry.TABLE_NAME + " MATCH ? ", selectionArgs);

                break;
            }

            case FTS_MEP: {
                String value = selectionArgs[0];
                Log.v(value, "testMep");
                retCursor = mOpenHelper.getReadableDatabase().rawQuery("SELECT docid,* FROM " + KitchenContract.FtsMepEntry.TABLE_NAME + " WHERE " + KitchenContract.FtsMepEntry.TABLE_NAME + " MATCH ? ", selectionArgs);

                break;
            }
            case FTS_INGREDIENT: {
                String value = selectionArgs[0];
                Log.v(value, "testIngredient");
                retCursor = mOpenHelper.getReadableDatabase().rawQuery("SELECT docid,* FROM " + KitchenContract.FtsIngredientEntry.TABLE_NAME + " WHERE " + KitchenContract.FtsIngredientEntry.TABLE_NAME + " MATCH ? ", selectionArgs);

                break;
            }


            case COURSE_CATEGORY_WITH_MENU_ID: {


                retCursor = courseCategoryWithMenuIDQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID,
                        null,
                        sortOrder);
                break;
            }
            case DISH_WITH_COURSE_CATEGORY_AND_MENU: {

                retCursor = dishWithCourseCategoryAndMenuIDQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;


            }

            case MEP_AND_INGREDIENT_WITH_DISH: {


                retCursor = IngredientandMepwithDishIDQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }

            case MEP_AND_INGREDIENT_WITH_MEP: {


                retCursor = IngredientandMepwithMepIDQueryBuilder.query(mOpenHelper.getReadableDatabase(),
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder);
                break;
            }
            case DISH_WITH_ID: {


                retCursor = mOpenHelper.getReadableDatabase().query(
                        KitchenContract.DishEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MENU: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KitchenContract.MenuEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case DISH: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KitchenContract.DishEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MEP: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KitchenContract.MepEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case INGREDIENT: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KitchenContract.IngredientEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case MENU_DISH: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KitchenContract.MenuDishEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }
            case COURSE_CATEGORY: {
                retCursor = mOpenHelper.getReadableDatabase().query(
                        KitchenContract.CourseCategoryEntry.TABLE_NAME,
                        projection,
                        selection,
                        selectionArgs,
                        null,
                        null,
                        sortOrder
                );
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        retCursor.setNotificationUri(getContext().getContentResolver(), uri);
        return retCursor;
    }


    @Override
    public Uri insert(Uri uri, ContentValues values) {
        KitchenDbHelper mOpenHelper;
        mOpenHelper = new KitchenDbHelper(getContext());
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        final String FTS_menu_column_projection = "docid," + KitchenContract.MenuEntry.COLUMN_MENU_NAME + "," + KitchenContract.MenuEntry.COLUMN_USER_NAME;

        Uri returnUri = null;

        switch (match) {

            case MENU: {

                db.beginTransaction();
                int nbrOfRow = 0;

                long _id = db.insert(KitchenContract.MenuEntry.TABLE_NAME, null, values);

                if (_id > 0) {
                    returnUri = KitchenContract.MenuEntry.buildMenuUri(_id);
                } else {
                    nbrOfRow = db.update(KitchenContract.MenuEntry.TABLE_NAME, values, KitchenContract.MenuEntry._ID + " = " + values.getAsString(KitchenContract.MenuEntry._ID), null);
                }
                db.execSQL("INSERT INTO " +
                        KitchenContract.FtsMenuEntry.TABLE_NAME + " (docid," +
                        KitchenContract.MenuEntry.COLUMN_MENU_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_USER_NAME + ") SELECT " +
                        _id + " , " +
                        KitchenContract.MenuEntry.COLUMN_MENU_NAME + " , " +
                        KitchenContract.MenuEntry.COLUMN_USER_NAME + " FROM " +
                        KitchenContract.MenuEntry.TABLE_NAME + " ;");
                Log.v("Menu insert FTS table", String.valueOf(_id));
                if (_id >= 2) {
                    db.execSQL("INSERT INTO " +
                            KitchenContract.FtsMenuEntry.TABLE_NAME + "( " + KitchenContract.FtsMenuEntry.TABLE_NAME + ") VALUES('rebuild'); ");
                }
                db.setTransactionSuccessful();
                db.endTransaction();
                if (nbrOfRow > 0) {
                    returnUri = KitchenContract.MenuEntry.buildMenuUri(values.getAsLong(KitchenContract.MenuEntry._ID));
                }
                if (returnUri == null) {

                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MEP: {
                int nbrOfRow = 0;
                db.beginTransaction();
                long _id = db.insert(KitchenContract.MepEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KitchenContract.MepEntry.buildMepUri(_id);
                else {
                    nbrOfRow = db.update(KitchenContract.MepEntry.TABLE_NAME, values, KitchenContract.MepEntry._ID + " = " + values.getAsString(KitchenContract.MepEntry._ID), null);

                }
                db.execSQL("INSERT INTO " +
                        KitchenContract.FtsMepEntry.TABLE_NAME + " (docid," +
                        KitchenContract.MepEntry.COLUMN_MEP_NAME + ", " +
                        KitchenContract.MepEntry.COLUMN_DESCRIPTION + ") SELECT " +
                        _id + " , " +
                        KitchenContract.MepEntry.COLUMN_MEP_NAME + " , " +
                        KitchenContract.MepEntry.COLUMN_DESCRIPTION + " FROM " +
                        KitchenContract.MepEntry.TABLE_NAME + " ;");
                Log.v("Mep FTS table", String.valueOf(_id));

                db.execSQL("INSERT INTO " +
                        KitchenContract.FtsMepEntry.TABLE_NAME + "( " + KitchenContract.FtsMepEntry.TABLE_NAME + ") VALUES('rebuild'); ");
                db.setTransactionSuccessful();
                db.endTransaction();

                if (nbrOfRow > 0) {
                    returnUri = KitchenContract.MepEntry.buildMepUri(values.getAsLong(KitchenContract.MepEntry._ID));
                }
                if (returnUri == null) {

                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case DISH_MEP_INGREDIENTS: {

                int nbrOfRow = 0;

                long _id = db.insert(KitchenContract.DishMepIngredientEntry.TABLE_NAME, null, values);

                if (_id > 0)
                    returnUri = KitchenContract.DishMepIngredientEntry.buildDmiUri(_id);
                else {
                    nbrOfRow = db.update(KitchenContract.DishMepIngredientEntry.TABLE_NAME, values, KitchenContract.DishMepIngredientEntry._ID + " = " + values.getAsString(KitchenContract.DishMepIngredientEntry._ID), null);
                }
                if (nbrOfRow > 0) {
                    returnUri = KitchenContract.DishMepIngredientEntry.buildDmiUri(values.getAsLong(KitchenContract.DishMepIngredientEntry._ID));
                }
                if (returnUri == null) {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }

                break;
            }
            case MEP_INGREDIENT: {
                int nbrOfRow = 0;

                long _id = db.insert(KitchenContract.MepIngredientEntry.TABLE_NAME, null, values);

                if (_id > 0)
                    returnUri = KitchenContract.MepIngredientEntry.buildMiUri(_id);
                else {
                    nbrOfRow = db.update(KitchenContract.MepIngredientEntry.TABLE_NAME, values, KitchenContract.MepIngredientEntry._ID + " = " + values.getAsString(KitchenContract.MepIngredientEntry._ID), null);
                }
                if (nbrOfRow > 0) {
                    returnUri = KitchenContract.MepIngredientEntry.buildMiUri(values.getAsLong(KitchenContract.MepIngredientEntry._ID));
                }
                if (returnUri == null) {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case DISH_CATEGORY: {
                int nbrOfRow = 0;

                long _id = db.insert(KitchenContract.DishCategoryEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KitchenContract.DishCategoryEntry.buildDcUri(_id);
                else {
                    nbrOfRow = db.update(KitchenContract.DishCategoryEntry.TABLE_NAME, values, KitchenContract.DishCategoryEntry._ID + " = " + values.getAsString(KitchenContract.DishCategoryEntry._ID), null);
                }
                if (nbrOfRow > 0) {
                    returnUri = KitchenContract.DishCategoryEntry.buildDcUri(values.getAsLong(KitchenContract.DishCategoryEntry._ID));
                }
                if (returnUri == null) {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case DISH: {
                int nbrOfRow = 0;
                db.beginTransaction();
                long _id = db.insert(KitchenContract.DishEntry.TABLE_NAME, null, values);

                Log.v("dish insert FTS table", String.valueOf(_id));


                if (_id > 0)
                    returnUri = KitchenContract.DishEntry.buildDishUri(_id);
                else {
                    nbrOfRow = db.update(KitchenContract.DishEntry.TABLE_NAME, values, KitchenContract.DishEntry._ID + " = " + values.getAsString(KitchenContract.DishEntry._ID), null);
                }
                db.execSQL("INSERT INTO " +
                        KitchenContract.FtsDishEntry.TABLE_NAME + "( " + KitchenContract.FtsDishEntry.TABLE_NAME + ") VALUES('rebuild'); ");
                db.setTransactionSuccessful();
                db.endTransaction();
                if (nbrOfRow > 0) {
                    returnUri = KitchenContract.DishEntry.buildDishUri(values.getAsLong(KitchenContract.DishEntry._ID));
                }
                if (returnUri == null) {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case INGREDIENT: {
                int nbrOfRow = 0;
                long _id = db.insert(KitchenContract.IngredientEntry.TABLE_NAME, null, values);

                if (_id > 0)
                    returnUri = KitchenContract.IngredientEntry.buildIngredientUri(_id);
                else {
                    nbrOfRow = db.update(KitchenContract.IngredientEntry.TABLE_NAME, values, KitchenContract.IngredientEntry._ID + " = " + values.getAsString(KitchenContract.IngredientEntry._ID), null);
                }
                db.execSQL("INSERT INTO " +
                        KitchenContract.FtsIngredientEntry.TABLE_NAME + " (docid," +
                        KitchenContract.IngredientEntry.COLUMN_INGREDIENT_NAME + ", " +
                        KitchenContract.IngredientEntry.COLUMN_CATEGORY_A + ", " +
                        KitchenContract.IngredientEntry.COLUMN_CATEGORY_B + ", " +
                        KitchenContract.IngredientEntry.COLUMN_QUALITY_NICKNAME_LIST + ") SELECT " +
                        _id + " , " +
                        KitchenContract.IngredientEntry.COLUMN_INGREDIENT_NAME + " , " +
                        KitchenContract.IngredientEntry.COLUMN_CATEGORY_A + ", " +
                        KitchenContract.IngredientEntry.COLUMN_CATEGORY_B + ", " +
                        KitchenContract.IngredientEntry.COLUMN_QUALITY_NICKNAME_LIST + " FROM " +
                        KitchenContract.IngredientEntry.TABLE_NAME + " ;");
                Log.v("Ingredient insert FTS ", String.valueOf(_id));

                db.execSQL("INSERT INTO " +
                        KitchenContract.FtsIngredientEntry.TABLE_NAME + "( " + KitchenContract.FtsIngredientEntry.TABLE_NAME + ") VALUES('rebuild'); ");
                db.setTransactionSuccessful();
                db.endTransaction();
                if (nbrOfRow > 0) {
                    returnUri = KitchenContract.IngredientEntry.buildIngredientUri(values.getAsLong(KitchenContract.IngredientEntry._ID));
                }
                if (returnUri == null) {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case MENU_DISH: {
                int nbrOfRow = 0;
                long _id = db.insert(KitchenContract.MenuDishEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KitchenContract.MenuDishEntry.buildMenuDishUri(_id);
                else {
                    nbrOfRow = db.update(KitchenContract.MenuDishEntry.TABLE_NAME, values, KitchenContract.MenuDishEntry._ID + " = " + values.getAsString(KitchenContract.MenuDishEntry._ID), null);
                }
                if (nbrOfRow > 0) {
                    returnUri = KitchenContract.MenuDishEntry.buildMenuDishUri(values.getAsLong(KitchenContract.MenuDishEntry._ID));
                }
                if (returnUri == null) {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }
            case COURSE_CATEGORY: {
                int nbrOfRow = 0;
                long _id = db.insert(KitchenContract.CourseCategoryEntry.TABLE_NAME, null, values);
                if (_id > 0)
                    returnUri = KitchenContract.CourseCategoryEntry.buildCourseCategoryUri(_id);
                else {
                    nbrOfRow = db.update(KitchenContract.CourseCategoryEntry.TABLE_NAME, values, KitchenContract.CourseCategoryEntry._ID + " = " + values.getAsString(KitchenContract.CourseCategoryEntry._ID), null);
                }
                if (nbrOfRow > 0) {
                    returnUri = KitchenContract.CourseCategoryEntry.buildCourseCategoryUri(values.getAsLong(KitchenContract.CourseCategoryEntry._ID));
                }
                if (returnUri == null) {
                    throw new android.database.SQLException("Failed to insert row into " + uri);
                }
                break;
            }

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }

        getContext().getContentResolver().notifyChange(uri, null);
        return returnUri;
    }

    @Override
    public int delete(Uri uri, String selection, String[] selectionArgs) {
        KitchenDbHelper mOpenHelper;
        mOpenHelper = new KitchenDbHelper(getContext());
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();


        final int match = sUriMatcher.match(uri);
        int rowsDeleted;

        if (null == selection) selection = "1";

        switch (match) {

            case MENU:
                rowsDeleted = db.delete(KitchenContract.MenuEntry.TABLE_NAME, selection, selectionArgs);
                break;

            default:
                throw new UnsupportedOperationException("Unknown uri: " + uri);
        }
        if (rowsDeleted != 0) {
            getContext().getContentResolver().notifyChange(uri, null);
        }
        return rowsDeleted;
    }

    @Override
    public int update(Uri uri, ContentValues values, String selection, String[] selectionArgs) {
        KitchenDbHelper mOpenHelper;
        mOpenHelper = new KitchenDbHelper(getContext());
        return 0;
    }

    @Override
    public int bulkInsert(Uri uri, ContentValues[] values) {
        KitchenDbHelper mOpenHelper;
        mOpenHelper = new KitchenDbHelper(getContext());
        SQLiteDatabase db = mOpenHelper.getWritableDatabase();

        final int match = sUriMatcher.match(uri);
        int valueLenghts;
        int rowsInserted = 0;


        switch (match) {

            case INGREDIENT: {

                String sql = "INSERT INTO " +
                        KitchenContract.IngredientEntry.TABLE_NAME + " ( " +
                        KitchenContract.IngredientEntry.COLUMN_CATEGORY_A + ", " +
                        KitchenContract.IngredientEntry.COLUMN_CATEGORY_B + ", " +
                        KitchenContract.IngredientEntry.COLUMN_QUALITY_NICKNAME_LIST + ", " +
                        KitchenContract.IngredientEntry.COLUMN_DEF_MESURE_ID_LIST + ", " +
                        KitchenContract.IngredientEntry.COLUMN_INGREDIENT_NAME + ") VALUES  ( ?, ?, ?, ?, ?);";


                valueLenghts = values.length;


                SQLiteStatement stmt = db.compileStatement(sql);


                ArrayList<String> catA = new ArrayList<String>();
                ArrayList<String> catB = new ArrayList<String>();
                ArrayList<String> catC = new ArrayList<String>();
                ArrayList<String> mesure = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();

                for (ContentValues v : values) {

                    if (v != null) {
                        catA.add(v.getAsString(KitchenContract.IngredientEntry.COLUMN_CATEGORY_A));
                        catB.add(v.getAsString(KitchenContract.IngredientEntry.COLUMN_CATEGORY_B));
                        catC.add(v.getAsString(KitchenContract.IngredientEntry.COLUMN_QUALITY_NICKNAME_LIST));
                        mesure.add(v.getAsString(KitchenContract.IngredientEntry.COLUMN_DEF_MESURE_ID_LIST));
                        name.add(v.getAsString(KitchenContract.IngredientEntry.COLUMN_INGREDIENT_NAME));

                    }
                }
                db.beginTransaction();
                if (valueLenghts > 0) {
                    try {
                        for (int i = 0; i < values.length; i++) {

                            if (i >= 0) {
                                if (catA.get(i) != null) stmt.bindString(1, catA.get(i));
                                if (catB.get(i) != null) stmt.bindString(2, catB.get(i));
                                if (catC.get(i) != null) stmt.bindString(3, catC.get(i));
                                if (mesure.get(i) != null) stmt.bindString(4, mesure.get(i));
                                if (name.get(i) != null) stmt.bindString(5, name.get(i));
                            }

                            long _id = stmt.executeInsert();

                            stmt.clearBindings();

                            if (_id > 0) {
                                rowsInserted = rowsInserted + 1;

                            }
                        }
                        db.execSQL("INSERT INTO " +
                                KitchenContract.FtsIngredientEntry.TABLE_NAME + "( " + KitchenContract.FtsIngredientEntry.TABLE_NAME + ") VALUES('rebuild'); ");

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

                }

                break;
            }
            case MENU: {

                String sql = "INSERT INTO " +
                        KitchenContract.MenuEntry.TABLE_NAME + " ( " +
                        KitchenContract.MenuEntry._ID + ", " +
                        KitchenContract.MenuEntry.COLUMN_MENU_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_USER_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_DATE_CREATED + ", " +
                        KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH + ") VALUES  ( ?, ?, ?, ?, ?);";


                valueLenghts = values.length;


                SQLiteStatement stmt = db.compileStatement(sql);


                ArrayList<String> id = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> username = new ArrayList<String>();
                ArrayList<String> date = new ArrayList<String>();
                ArrayList<String> numberOfDish = new ArrayList<String>();

                for (ContentValues v : values) {

                    if (v != null) {
                        id.add(v.getAsString(KitchenContract.MenuEntry._ID));
                        name.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        username.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        date.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_DATE_CREATED));
                        numberOfDish.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH));

                    }
                }
                db.beginTransaction();
                if (valueLenghts > 0) {
                    try {
                        for (int i = 0; i < values.length; i++) {

                            if (i >= 0) {
                                if (id.get(i) != null) stmt.bindString(1, id.get(i));
                                if (name.get(i) != null) stmt.bindString(2, name.get(i));
                                if (username.get(i) != null) stmt.bindString(3, username.get(i));
                                if (date.get(i) != null) stmt.bindString(4, date.get(i));
                                if (numberOfDish.get(i) != null)
                                    stmt.bindString(5, numberOfDish.get(i));
                            }

                            long _id = stmt.executeInsert();

                            stmt.clearBindings();

                            if (_id > 0) {
                                rowsInserted = rowsInserted + 1;

                            }
                        }
                        db.execSQL("INSERT INTO " +
                                KitchenContract.FtsMenuEntry.TABLE_NAME + "( " + KitchenContract.FtsMenuEntry.TABLE_NAME + ") VALUES('rebuild'); ");

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

                }

                break;
            }
            case DISH: {

                String sql = "INSERT INTO " +
                        KitchenContract.DishEntry.TABLE_NAME + " ( " +
                        KitchenContract.DishEntry._ID + ", " +
                        KitchenContract.DishEntry.COLUMN_DISH_NAME + ", " +
                        KitchenContract.DishEntry.COLUMN_USER_NAME + ", " +
                        KitchenContract.DishEntry.COLUMN_DESCRIPTION + ") VALUES  ( ?, ?, ?, ?);";


                valueLenghts = values.length;


                SQLiteStatement stmt = db.compileStatement(sql);


                ArrayList<String> id = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> username = new ArrayList<String>();
                ArrayList<String> description = new ArrayList<String>();


                for (ContentValues v : values) {

                    if (v != null) {
                        id.add(v.getAsString(KitchenContract.DishEntry._ID));
                        name.add(v.getAsString(KitchenContract.DishEntry.COLUMN_DISH_NAME));
                        username.add(v.getAsString(KitchenContract.DishEntry.COLUMN_USER_NAME));
                        description.add(v.getAsString(KitchenContract.DishEntry.COLUMN_DESCRIPTION));

                    }
                }
                db.beginTransaction();
                if (valueLenghts > 0) {
                    try {
                        for (int i = 0; i < values.length; i++) {

                            if (i >= 0) {
                                stmt.bindString(1, id.get(i));
                                stmt.bindString(2, name.get(i));
                                stmt.bindString(3, username.get(i));
                                stmt.bindString(4, description.get(i));
                            }

                            long _id = stmt.executeInsert();

                            stmt.clearBindings();

                            if (_id > 0) {
                                rowsInserted = rowsInserted + 1;

                            }
                        }
                        db.execSQL("INSERT INTO " +
                                KitchenContract.FtsDishEntry.TABLE_NAME + "( " + KitchenContract.FtsDishEntry.TABLE_NAME + ") VALUES('rebuild'); ");

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

                }

                break;
            }
            case MENU_DISH: {

                String sql = "INSERT INTO " +
                        KitchenContract.MenuDishEntry.TABLE_NAME + " ( " +
                        KitchenContract.MenuDishEntry._ID + ", " +
                        KitchenContract.MenuDishEntry.COLUMN_DISH_ID + ", " +
                        KitchenContract.MenuDishEntry.COLUMN_DISH_POSITION + ", " +
                        KitchenContract.MenuDishEntry.COLUMN_MENU_ID + ", " +
                        KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID + ", " +
                        KitchenContract.MenuDishEntry.COLUMN_COURSE_POSITION + ") VALUES  ( ?, ?, ?, ?, ?, ?);";


                valueLenghts = values.length;


                SQLiteStatement stmt = db.compileStatement(sql);


                ArrayList<String> id = new ArrayList<String>();
                ArrayList<String> dishId = new ArrayList<String>();
                ArrayList<String> dishPosition = new ArrayList<String>();
                ArrayList<String> menuId = new ArrayList<String>();
                ArrayList<String> coursCategoryId = new ArrayList<String>();
                ArrayList<String> coursePosition = new ArrayList<String>();

                for (ContentValues v : values) {

                    if (v != null) {
                        id.add(v.getAsString(KitchenContract.MenuDishEntry._ID));
                        dishId.add(v.getAsString(KitchenContract.MenuDishEntry.COLUMN_DISH_ID));
                        dishPosition.add(v.getAsString(KitchenContract.MenuDishEntry.COLUMN_DISH_POSITION));
                        menuId.add(v.getAsString(KitchenContract.MenuDishEntry.COLUMN_MENU_ID));
                        coursCategoryId.add(v.getAsString(KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID));
                        coursePosition.add(v.getAsString(KitchenContract.MenuDishEntry.COLUMN_COURSE_POSITION));

                    }
                }
                db.beginTransaction();
                if (valueLenghts > 0) {
                    try {
                        for (int i = 0; i < values.length; i++) {

                            if (i >= 0) {
                                stmt.bindString(1, id.get(i));
                                stmt.bindString(2, dishId.get(i));
                                stmt.bindString(3, dishPosition.get(i));
                                stmt.bindString(4, menuId.get(i));
                                stmt.bindString(5, coursCategoryId.get(i));
                                stmt.bindString(6, coursePosition.get(i));
                            }

                            long _id = stmt.executeInsert();

                            stmt.clearBindings();

                            if (_id > 0) {
                                rowsInserted = rowsInserted + 1;

                            }
                        }

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

                }

                break;
            }
            //// TODO: 22/09/2017 edit bulk insert
            case MEP: {

                String sql = "INSERT INTO " +
                        KitchenContract.MenuEntry.TABLE_NAME + " ( " +
                        KitchenContract.MenuEntry._ID + ", " +
                        KitchenContract.MenuEntry.COLUMN_MENU_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_USER_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_DATE_CREATED + ", " +
                        KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH + ") VALUES  ( ?, ?, ?, ?, ?);";


                valueLenghts = values.length;


                SQLiteStatement stmt = db.compileStatement(sql);


                ArrayList<String> id = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> username = new ArrayList<String>();
                ArrayList<String> date = new ArrayList<String>();
                ArrayList<String> numberOfDish = new ArrayList<String>();

                for (ContentValues v : values) {

                    if (v != null) {
                        id.add(v.getAsString(KitchenContract.MenuEntry._ID));
                        name.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        username.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        date.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_DATE_CREATED));
                        numberOfDish.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH));

                    }
                }
                db.beginTransaction();
                if (valueLenghts > 0) {
                    try {
                        for (int i = 0; i < values.length - 1; i++) {

                            if (i >= 0) {
                                stmt.bindString(1, id.get(i));
                                stmt.bindString(2, name.get(i));
                                stmt.bindString(3, username.get(i));
                                stmt.bindString(4, date.get(i));
                                stmt.bindString(5, name.get(i));
                            }

                            long _id = stmt.executeInsert();

                            stmt.clearBindings();

                            if (_id > 0) {
                                rowsInserted = rowsInserted + 1;

                            }
                        }
                        db.execSQL("INSERT INTO " +
                                KitchenContract.FtsMenuEntry.TABLE_NAME + "( " + KitchenContract.FtsMenuEntry.TABLE_NAME + ") VALUES('rebuild'); ");

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

                }

                break;
            }
            case MEP_INGREDIENT: {

                String sql = "INSERT INTO " +
                        KitchenContract.MenuEntry.TABLE_NAME + " ( " +
                        KitchenContract.MenuEntry._ID + ", " +
                        KitchenContract.MenuEntry.COLUMN_MENU_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_USER_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_DATE_CREATED + ", " +
                        KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH + ") VALUES  ( ?, ?, ?, ?, ?);";


                valueLenghts = values.length;


                SQLiteStatement stmt = db.compileStatement(sql);


                ArrayList<String> id = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> username = new ArrayList<String>();
                ArrayList<String> date = new ArrayList<String>();
                ArrayList<String> numberOfDish = new ArrayList<String>();

                for (ContentValues v : values) {

                    if (v != null) {
                        id.add(v.getAsString(KitchenContract.MenuEntry._ID));
                        name.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        username.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        date.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_DATE_CREATED));
                        numberOfDish.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH));

                    }
                }
                db.beginTransaction();
                if (valueLenghts > 0) {
                    try {
                        for (int i = 0; i < values.length - 1; i++) {

                            if (i >= 0) {
                                stmt.bindString(1, id.get(i));
                                stmt.bindString(2, name.get(i));
                                stmt.bindString(3, username.get(i));
                                stmt.bindString(4, date.get(i));
                                stmt.bindString(5, name.get(i));
                            }

                            long _id = stmt.executeInsert();

                            stmt.clearBindings();

                            if (_id > 0) {
                                rowsInserted = rowsInserted + 1;

                            }
                        }
                        db.execSQL("INSERT INTO " +
                                KitchenContract.FtsMenuEntry.TABLE_NAME + "( " + KitchenContract.FtsMenuEntry.TABLE_NAME + ") VALUES('rebuild'); ");

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

                }

                break;
            }
            case COURSE_CATEGORY: {

                String sql = "INSERT INTO " +
                        KitchenContract.CourseCategoryEntry.TABLE_NAME + " ( " +
                        KitchenContract.CourseCategoryEntry._ID + ", " +
                        KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME +
                        ") VALUES  ( ?, ?);";


                valueLenghts = values.length;


                SQLiteStatement stmt = db.compileStatement(sql);


                ArrayList<String> id = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();


                for (ContentValues v : values) {

                    if (v != null) {
                        id.add(v.getAsString(KitchenContract.CourseCategoryEntry._ID));
                        name.add(v.getAsString(KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME));

                    }
                }
                db.beginTransaction();
                if (valueLenghts > 0) {
                    try {
                        for (int i = 0; i < values.length; i++) {

                            if (i >= 0) {
                                if (id.get(i) != null) stmt.bindString(1, id.get(i));
                                if (name.get(i) != null) stmt.bindString(2, name.get(i));

                            }

                            long _id = stmt.executeInsert();

                            stmt.clearBindings();

                            if (_id > 0) {
                                rowsInserted = rowsInserted + 1;

                            }
                        }

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

                }

                break;
            }
            //// TODO: 22/09/2017 edit bulk insert
            case DISH_MEP_INGREDIENTS: {

                String sql = "INSERT INTO " +
                        KitchenContract.MenuEntry.TABLE_NAME + " ( " +
                        KitchenContract.MenuEntry._ID + ", " +
                        KitchenContract.MenuEntry.COLUMN_MENU_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_USER_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_DATE_CREATED + ", " +
                        KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH + ") VALUES  ( ?, ?, ?, ?, ?);";


                valueLenghts = values.length;


                SQLiteStatement stmt = db.compileStatement(sql);


                ArrayList<String> id = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> username = new ArrayList<String>();
                ArrayList<String> date = new ArrayList<String>();
                ArrayList<String> numberOfDish = new ArrayList<String>();

                for (ContentValues v : values) {

                    if (v != null) {
                        id.add(v.getAsString(KitchenContract.MenuEntry._ID));
                        name.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        username.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        date.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_DATE_CREATED));
                        numberOfDish.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH));

                    }
                }
                db.beginTransaction();
                if (valueLenghts > 0) {
                    try {
                        for (int i = 0; i < values.length; i++) {

                            if (i >= 0) {
                                stmt.bindString(1, id.get(i));
                                stmt.bindString(2, name.get(i));
                                stmt.bindString(3, username.get(i));
                                stmt.bindString(4, date.get(i));
                                stmt.bindString(5, name.get(i));
                            }

                            long _id = stmt.executeInsert();

                            stmt.clearBindings();

                            if (_id > 0) {
                                rowsInserted = rowsInserted + 1;

                            }
                        }
                        db.execSQL("INSERT INTO " +
                                KitchenContract.FtsMenuEntry.TABLE_NAME + "( " + KitchenContract.FtsMenuEntry.TABLE_NAME + ") VALUES('rebuild'); ");

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

                }

                break;
            }
            //// TODO: 22/09/2017 edit bulk insert
            case DISH_CATEGORY: {

                String sql = "INSERT INTO " +
                        KitchenContract.MenuEntry.TABLE_NAME + " ( " +
                        KitchenContract.MenuEntry._ID + ", " +
                        KitchenContract.MenuEntry.COLUMN_MENU_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_USER_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_DATE_CREATED + ", " +
                        KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH + ") VALUES  ( ?, ?, ?, ?, ?);";


                valueLenghts = values.length;


                SQLiteStatement stmt = db.compileStatement(sql);


                ArrayList<String> id = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> username = new ArrayList<String>();
                ArrayList<String> date = new ArrayList<String>();
                ArrayList<String> numberOfDish = new ArrayList<String>();

                for (ContentValues v : values) {

                    if (v != null) {
                        id.add(v.getAsString(KitchenContract.MenuEntry._ID));
                        name.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        username.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        date.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_DATE_CREATED));
                        numberOfDish.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH));

                    }
                }
                db.beginTransaction();
                if (valueLenghts > 0) {
                    try {
                        for (int i = 0; i < values.length - 1; i++) {

                            if (i >= 0) {
                                stmt.bindString(1, id.get(i));
                                stmt.bindString(2, name.get(i));
                                stmt.bindString(3, username.get(i));
                                stmt.bindString(4, date.get(i));
                                stmt.bindString(5, name.get(i));
                            }

                            long _id = stmt.executeInsert();

                            stmt.clearBindings();

                            if (_id > 0) {
                                rowsInserted = rowsInserted + 1;

                            }
                        }
                        db.execSQL("INSERT INTO " +
                                KitchenContract.FtsMenuEntry.TABLE_NAME + "( " + KitchenContract.FtsMenuEntry.TABLE_NAME + ") VALUES('rebuild'); ");

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

                }

                break;
            }
            //// TODO: 22/09/2017 edit bulk insert
            case INGREDIENT_CATEGORY: {

                String sql = "INSERT INTO " +
                        KitchenContract.MenuEntry.TABLE_NAME + " ( " +
                        KitchenContract.MenuEntry._ID + ", " +
                        KitchenContract.MenuEntry.COLUMN_MENU_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_USER_NAME + ", " +
                        KitchenContract.MenuEntry.COLUMN_DATE_CREATED + ", " +
                        KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH + ") VALUES  ( ?, ?, ?, ?, ?);";


                valueLenghts = values.length;


                SQLiteStatement stmt = db.compileStatement(sql);


                ArrayList<String> id = new ArrayList<String>();
                ArrayList<String> name = new ArrayList<String>();
                ArrayList<String> username = new ArrayList<String>();
                ArrayList<String> date = new ArrayList<String>();
                ArrayList<String> numberOfDish = new ArrayList<String>();

                for (ContentValues v : values) {

                    if (v != null) {
                        id.add(v.getAsString(KitchenContract.MenuEntry._ID));
                        name.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        username.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_MENU_NAME));
                        date.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_DATE_CREATED));
                        numberOfDish.add(v.getAsString(KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH));

                    }
                }
                db.beginTransaction();
                if (valueLenghts > 0) {
                    try {
                        for (int i = 0; i < values.length - 1; i++) {

                            if (i >= 0) {
                                stmt.bindString(1, id.get(i));
                                stmt.bindString(2, name.get(i));
                                stmt.bindString(3, username.get(i));
                                stmt.bindString(4, date.get(i));
                                stmt.bindString(5, name.get(i));
                            }

                            long _id = stmt.executeInsert();

                            stmt.clearBindings();

                            if (_id > 0) {
                                rowsInserted = rowsInserted + 1;

                            }
                        }
                        db.execSQL("INSERT INTO " +
                                KitchenContract.FtsMenuEntry.TABLE_NAME + "( " + KitchenContract.FtsMenuEntry.TABLE_NAME + ") VALUES('rebuild'); ");

                        db.setTransactionSuccessful();
                    } finally {
                        db.endTransaction();
                    }

                }

                break;
            }
        }


        return rowsInserted;
    }


    // TODO: 28/10/2016  create each case
    @Override
    public void shutdown() {
        super.shutdown();
    }
}

