package com.adx2099.bakingapp.ui.splash;

import com.adx2099.bakingapp.models.RecipeResponse;

import java.util.List;

interface SplashView {
    void recipeLoaded(List<RecipeResponse> recipeResponses);
    void recipesFailed(String msg);
}
