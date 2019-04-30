package com.example.android.cookpit.Model.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.cookpit.Model.data.KitchenContract.IngredientEntry.COLUMN_CATEGORY_A;
import static com.example.android.cookpit.Model.data.KitchenContract.MenuEntry.COLUMN_DATE_CREATED;
import static com.example.android.cookpit.Model.data.KitchenContract.MenuEntry.COLUMN_MENU_NAME;

/**
 * Created by alexandrelevieux on 15/10/2016.
 */

public class KitchenDbHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;

    public static final String DATABASE_NAME = "cookpit.db";

    public KitchenDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        final String SQL_CREATE_FTS_MENU_TABLE = "CREATE VIRTUAL TABLE " +
                KitchenContract.FtsMenuEntry.TABLE_NAME + " USING fts4(tokenize=simple, content='" +
                KitchenContract.MenuEntry.TABLE_NAME + "' ," +
                KitchenContract.MenuEntry.COLUMN_MENU_NAME + " , " +
                KitchenContract.MenuEntry.COLUMN_USER_NAME + ");";

        final String SQL_CREATE_FTS_DISH_TABLE = "CREATE VIRTUAL TABLE " +
                KitchenContract.FtsDishEntry.TABLE_NAME + " USING fts4(tokenize=simple, content='" +
                KitchenContract.DishEntry.TABLE_NAME + "' ," +
                KitchenContract.DishEntry.COLUMN_DISH_NAME + " , " +
                KitchenContract.DishEntry.COLUMN_DESCRIPTION + ");";

        final String SQL_CREATE_FTS_MEP_TABLE = "CREATE VIRTUAL TABLE " +
                KitchenContract.FtsMepEntry.TABLE_NAME + " USING fts4(tokenize=simple, content='" +
                KitchenContract.MepEntry.TABLE_NAME + "' ," +
                KitchenContract.MepEntry.COLUMN_MEP_NAME + " , " +
                KitchenContract.MepEntry.COLUMN_DESCRIPTION + ");";

        final String SQL_CREATE_FTS_INGREDIENT_TABLE = "CREATE VIRTUAL TABLE " +
                KitchenContract.FtsIngredientEntry.TABLE_NAME + " USING fts4(tokenize=simple, content='" +
                KitchenContract.IngredientEntry.TABLE_NAME + "' ," +
                KitchenContract.IngredientEntry.COLUMN_INGREDIENT_NAME + " , " +
                KitchenContract.IngredientEntry.COLUMN_CATEGORY_A + " , " +
                KitchenContract.IngredientEntry.COLUMN_CATEGORY_B + " , " +
                KitchenContract.IngredientEntry.COLUMN_QUALITY_NICKNAME_LIST + " );";


        final String SQL_CREATE_DISH_CATEGORY_TABLE = "CREATE TABLE " +
                KitchenContract.DishCategoryEntry.TABLE_NAME + " (" +
                KitchenContract.DishCategoryEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.DishCategoryEntry.COLUMN_CATEGORY_NAME + " TEXT UNIQUE NOT NULL, " +
                KitchenContract.DishCategoryEntry.COLUMN_POSITION + " INTEGER );";


        final String SQL_CREATE_MENU_TABLE = "CREATE TABLE " +
                KitchenContract.MenuEntry.TABLE_NAME + " (" +
                KitchenContract.MenuEntry._ID + " INTEGER PRIMARY KEY, " +
                COLUMN_MENU_NAME + " TEXT UNIQUE NOT NULL, " +
                KitchenContract.MenuEntry.COLUMN_USER_NAME + " TEXT NOT NULL, " +
                COLUMN_DATE_CREATED + " INTEGER , " +
                KitchenContract.MenuEntry.COLUMN_NUMBER_OF_DISH + " INTEGER " +
                " );";

        final String SQL_CREATE_DISH_TABLE = "CREATE TABLE " +
                KitchenContract.DishEntry.TABLE_NAME + " (" +
                KitchenContract.DishEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.DishEntry.COLUMN_DISH_NAME + " TEXT UNIQUE NOT NULL, " +
                KitchenContract.DishEntry.COLUMN_USER_NAME + " TEXT NOT NULL, " +
                KitchenContract.DishEntry.COLUMN_NUMBER_OF_INGREDIENTS + " INTEGER, " +
                KitchenContract.DishEntry.COLUMN_NUMBER_OF_MEP + " INTEGER, " +
                KitchenContract.DishEntry.COLUMN_COST_PRICE + " REAL, " +
                KitchenContract.DishEntry.COLUMN_DESCRIPTION + " TEXT, " +
                KitchenContract.DishEntry.COLUMN_SELLING_PRICE + " REAL, " +
                KitchenContract.DishEntry.COLUMN_DISH_CATEGORY_ID + " INTEGER, " +
                KitchenContract.DishEntry.COLUMN_DRAWABLE_ID + " INTEGER, " +

                " FOREIGN KEY (" + KitchenContract.DishEntry.COLUMN_DISH_CATEGORY_ID + ") REFERENCES " +
                KitchenContract.DishCategoryEntry.TABLE_NAME + " (" + KitchenContract.DishCategoryEntry._ID + "));";


        final String SQL_CREATE_INGREDIENT_TABLE = "CREATE TABLE " +
                KitchenContract.IngredientEntry.TABLE_NAME + " (" +
                KitchenContract.IngredientEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.IngredientEntry.COLUMN_INGREDIENT_NAME + " TEXT, " +
                COLUMN_CATEGORY_A + " TEXT, " +
                KitchenContract.IngredientEntry.COLUMN_CATEGORY_B + " TEXT, " +
                KitchenContract.IngredientEntry.COLUMN_QUALITY_NICKNAME_LIST + " TEXT, " +
                KitchenContract.IngredientEntry.COLUMN_DEF_SUPPLIER_LIST + " TEXT, " +
                KitchenContract.IngredientEntry.COLUMN_DEF_COST_PRICE_LIST + " REAL, " +
                KitchenContract.IngredientEntry.COLUMN_DEF_MESURE_ID_LIST + " TEXT, " +

                " FOREIGN KEY (" + COLUMN_CATEGORY_A + ") REFERENCES " +
                KitchenContract.IngredientCategoryEntry.TABLE_NAME + " (" + KitchenContract.IngredientCategoryEntry._ID + "), " +

                " FOREIGN KEY (" + KitchenContract.IngredientEntry.COLUMN_CATEGORY_B + ") REFERENCES " +
                KitchenContract.IngredientSubCategoryEntry.TABLE_NAME + " (" + KitchenContract.IngredientSubCategoryEntry._ID + "), " +

                " FOREIGN KEY (" + KitchenContract.IngredientEntry.COLUMN_QUALITY_NICKNAME_LIST + ") REFERENCES " +
                KitchenContract.IngredientDetailCategoryEntry.TABLE_NAME + " (" + KitchenContract.IngredientDetailCategoryEntry._ID + ")" +


                " );";


        final String SQL_CREATE_INGREDIENT_CATEGORY_A_TABLE = "CREATE TABLE " +
                KitchenContract.IngredientCategoryEntry.TABLE_NAME + " (" +
                KitchenContract.IngredientCategoryEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.IngredientCategoryEntry.COLUMN_CATEGORY_NAME + " TEXT UNIQUE NOT NULL );";

        final String SQL_CREATE_INGREDIENT_CATEGORY_B_TABLE = "CREATE TABLE " +
                KitchenContract.IngredientSubCategoryEntry.TABLE_NAME + " (" +
                KitchenContract.IngredientSubCategoryEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.IngredientSubCategoryEntry.COLUMN_SUB_CATEGORY_NAME + " TEXT UNIQUE NOT NULL );";

        final String SQL_CREATE_COURSE_CATEGORY_TABLE = "CREATE TABLE " +
                KitchenContract.CourseCategoryEntry.TABLE_NAME + " (" +
                KitchenContract.CourseCategoryEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.CourseCategoryEntry.COLUMN_COURSE_NAME + " TEXT UNIQUE NOT NULL);";

        final String SQL_CREATE_MEP_CATEGORY_TABLE = "CREATE TABLE " +
                KitchenContract.MepCategoryEntry.TABLE_NAME + " (" +
                KitchenContract.MepCategoryEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.MepCategoryEntry.COLUMN_MEP_CATEGORY_NAME + " TEXT UNIQUE NOT NULL);";

        final String SQL_CREATE_MEP_TABLE = "CREATE TABLE " +
                KitchenContract.MepEntry.TABLE_NAME + " (" +
                KitchenContract.MepEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.MepEntry.COLUMN_MEP_NAME + " TEXT UNIQUE NOT NULL, " +
                KitchenContract.MepEntry.COLUMN_MEP_USERNAME + " TEXT NOT NULL, " +
                KitchenContract.MepEntry.COLUMN_DESCRIPTION + " TEXT, " +
                KitchenContract.MepEntry.COLUMN_MEP_CATEGORY_A + " TEXT, " +
                KitchenContract.MepEntry.COLUMN_MEP_CATEGORY_B + " TEXT, " +
                KitchenContract.MepEntry.COLUMN_YEILD + " REAL, " +
                KitchenContract.MepEntry.COLUMN_MESURE + " INTEGER, " +
                KitchenContract.MepEntry.COLUMN_DRAWABLE_ID + " REAL, " +
                KitchenContract.MepEntry.COLUMN_TRIM_INGREDIENT_ID + " INTEGER, " +

                " FOREIGN KEY (" + KitchenContract.MepEntry.COLUMN_MESURE + ") REFERENCES " +
                KitchenContract.MesureEntry.TABLE_NAME + " (" + KitchenContract.MesureEntry._ID + ")," +

                " FOREIGN KEY (" + KitchenContract.MepEntry.COLUMN_TRIM_INGREDIENT_ID + ") REFERENCES " +
                KitchenContract.IngredientEntry.TABLE_NAME + " (" + KitchenContract.IngredientEntry._ID + ")" +


                ");";

        final String SQL_CREATE_MESURE_TABLE = "CREATE TABLE " +
                KitchenContract.MesureEntry.TABLE_NAME + " (" +
                KitchenContract.MesureEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.MesureEntry.COLUMN_MESURE_NAME + " TEXT UNIQUE NOT NULL, " +
                KitchenContract.MesureEntry.COLUMN_MESURE_SHORT_NAME + " TEXT UNIQUE NOT NULL, " +
                KitchenContract.MesureEntry.COLUMN_CONVERSION_A_MESURE_ID + " INTEGER, " +
                KitchenContract.MesureEntry.COLUMN_CONVERSION_A_FACTOR + " REAL, " +
                KitchenContract.MesureEntry.COLUMN_CONVERSION_B_MESURE_ID + " INTEGER, " +
                KitchenContract.MesureEntry.COLUMN_CONVERSION_B_FACTOR + " REAL, " +
                KitchenContract.MesureEntry.COLUMN_CONVERSION_C_MESURE_ID + " INTEGER, " +
                KitchenContract.MesureEntry.COLUMN_CONVERSION_C_FACTOR + " REAL, " +

                " FOREIGN KEY (" + KitchenContract.MesureEntry.COLUMN_CONVERSION_A_MESURE_ID + ") REFERENCES " +
                KitchenContract.MesureEntry.TABLE_NAME + " (" + KitchenContract.MesureEntry._ID + "), " +

                " FOREIGN KEY (" + KitchenContract.MesureEntry.COLUMN_CONVERSION_B_MESURE_ID + ") REFERENCES " +
                KitchenContract.MesureEntry.TABLE_NAME + " (" + KitchenContract.MesureEntry._ID + "), " +

                " FOREIGN KEY (" + KitchenContract.MesureEntry.COLUMN_CONVERSION_C_MESURE_ID + ") REFERENCES " +
                KitchenContract.MesureEntry.TABLE_NAME + " (" + KitchenContract.MesureEntry._ID + ")" +


                " );";


        final String SQL_CREATE_DISH_MEP_INGREDIENT_TABLE = "CREATE TABLE " +
                KitchenContract.DishMepIngredientEntry.TABLE_NAME + " (" +
                KitchenContract.DishMepIngredientEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.DishMepIngredientEntry.COLUMN_DISH_ID + " INTEGER NOT NULL, " +
                KitchenContract.DishMepIngredientEntry.COLUMN_INGREDIENT_ID + " INTEGER, " +
                KitchenContract.DishMepIngredientEntry.COLUMN_MEP_ID + " INTEGER, " +
                KitchenContract.DishMepIngredientEntry.COLUMN_QUANTITY + " REAL, " +
                KitchenContract.DishMepIngredientEntry.COLUMN_MESURE + " INTEGER, " +
                KitchenContract.DishMepIngredientEntry.COLUMN_POSITION + " INTEGER, " +

                " FOREIGN KEY (" + KitchenContract.DishMepIngredientEntry.COLUMN_DISH_ID + ") REFERENCES " +
                KitchenContract.DishEntry.TABLE_NAME + " (" + KitchenContract.DishEntry._ID + "), " +

                " FOREIGN KEY (" + KitchenContract.DishMepIngredientEntry.COLUMN_INGREDIENT_ID + ") REFERENCES " +
                KitchenContract.IngredientEntry.TABLE_NAME + " (" + KitchenContract.IngredientEntry._ID + "), " +

                " FOREIGN KEY (" + KitchenContract.DishMepIngredientEntry.COLUMN_MEP_ID + ") REFERENCES " +
                KitchenContract.MepEntry.TABLE_NAME + " (" + KitchenContract.MepEntry._ID + ")," +

                " FOREIGN KEY (" + KitchenContract.DishMepIngredientEntry.COLUMN_MESURE + ") REFERENCES " +
                KitchenContract.MesureEntry.TABLE_NAME + " (" + KitchenContract.MesureEntry._ID + ")" +


                " );";

        final String SQL_CREATE_MEP_INGREDIENT_TABLE = "CREATE TABLE " +
                KitchenContract.MepIngredientEntry.TABLE_NAME + " (" +
                KitchenContract.MepIngredientEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.MepIngredientEntry.COLUMN_MEP_ID + " INTEGER NOT NULL, " +
                KitchenContract.MepIngredientEntry.COLUMN_INGREDIENT_ID + " INTEGER, " +
                KitchenContract.MepIngredientEntry.COLUMN_SUB_MEP_ID + " INTEGER, " +
                KitchenContract.MepIngredientEntry.COLUMN_QUANTITY + " REAL, " +
                KitchenContract.MepIngredientEntry.COLUMN_MESURE + " INTEGER, " +
                KitchenContract.MepIngredientEntry.COLUMN_POSITION + " INTEGER, " +

                " FOREIGN KEY (" + KitchenContract.MepIngredientEntry.COLUMN_MEP_ID + ") REFERENCES " +
                KitchenContract.MepEntry.TABLE_NAME + " (" + KitchenContract.MepEntry._ID + "), " +

                " FOREIGN KEY (" + KitchenContract.MepIngredientEntry.COLUMN_INGREDIENT_ID + ") REFERENCES " +
                KitchenContract.IngredientEntry.TABLE_NAME + " (" + KitchenContract.IngredientEntry._ID + "), " +

                " FOREIGN KEY (" + KitchenContract.MepIngredientEntry.COLUMN_SUB_MEP_ID + ") REFERENCES " +
                KitchenContract.MepEntry.TABLE_NAME + " (" + KitchenContract.MepEntry._ID + ")," +

                " FOREIGN KEY (" + KitchenContract.MepIngredientEntry.COLUMN_MESURE + ") REFERENCES " +
                KitchenContract.MesureEntry.TABLE_NAME + " (" + KitchenContract.MesureEntry._ID + ")" +


                " );";

        final String SQL_CREATE_MENU_DISH_TABLE = "CREATE TABLE " +
                KitchenContract.MenuDishEntry.TABLE_NAME + " (" +
                KitchenContract.MenuDishEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.MenuDishEntry.COLUMN_MENU_ID + " INTEGER NOT NULL, " +
                KitchenContract.MenuDishEntry.COLUMN_DISH_ID + " INTEGER, " +
                KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID + " INTEGER, " +
                KitchenContract.MenuDishEntry.COLUMN_COURSE_POSITION + " INTEGER, " +
                KitchenContract.MenuDishEntry.COLUMN_DISH_POSITION + " INTEGER, " +

                " FOREIGN KEY (" + KitchenContract.MenuDishEntry.COLUMN_MENU_ID + ") REFERENCES " +
                KitchenContract.MenuEntry.TABLE_NAME + " (" + KitchenContract.MenuEntry._ID + "), " +

                " FOREIGN KEY (" + KitchenContract.MenuDishEntry.COLUMN_DISH_ID + ") REFERENCES " +
                KitchenContract.DishEntry.TABLE_NAME + " (" + KitchenContract.DishEntry._ID + "), " +

                " FOREIGN KEY (" + KitchenContract.MenuDishEntry.COLUMN_COURSE_CATEGORY_ID + ") REFERENCES " +
                KitchenContract.CourseCategoryEntry.TABLE_NAME + " (" + KitchenContract.CourseCategoryEntry._ID + ")" +


                " );";
        final String SQL_CREATE_DRAWABLE_TABLE = "CREATE TABLE " +
                KitchenContract.DrawableEntry.TABLE_NAME + " (" +
                KitchenContract.DrawableEntry._ID + " INTEGER PRIMARY KEY, " +
                KitchenContract.DrawableEntry.COLUMN_DISH_ID + " INTEGER , " +
                KitchenContract.DrawableEntry.COLUMN_DRAWABLE_URI + " TEXT NOT NULL, " +
                KitchenContract.DrawableEntry.COLUMN_FILE_NAME + " TEXT NOT NULL, " +
                KitchenContract.DrawableEntry.COLUMN_DEFAULT_DRAWABLE + " INTEGER NOT NULL, " +
                KitchenContract.DrawableEntry.COLUMN_DATE_TIME_CREATED + " INTEGER NOT NULL, " +
                KitchenContract.DrawableEntry.COLUMN_PICTURE_TAGS_BLOB + " BLOB, " +


                " FOREIGN KEY (" + KitchenContract.DrawableEntry.COLUMN_DISH_ID + ") REFERENCES " +
                KitchenContract.DishEntry.TABLE_NAME + " (" + KitchenContract.DishEntry._ID + ") " +


                " );";

        //base entry table
        db.execSQL(SQL_CREATE_DISH_TABLE);
        db.execSQL(SQL_CREATE_MENU_TABLE);
        db.execSQL(SQL_CREATE_INGREDIENT_TABLE);
        db.execSQL(SQL_CREATE_MEP_TABLE);
        db.execSQL(SQL_CREATE_MESURE_TABLE);
        db.execSQL(SQL_CREATE_DRAWABLE_TABLE);


        //tables category
        db.execSQL(SQL_CREATE_INGREDIENT_CATEGORY_A_TABLE);
        db.execSQL(SQL_CREATE_INGREDIENT_CATEGORY_B_TABLE);

        db.execSQL(SQL_CREATE_COURSE_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_DISH_CATEGORY_TABLE);
        db.execSQL(SQL_CREATE_MEP_CATEGORY_TABLE);

        //relational table
        db.execSQL(SQL_CREATE_DISH_MEP_INGREDIENT_TABLE);
        db.execSQL(SQL_CREATE_MEP_INGREDIENT_TABLE);
        db.execSQL(SQL_CREATE_MENU_DISH_TABLE);

        //FTS table
        db.execSQL(SQL_CREATE_FTS_MENU_TABLE);
        db.execSQL(SQL_CREATE_FTS_DISH_TABLE);
        db.execSQL(SQL_CREATE_FTS_MEP_TABLE);
        db.execSQL(SQL_CREATE_FTS_INGREDIENT_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
