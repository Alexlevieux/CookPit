package com.example.android.cookpit.data;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;
import android.util.Log;

import java.util.HashSet;

/**
 * Created by alexandrelevieux on 15/10/2016.
 */

public class TestDb extends AndroidTestCase {

    public static final String LOG_TAG = TestDb.class.getSimpleName();

    void deleteTheDatabase() {
        mContext.deleteDatabase(KitchenDbHelper.DATABASE_NAME);
    }

    public void setUp() {
        deleteTheDatabase();
    }


    public void testCreateDb() throws Throwable {
        // build a HashSet of all of the table names we wish to look for
        // Note that there will be another table in the DB that stores the
        // Android metadata (db version information)
        final HashSet<String> tableNameHashSet = new HashSet<String>();
        tableNameHashSet.add(KitchenContract.MenuEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.DishEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.DishCategoryEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.IngredientEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.IngredientCategoryEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.IngredientSubCategoryEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.IngredientDetailCategoryEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.CourseCategoryEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.MesureEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.MepEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.DishMepIngredientEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.MepIngredientEntry.TABLE_NAME);
        tableNameHashSet.add(KitchenContract.MenuDishEntry.TABLE_NAME);


        mContext.deleteDatabase(KitchenDbHelper.DATABASE_NAME);
        SQLiteDatabase db = new KitchenDbHelper(
                this.mContext).getWritableDatabase();
        assertEquals(true, db.isOpen());

        // have we created the tables we want?
        Cursor c = db.rawQuery("SELECT name FROM sqlite_master WHERE type='table'", null);

        assertTrue("Error: This means that the database has not been created correctly",
                c.moveToFirst());

        // verify that the tables have been created
        do {
            tableNameHashSet.remove(c.getString(0));
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain both the location entry
        // and weather entry tables
        assertTrue("Error: Your database was created without both the location entry and weather entry tables",
                tableNameHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + KitchenContract.MenuDishEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> TestAColumnHashSet = new HashSet<String>();

        TestAColumnHashSet.add(KitchenContract.MenuDishEntry._ID);
        TestAColumnHashSet.add(KitchenContract.MenuDishEntry.COLUMN_MENU_ID);
        TestAColumnHashSet.add(KitchenContract.MenuDishEntry.COLUMN_DISH_ID);
        TestAColumnHashSet.add(KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID);


        int columnNameIndex = c.getColumnIndex("name");

        Log.v(TestAColumnHashSet.toString(), "numberofcolumn");
        do {
            String columnName = c.getString(columnNameIndex);
            Log.v(columnName, "test");
            TestAColumnHashSet.remove(columnName);
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                TestAColumnHashSet.isEmpty());


        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + KitchenContract.DishEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> TestBColumnHashSet = new HashSet<String>();

        TestBColumnHashSet.add(KitchenContract.DishEntry._ID);
        TestBColumnHashSet.add(KitchenContract.DishEntry.COLUMN_DRAWABLE_ID);

        int columnMenuNameIndex = c.getColumnIndex("name");

        do {
            String columnMenuName = c.getString(columnMenuNameIndex);

            TestBColumnHashSet.remove(columnMenuName);
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                TestBColumnHashSet.isEmpty());

        // now, do our tables contain the correct columns?
        c = db.rawQuery("PRAGMA table_info(" + KitchenContract.MepEntry.TABLE_NAME + ")",
                null);

        assertTrue("Error: This means that we were unable to query the database for table information.",
                c.moveToFirst());

        // Build a HashSet of all of the column names we want to look for
        final HashSet<String> TestCColumnHashSet = new HashSet<String>();

        TestCColumnHashSet.add(KitchenContract.MepEntry._ID);
        TestCColumnHashSet.add(KitchenContract.MepEntry.COLUMN_MEP_NAME);
        TestCColumnHashSet.add(KitchenContract.MepEntry.COLUMN_MEP_USERNAME);
        TestCColumnHashSet.add(KitchenContract.MepEntry.COLUMN_DESCRIPTION);
        TestCColumnHashSet.add(KitchenContract.MepEntry.COLUMN_DRAWABLE_ID);
        TestCColumnHashSet.add(KitchenContract.MepEntry.COLUMN_YEILD);
        TestCColumnHashSet.add(KitchenContract.MepEntry.COLUMN_MESURE);
        TestCColumnHashSet.add(KitchenContract.MepEntry.COLUMN_TRIM_INGREDIENT_ID);


        int DishcolumnNameIndex = c.getColumnIndex("name");

        Log.v(TestCColumnHashSet.toString(), "numberofcolumn");
        do {
            String DishCategorycolumnName = c.getString(DishcolumnNameIndex);

            TestCColumnHashSet.remove(DishCategorycolumnName);
        } while (c.moveToNext());

        // if this fails, it means that your database doesn't contain all of the required location
        // entry columns
        assertTrue("Error: The database doesn't contain all of the required location entry columns",
                TestCColumnHashSet.isEmpty());


        db.close();
    }


}
