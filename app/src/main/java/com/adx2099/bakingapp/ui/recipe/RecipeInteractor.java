package com.adx2099.bakingapp.ui.recipe;

import android.database.Cursor;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.Loader;
import android.util.Log;

import com.adx2099.bakingapp.App;
import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.connection.APIService;
import com.adx2099.bakingapp.connection.ApiUtils;
import com.adx2099.bakingapp.helper.BakingDbHelper;
import com.adx2099.bakingapp.models.RecipeResponse;

import java.util.List;
import java.util.Observable;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;


public class RecipeInteractor implements RecipeInt  {
    private OnRecipeListener listener;
    private APIService mApiService;
    private BakingDbHelper dbHelper;

    public RecipeInteractor(OnRecipeListener listener) {
        this.listener = listener;
        mApiService = ApiUtils.getApiService();
        dbHelper = new BakingDbHelper(App.getCurrentActivity());
    }


    @Override
    public Cursor retrieveRecipes() {
       return  dbHelper.getAllFromBaking();
    }

}
