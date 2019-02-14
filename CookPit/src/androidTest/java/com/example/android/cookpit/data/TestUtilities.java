package com.example.android.cookpit.data;

import android.content.ContentUris;
import android.content.ContentValues;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.HandlerThread;

import com.example.android.cookpit.data.utils.PollingCheck;

import java.util.Map;
import java.util.Set;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;


/**
 * Created by alexandrelevieux on 18/10/2016.
 */

public class TestUtilities {


    static void validateCursor(String error, Cursor valueCursor, ContentValues expectedValues) {
        assertTrue("Empty cursor returned. " + error, valueCursor.moveToFirst());
        validateCurrentRecord(error, valueCursor, expectedValues);
        valueCursor.close();
    }

    static void validateCurrentRecord(String error, Cursor valueCursor, ContentValues expectedValues) {
        Set<Map.Entry<String, Object>> valueSet = expectedValues.valueSet();
        for (Map.Entry<String, Object> entry : valueSet) {
            String columnName = entry.getKey();
            int idx = valueCursor.getColumnIndex(columnName);
            assertFalse("Column '" + columnName + "' not found. " + error, idx == -1);
            String expectedValue = entry.getValue().toString();
            assertEquals("Value '" + entry.getValue().toString() +
                    "' did not match the expected value '" +
                    expectedValue + "'. " + error, expectedValue, valueCursor.getString(idx));
        }
    }

    static class TestContentObserver extends ContentObserver {
        final HandlerThread mHT;
        boolean mContentChanged;

        static TestContentObserver getTestContentObserver() {
            HandlerThread ht = new HandlerThread("ContentObserverThread");
            ht.start();
            return new TestContentObserver(ht);
        }

        private TestContentObserver(HandlerThread ht) {
            super(new Handler(ht.getLooper()));
            mHT = ht;
        }

        // On earlier versions of Android, this onChange method is called
        @Override
        public void onChange(boolean selfChange) {
            onChange(selfChange, null);
        }

        @Override
        public void onChange(boolean selfChange, Uri uri) {
            mContentChanged = true;
        }

        public void waitForNotificationOrFail() {
            // Note: The PollingCheck class is taken from the Android CTS (Compatibility Test Suite).
            // It's useful to look at the Android CTS source for ideas on how to test your Android
            // applications.  The reason that PollingCheck works is that, by default, the JUnit
            // testing framework is not running on the main Android application thread.
            new PollingCheck(5000) {
                @Override
                protected boolean check() {
                    return mContentChanged;
                }
            }.run();
            mHT.quit();
        }
    }

    static TestContentObserver getTestContentObserver() {
        return TestContentObserver.getTestContentObserver();
    }

    static ContentValues createMenuValues() {
        ContentValues MenuValues = new ContentValues();
        MenuValues.put(KitchenContract.MenuEntry.COLUMN_MENU_NAME, "AlexMenu");
        MenuValues.put(KitchenContract.MenuEntry.COLUMN_USER_NAME, "Alex");
        MenuValues.put(KitchenContract.MenuEntry.COLUMN_DATE_CREATED, 1419033600L);


        return MenuValues;
    }

    static ContentValues createRibEyeDishValues() {
        ContentValues MenuValues = new ContentValues();
        MenuValues.put(KitchenContract.DishEntry.COLUMN_DISH_NAME, "Rib eye");
        MenuValues.put(KitchenContract.DishEntry.COLUMN_USER_NAME, "Alex");
        MenuValues.put(KitchenContract.DishEntry.COLUMN_DESCRIPTION, "Rib eye 10 OZ, mixed leaf, french fries, pepper sauce or red wine jus.");

        return MenuValues;
    }

    static ContentValues createBurgerDishValues() {
        ContentValues MenuValues = new ContentValues();
        MenuValues.put(KitchenContract.DishEntry.COLUMN_DISH_NAME, "Burger");
        MenuValues.put(KitchenContract.DishEntry.COLUMN_USER_NAME, "Alex");
        MenuValues.put(KitchenContract.DishEntry.COLUMN_DESCRIPTION, "organic beef burger 8 OZ, lettuce,tomatoe, red oinion pickle cucumber, basil aioli, with coleslaw and french fries");

        return MenuValues;
    }

    static ContentValues createMenuDishValues(Uri MenuUri, Uri UriA) {
        ContentValues MenuDishValues = new ContentValues();
        MenuDishValues.put(KitchenContract.MenuDishEntry.COLUMN_MENU_ID, ContentUris.parseId(MenuUri));
        MenuDishValues.put(KitchenContract.MenuDishEntry.COLUMN_DISH_ID, ContentUris.parseId(UriA));
        return MenuDishValues;
    }

    // todo Dish content value,
    // todo Ingredient Content value
    // todo mep Content value
    // todo Mesure Content value
    // todo Course Category Content value
    // todo Dish category Content value
    // todo MEnu dish Content value
    // todo Dish mep Ingredient Content value

}
