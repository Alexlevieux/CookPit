package com.example.android.cookpit.Model.data;

import android.content.ComponentName;
import android.content.ContentUris;
import android.content.ContentValues;
import android.content.pm.PackageManager;
import android.content.pm.ProviderInfo;
import android.database.Cursor;
import android.net.Uri;
import android.test.AndroidTestCase;
import android.util.Log;

import com.example.android.cookpit.Model.Utility;

import java.io.IOException;

/**
 * Created by alexandrelevieux on 27/10/2016.
 */

public class TestProvider extends AndroidTestCase {

    public static final String LOG_TAG = TestProvider.class.getSimpleName();


    @Override
    protected void setUp() throws Exception {
        super.setUp();

    }


    //todo insert a csv file of Ingredient.
    public void testInsertMenuProvider() throws IOException {
        String NewmenuName = "puremagic";
        String username = "Francois";

        ContentValues[] testCSV = Utility.getContentValueOfCsvFile(NewmenuName, getContext());
        TestUtilities.TestContentObserver tco = TestUtilities.getTestContentObserver();
        mContext.getContentResolver().registerContentObserver(KitchenContract.CourseCategoryEntry.CONTENT_URI, true, tco);
        Log.v(LOG_TAG, String.valueOf(testCSV.length));


        ContentValues newMenu = new ContentValues();
        newMenu.put(KitchenContract.MenuEntry.COLUMN_MENU_NAME, NewmenuName);
        newMenu.put(KitchenContract.MenuEntry.COLUMN_USER_NAME, username);
        Uri menuUri = mContext.getContentResolver().insert(KitchenContract.MenuEntry.CONTENT_URI, newMenu);
        long menuId = ContentUris.parseId(menuUri);

        for (int i = 0; i < testCSV.length - 1; i++) {
            ContentValues values = testCSV[i];
            String courseCategory = values.getAsString(KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME);

            if (Utility.doesCourseCategoryExist(courseCategory, getContext())) {

                ContentValues newCategory = new ContentValues();
                newCategory.put(KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME, courseCategory);
                Uri courseCategoryUri = mContext.getContentResolver().insert(KitchenContract.CourseCategoryEntry.CONTENT_URI, newCategory);
                Log.v(LOG_TAG, "new category : " + courseCategory);

            }


            String dishName = values.getAsString(KitchenContract.DishEntry.COLUMN_DISH_NAME);


            ContentValues dishValue = new ContentValues();
            dishValue.put(KitchenContract.DishEntry.COLUMN_DISH_NAME, dishName);
            dishValue.put(KitchenContract.DishEntry.COLUMN_DESCRIPTION, values.getAsString(KitchenContract.DishEntry.COLUMN_DESCRIPTION));
            dishValue.put(KitchenContract.DishEntry.COLUMN_USER_NAME, "KitchenDriveAlex");
            Uri DishUri = mContext.getContentResolver().insert(KitchenContract.DishEntry.CONTENT_URI, dishValue);
            long DishRowId = ContentUris.parseId(DishUri);


            ContentValues menuDishValue = new ContentValues();
            Long CoursecategoryId = Utility.getcourseCategoryId(courseCategory, getContext());

            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_MENU_ID, menuId);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID, CoursecategoryId);
            menuDishValue.put(KitchenContract.MenuDishEntry.COLUMN_DISH_ID, DishRowId);

            Uri MenuDishUri = mContext.getContentResolver().insert(KitchenContract.MenuDishEntry.CONTENT_URI, menuDishValue);


            assertTrue(DishRowId != -1);
            Log.v("new Dish entered : " + dishName, String.valueOf(DishRowId));
            Log.v("Attached to : " + NewmenuName + " id : " + menuId, "and category : " + courseCategory + " id : " + CoursecategoryId);

        }
        // Register a content observer for our insert.  This time, directly with the content resolver


        tco.waitForNotificationOrFail();
        mContext.getContentResolver().unregisterContentObserver(tco);


    }

    public void testProviderRegistry() {
        PackageManager pm = mContext.getPackageManager();

        // We define the component name based on the package name from the context and the
        // WeatherProvider class.
        ComponentName componentName = new ComponentName(mContext.getPackageName(),
                cookpitProvider.class.getName());
        try {
            // Fetch the provider info using the component name from the PackageManager
            // This throws an exception if the provider isn't registered.
            ProviderInfo providerInfo = pm.getProviderInfo(componentName, 0);

            // Make sure that the registered authority matches the authority from the Contract.
            assertEquals("Error: cookpitProvider registered with authority: " + providerInfo.authority +
                            " instead of authority: " + KitchenContract.CONTENT_AUTHORITY,
                    providerInfo.authority, KitchenContract.CONTENT_AUTHORITY);
        } catch (PackageManager.NameNotFoundException e) {
            // I guess the provider isn't registered correctly.
            assertTrue("Error: cookpitProvider not registered at " + mContext.getPackageName(),
                    false);
        }
    }

    public void testIngredientandMepwithDishIdquery() throws IOException {


        Utility.testInsertIngredients(getContext());
        Utility.testinsertDMI(getContext());
        Utility.testinsertMEP(getContext());
        Utility.testinsertMI(getContext());


        Uri uri = ContentUris.withAppendedId(KitchenContract.DishMepIngredientEntry.CONTENT_URI, Long.parseLong("2"));

        String[] projection = {
                KitchenContract.DishMepIngredientEntry.TABLE_NAME + "." + KitchenContract.DishMepIngredientEntry.COLUMN_DISH_ID,
                KitchenContract.MepEntry.TABLE_NAME + "." + KitchenContract.MepEntry._ID,
                KitchenContract.MepEntry.COLUMN_MEP_NAME,
                KitchenContract.IngredientEntry.TABLE_NAME + "." + KitchenContract.IngredientEntry._ID,
                KitchenContract.IngredientEntry.COLUMN_INGREDIENT_NAME,
                KitchenContract.DishMepIngredientEntry.COLUMN_QUANTITY

        };

        String selection = KitchenContract.DishMepIngredientEntry.TABLE_NAME + "." + KitchenContract.DishMepIngredientEntry.COLUMN_DISH_ID + " = ? ";

        String[] args = {"2"};
        String sortOrder = KitchenContract.MepEntry.TABLE_NAME + "." + KitchenContract.MepEntry._ID + " ASC";

        Cursor c = getContext().getContentResolver().query(uri,
                projection,
                selection,
                args,
                sortOrder
        );
        Log.v(c.toString(), "line : ");
        c.moveToFirst();
        for (int i = 0; i <= c.getCount(); i++, c.moveToNext()) {
            Log.v(c.toString(), "line : " + String.valueOf(i));
        }
    }


}
