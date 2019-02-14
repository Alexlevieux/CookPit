package com.example.android.cookpit.data;

import android.content.ContentResolver;
import android.net.Uri;
import android.provider.BaseColumns;

import static android.content.ContentUris.withAppendedId;

/**
 * Created by alexandrelevieux on 26/09/2016.
 */

public class KitchenContract {
    // relationship between a domain name and its website.  A convenient string to use for the
    // content authority is the package name for the app, which is guaranteed to be unique on the
    // device.
    public static final String CONTENT_AUTHORITY = "com.example.android.cookpit";

    // Use CONTENT_AUTHORITY to create the base of all URI's which apps will use to contact
    // the content provider.
    public static final Uri BASE_CONTENT_URI = Uri.parse("content://" + CONTENT_AUTHORITY);

    // Possible paths (appended to base content URI for possible URI's)
    // For instance, content://com.example.android.sunshine.app/weather/ is a valid path for
    // looking at weather data. content://com.example.android.sunshine.app/givemeroot/ will fail,
    // as the ContentProvider hasn't been given any information on what to do with "givemeroot".
    // At least, let's hope not.  Don't be that dev, reader.  Don't be that dev.
    public static final String PATH_MENU = "menu";
    public static final String PATH_DISH = "dish";
    public static final String PATH_MEP = "mep";
    public static final String PATH_INGREDIENT = "ingredient";
    public static final String PATH_COURSE_CATEGORY = "coursecategory";
    public static final String PATH_DISH_CATEGORY = "dishcategory";
    public static final String PATH_INGREDIENT_CATEGORY = "ingredientcategory";
    public static final String PATH_INGREDIENT_SUBCATEGORY = "ingredientsubcategory";
    public static final String PATH_INGREDIENT_DETAIL_CATEGORY = "ingredientdetailcategory";
    public static final String PATH_DISH_MEP_INGREDIENT = "dishmepingredient";
    public static final String PATH_MENU_DISH = "menudish";
    public static final String PATH_MEP_INGREDIENT = "mep_ingredient";
    public static final String PATH_MESURE = "mesure";
    public static final String PATH_DRAWABLE = "drawable";
    public static final String PATH_MEP_CATEGORY = "mep_category";
    public static final String PATH_MEP_SUBCATEGORY = "mep_subcategory";
    public static final String PATH_FTS_MENU = "fts_menu";
    public static final String PATH_FTS_DISH = "fts_dish";
    public static final String PATH_FTS_MEP = "fts_mep";
    public static final String PATH_FTS_INGREDIENT = "fts_ingredient";

