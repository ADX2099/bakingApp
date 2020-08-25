package com.adx2099.bakingapp.widget;

import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.support.annotation.Nullable;
import android.util.Log;
import android.widget.RemoteViews;

import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.provider.BakingContentProvider;
import com.adx2099.bakingapp.provider.BakingDbContract;

import static com.adx2099.bakingapp.provider.BakingDbContract.BASE_CONTENT_URI;
import static com.adx2099.bakingapp.provider.BakingDbContract.PATH_BAKING;

public class GetRecipesService extends IntentService {
    public static final String ACTION_GET_RECIPE = "com.adx2099.bakingapp.action.get_recipes";

    Context mContext;

    public static void startActionGetRecipes(Context context){
        Intent intent = new Intent(context, GetRecipesService.class);
        intent.setAction(ACTION_GET_RECIPE);
        context.startService(intent);

    }

    public GetRecipesService() {
        super("GetRecipesService");
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if(intent != null){
            final String action = intent.getAction();
            if(ACTION_GET_RECIPE.equals(action)){
               handleActionUpdateBakingWidgets();
            }
        }
    }


    private void handleActionUpdateBakingWidgets() {

        Uri BAKING_URI = BASE_CONTENT_URI.buildUpon().appendPath(PATH_BAKING).build();
        Cursor cursor = getContentResolver().query(
                BAKING_URI,
                null,
                null,
                null,
                null
        );
        if (cursor != null && cursor.getCount() > 0) {
            cursor.moveToFirst();

            int recipeNameIndex = cursor.getColumnIndex(BakingDbContract.BakingEntry.COLUMN_NAME);
            int recipeIngredientsIndex = cursor.getColumnIndex(BakingDbContract.BakingEntry.COLUMN_INGREDIENTS);

            String recipeName = cursor.getString(recipeNameIndex);
            String recipeIngredients = cursor.getString(recipeIngredientsIndex);
            cursor.close();


            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingWidgetProvider.class));
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.gvRecipeWidget);
            BakingWidgetProvider.updateBakingWidgets(this, appWidgetManager,recipeName,recipeIngredients,appWidgetIds);

        }


    }
}
