package com.adx2099.bakingapp.ui.splash;

import com.adx2099.bakingapp.models.RecipeResponse;

import java.util.List;

public class SplashPresenter implements OnSplashListener, SplashPres {
    private SplashView splashView;
    private SplashInteractor splashInteractor;
    public SplashPresenter(SplashView splashView) {
        this.splashView = splashView;
        splashInteractor = new SplashInteractor(this);
    }

    @Override
    public void onRecipesFailed(String msg) {

    }

    @Override
    public void onRecipesLoaded(List<RecipeResponse> recipeResponses) {
        splashInteractor.insertIntoDB(recipeResponses);
    }

    @Override
    public void fetchRecipes() {
        splashInteractor.fetchRecipes();
    }

    @Override
    public boolean dbCount() {
        return splashInteractor.dbCount();
    }
}
