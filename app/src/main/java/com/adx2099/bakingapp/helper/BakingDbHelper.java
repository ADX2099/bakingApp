package com.adx2099.bakingapp.helper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.Toast;

import com.adx2099.bakingapp.App;
import com.adx2099.bakingapp.models.RecipeResponse;
import com.adx2099.bakingapp.provider.BakingDbContract;
import com.adx2099.bakingapp.provider.BakingDbContract.BakingEntry;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

public class BakingDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "bakingDb.db";
    private static final int VERSION = 1;

    public BakingDbHelper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        final String CREATE_TABLE = "CREATE TABLE "  + BakingEntry.TABLE_NAME + " (" +
                BakingEntry._ID                + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                BakingEntry.COLUMN_INGREDIENTS + " TEXT NOT NULL, " +
                BakingEntry.COLUMN_NAME     + " TEXT NOT NULL, "       +
                BakingEntry.COLUMN_STEPS    + " TEXT NOT NULL,"     +
                BakingEntry.COLUMN_FAV + "TEXT," +
                BakingEntry.COLUMN_SERVINGS + " INTEGER NOT NULL);";

        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + BakingEntry.TABLE_NAME);
        onCreate(db);
    }

    public Uri insertToBaking(RecipeResponse recipeResponse){
        Gson gson = new Gson();

        ContentValues values = new ContentValues();
        values.put(BakingEntry._ID, recipeResponse.getRecipeId());
        values.put(BakingEntry.COLUMN_INGREDIENTS, gson.toJson(recipeResponse.getIngredients()));
        values.put(BakingEntry.COLUMN_STEPS, gson.toJson(recipeResponse.getSteps()));
        values.put(BakingEntry.COLUMN_SERVINGS,recipeResponse.getServings());
        values.put(BakingEntry.COLUMN_NAME, recipeResponse.getName());
        Uri uri = App.getCurrentActivity().getContentResolver().insert(BakingEntry.CONTENT_URI, values);

        if(uri != null) {
            return uri;
        }
        return null;
    }

    public Cursor getAllFromBaking(){
        Cursor cursor = App.getCurrentActivity().getContentResolver().query(BakingEntry.CONTENT_URI,
                null,
                null,
                null,
                BakingEntry._ID);
        return cursor;
    }

}
