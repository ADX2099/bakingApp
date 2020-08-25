package com.adx2099.bakingapp.ui.splash;

import android.database.Cursor;
import android.util.Log;

import com.adx2099.bakingapp.App;
import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.connection.APIService;
import com.adx2099.bakingapp.connection.ApiUtils;
import com.adx2099.bakingapp.helper.BakingDbHelper;
import com.adx2099.bakingapp.models.RecipeResponse;

import java.util.List;

import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

public class SplashInteractor implements SplashInt {
    private APIService mApiService;
    private OnSplashListener listener;
    private BakingDbHelper dbHelper;

    public SplashInteractor(OnSplashListener listener) {
        this.listener = listener;
        mApiService = ApiUtils.getApiService();
        dbHelper = new BakingDbHelper(App.getCurrentActivity());
    }

    @Override
    public void fetchRecipes() {
        mApiService.getRecipes()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<List<RecipeResponse>>() {
                    @Override
                    public void onCompleted() {
                        Log.e("OkHttp", "onCompleted: " );
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e("OkHttp", "Error .: " +  e.getMessage());
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(List<RecipeResponse> recipeResponses) {
                        if (recipeResponses.size() == 0) {
                            listener.onRecipesFailed(App.getInstance().getString(R.string.recipe_not_found));
                            //mRecipeFragmentView.onProgressVisibility(false);
                            return;
                        }

                        listener.onRecipesLoaded(recipeResponses);

                    }
                });
    }

    @Override
    public void insertIntoDB(List<RecipeResponse> recipeResponses) {
        int size = recipeResponses.size();
        if(size > 0){
            for(int i = 0; i < size; i++){
                dbHelper.insertToBaking(recipeResponses.get(i));
            }
        }
        dbHelper.close();
    }

    @Override
    public boolean dbCount() {
        Cursor valuesInDB = dbHelper.getAllFromBaking();
        if(valuesInDB.getCount() > 0){
            return true;
        }
        dbHelper.close();
        return false;
    }
}
