 package com.adx2099.bakingapp.widget;

import android.annotation.TargetApi;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;

import com.adx2099.bakingapp.MainActivity;
import com.adx2099.bakingapp.R;

import static com.adx2099.bakingapp.helper.BakingConstants.FROM_WIDGET;

/**
 * Implementation of App Widget functionality.
 */
public class BakingWidgetProvider extends AppWidgetProvider {

    //Actualizamos cada widget
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    static void updateBakingWidget(Context context, AppWidgetManager appWidgetManager,String recipeName, String recipeIngredients,  int appWidgetId) {
        Bundle options = appWidgetManager.getAppWidgetOptions(appWidgetId);
        //Aqui construyo el grid que voy a mostrar
        RemoteViews remoteViews = getRecipesFromGridView(context);
        //El manejador  de widget se encarga de actualizar el widget
        appWidgetManager.updateAppWidget(appWidgetId, remoteViews);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        // There may be multiple widgets active, so update all of them
        //Cada vez que se llama el update del widget
        GetRecipesService.startActionGetRecipes(context);
    }


    //If we have multiple widgets on home screen
    public static void updateBakingWidgets(Context context, AppWidgetManager appWidgetManager, String recipeName, String recipeIngredients, int[] appWidgetsIds){
        for (int appWidgetId : appWidgetsIds) {
            updateBakingWidget(context, appWidgetManager,recipeName,recipeIngredients, appWidgetId);
        }
    }
    /*
    public static RemoteViews GetSingleRecipeRemoteView(Context context,String recipeName, String recipeIngredients){
        Intent intent;
        intent = new Intent(context, MainActivity.class);
        intent.putExtra(FROM_WIDGET,"true");
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
        //views.setTextViewText(R.id.);
    }*/

  public static RemoteViews getRecipesFromGridView(Context context){
      RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_widget_provider);
      Intent gridIntent = new Intent(context, GridWidgetService.class);
      views.setRemoteAdapter(R.id.gvRecipeWidget, gridIntent);
      Intent appIntent = new Intent(context, MainActivity.class);
      appIntent.putExtra(FROM_WIDGET,"true");
      PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
      views.setPendingIntentTemplate(R.id.gvRecipeWidget, appPendingIntent);
      views.setEmptyView(R.id.gvRecipeWidget, R.id.emptyView);
      views.setOnClickPendingIntent(R.id.buttonGo, appPendingIntent);

      return views;
  }


  @Override
  public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

