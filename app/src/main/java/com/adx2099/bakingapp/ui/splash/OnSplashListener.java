package com.adx2099.bakingapp.ui.splash;

import com.adx2099.bakingapp.models.RecipeResponse;

import java.util.List;

interface OnSplashListener {
    void onRecipesFailed(String msg);
    void onRecipesLoaded(List<RecipeResponse> recipeResponses);
}