    public static final class FtsMenuEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FTS_MENU).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FTS_MENU;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FTS_MENU;

        public static final String TABLE_NAME = "fts_menu";

        public static Uri buildFtsMenuUri(String query) {
            return Uri.withAppendedPath(CONTENT_URI, query);
        }


    }

    public static final class FtsDishEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FTS_DISH).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FTS_DISH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FTS_DISH;

        public static final String TABLE_NAME = "fts_dish";

        public static Uri buildFtsDishUri(String query) {
            return Uri.withAppendedPath(CONTENT_URI, query);
        }


    }

    public static final class FtsMepEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FTS_MEP).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FTS_MEP;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FTS_MEP;

        public static final String TABLE_NAME = "fts_mep";

        public static Uri buildFtsMepUri(String query) {
            return Uri.withAppendedPath(CONTENT_URI, query);
        }


    }

    public static final class FtsIngredientEntry implements BaseColumns {
        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_FTS_INGREDIENT).build();
        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FTS_INGREDIENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_FTS_INGREDIENT;

        public static final String TABLE_NAME = "fts_ingredient";

        public static Uri buildFtsIngredientUri(String query) {
            return Uri.withAppendedPath(CONTENT_URI, query);
        }


    }


    public static final class MenuEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MENU).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MENU;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MENU;


        public static final String TABLE_NAME = "menu";
        public static final String COLUMN_MENU_NAME = "menu_name";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_DATE_CREATED = "date_created";
        public static final String COLUMN_NUMBER_OF_DISH = "number_of_dish";

        public static Uri buildMenuUri(long id) {
            return withAppendedId(CONTENT_URI, id);
        }


    }

    public static final class DishEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DISH).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DISH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DISH;


        public static final String TABLE_NAME = "dish";
        public static final String COLUMN_DISH_NAME = "dish_name";
        public static final String COLUMN_USER_NAME = "user_name";
        public static final String COLUMN_DISH_CATEGORY_ID = "dish_category_id";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_COST_PRICE = "cost_price";
        public static final String COLUMN_SELLING_PRICE = "selling_price";
        public static final String COLUMN_NUMBER_OF_MEP = "number_of_mep";
        public static final String COLUMN_NUMBER_OF_INGREDIENTS = "number_of_ingredients";
        public static final String COLUMN_DRAWABLE_ID = "drawable_id";

        public static Uri buildDishUri(long id) {
            return withAppendedId(CONTENT_URI, id);
        }

        public static Uri buildDishUriWithCourseCategoryIdandMenuId(Long idMenu, long idCategory) {

            return CONTENT_URI.buildUpon().appendPath(Long.toString(idMenu)).appendQueryParameter(TABLE_NAME + "." + DishEntry._ID, Long.toString(idCategory)).build();

        }

    }

    public static final class IngredientEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT;


        public static final String TABLE_NAME = "ingredient";
        public static final String COLUMN_INGREDIENT_NAME = "ingredient_name";
        public static final String COLUMN_DEF_MESURE_ID_LIST = "def_mesure_id_list";
        public static final String COLUMN_DEF_COST_PRICE_LIST = "def_cost_price_list";
        public static final String COLUMN_DEF_SUPPLIER_LIST = "def_supplier_list";
        public static final String COLUMN_CATEGORY_A = "category_a";
        public static final String COLUMN_CATEGORY_B = "category_b";
        public static final String COLUMN_QUALITY_NICKNAME_LIST = "nickname_list";

        public static Uri buildIngredientUri(long id) {
            return withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class MepEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEP).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEP;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEP;


        public static final String TABLE_NAME = "mep";
        public static final String COLUMN_MEP_NAME = "mep_name";
        public static final String COLUMN_MEP_USERNAME = "mep_username";
        public static final String COLUMN_DESCRIPTION = "description";
        public static final String COLUMN_MEP_CATEGORY_A = "category_a";
        public static final String COLUMN_MEP_CATEGORY_B = "category_b";
        public static final String COLUMN_YEILD = "yeild";
        public static final String COLUMN_MESURE = "mesure";
        public static final String COLUMN_TRIM_INGREDIENT_ID = "trim_ingredient_id";
        public static final String COLUMN_DRAWABLE_ID = "drawable_id";

        public static Uri buildMepUri(long id) {
            return withAppendedId(CONTENT_URI, id);
        }


    }

    public static final class DishMepIngredientEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DISH_MEP_INGREDIENT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DISH_MEP_INGREDIENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DISH_MEP_INGREDIENT;


        public static final String TABLE_NAME = "dish_mep_ingredient";
        public static final String COLUMN_DISH_ID = "dish_id";
        public static final String COLUMN_MEP_ID = "mep_id";
        public static final String COLUMN_INGREDIENT_ID = "ingredient_id";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MESURE = "mesure_id";
        public static final String COLUMN_POSITION = "position";

        public static Uri buildDmiUri(long id) {
            return withAppendedId(CONTENT_URI, id);
        }


    }

    public static final class MepIngredientEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEP_INGREDIENT).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEP_INGREDIENT;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEP_INGREDIENT;


        public static final String TABLE_NAME = "mep_ingredient";
        public static final String COLUMN_MEP_ID = "mep_id";
        public static final String COLUMN_INGREDIENT_ID = "ingredient_id";
        public static final String COLUMN_SUB_MEP_ID = "sub_mep_id";
        public static final String COLUMN_QUANTITY = "quantity";
        public static final String COLUMN_MESURE = "mesure";
        public static final String COLUMN_POSITION = "position";

        public static Uri buildMiUri(long id) {
            return withAppendedId(CONTENT_URI, id);
        }


    }

    public static final class MenuDishEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MENU_DISH).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MENU_DISH;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MENU_DISH;


        public static final String TABLE_NAME = "menu_dish";
        public static final String COLUMN_MENU_ID = "menu_id";
        public static final String COLUMN_DISH_ID = "dish_id";
        public static final String COLUMN_COURSE_CATEGORY_ID = "course_category_id";
        public static final String COLUMN_COURSE_POSITION = "course_position";
        public static final String COLUMN_DISH_POSITION = "dish_position";


        public static Uri buildMenuDishUri(long id) {
            return withAppendedId(CONTENT_URI, id);

        }
    }

    public static final class DishCategoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DISH_CATEGORY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DISH_CATEGORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DISH_CATEGORY;


        public static final String TABLE_NAME = "dish_category";
        public static final String COLUMN_CATEGORY_NAME = "category_name";
        public static final String COLUMN_POSITION = "position";

        public static Uri buildDcUri(long id) {
            return withAppendedId(CONTENT_URI, id);
        }

    }

    public static final class CourseCategoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_COURSE_CATEGORY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_CATEGORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_COURSE_CATEGORY;


        public static final String TABLE_NAME = "course_category";
        public static final String COLUMN_COURSE_NAME = "course_name";

        public static Uri buildCourseCategoryUri(long id) {
            return withAppendedId(CONTENT_URI, id);
        }
    }

    public static final class IngredientCategoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT_CATEGORY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT_CATEGORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT_CATEGORY;


        public static final String TABLE_NAME = "ingredient_category";
        public static final String COLUMN_CATEGORY_NAME = "category_name";

    }

    public static final class IngredientSubCategoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT_SUBCATEGORY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT_SUBCATEGORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT_SUBCATEGORY;


        public static final String TABLE_NAME = "ingredient_subcategory";
        public static final String COLUMN_SUB_CATEGORY_NAME = "sub_category_name";

    }

    public static final class IngredientDetailCategoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_INGREDIENT_DETAIL_CATEGORY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT_DETAIL_CATEGORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_INGREDIENT_DETAIL_CATEGORY;


        public static final String TABLE_NAME = "ingredient_detail_category";
        public static final String COLUMN_DETAIL_CATEGORY_NAME = "detail_category_name";

    }

    public static final class DrawableEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_DRAWABLE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DRAWABLE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_DRAWABLE;


        public static final String TABLE_NAME = "drawable";
        public static final String COLUMN_DRAWABLE_URI = "uri";

    }

    public static final class MesureEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MESURE).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MESURE;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MESURE;


        public static final String TABLE_NAME = "mesure";
        public static final String COLUMN_MESURE_NAME = "mesure_name";
        public static final String COLUMN_MESURE_SHORT_NAME = "mesure_short_name";
        public static final String COLUMN_CONVERSION_A_MESURE_ID = "conversion_a_mesure_id";
        public static final String COLUMN_CONVERSION_A_FACTOR = "conversion_a_factor";
        public static final String COLUMN_CONVERSION_B_MESURE_ID = "conversion_b_mesure_id";
        public static final String COLUMN_CONVERSION_B_FACTOR = "conversion_b_factor";
        public static final String COLUMN_CONVERSION_C_MESURE_ID = "conversion_c_mesure_id";
        public static final String COLUMN_CONVERSION_C_FACTOR = "conversion_c_factor";


    }

    public static final class MepCategoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEP_CATEGORY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEP_CATEGORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEP_CATEGORY;


        public static final String TABLE_NAME = "mep_category";
        public static final String COLUMN_MEP_CATEGORY_NAME = "mep_category_name";

    }

    public static final class MepSubCategoryEntry implements BaseColumns {

        public static final Uri CONTENT_URI =
                BASE_CONTENT_URI.buildUpon().appendPath(PATH_MEP_SUBCATEGORY).build();

        public static final String CONTENT_TYPE =
                ContentResolver.CURSOR_DIR_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEP_SUBCATEGORY;
        public static final String CONTENT_ITEM_TYPE =
                ContentResolver.CURSOR_ITEM_BASE_TYPE + "/" + CONTENT_AUTHORITY + "/" + PATH_MEP_SUBCATEGORY;


        public static final String TABLE_NAME = "mep_subcategory";
        public static final String COLUMN_MEP_SUBCATEGORY_NAME = "mep_subcategory_name";

    }

}