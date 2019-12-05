package com.adx2099.bakingapp.ui.splash;

import com.adx2099.bakingapp.models.RecipeResponse;

import java.util.List;

interface SplashInt {
    void fetchRecipes();
    void insertIntoDB(List<RecipeResponse> recipeResponses);
    boolean dbCount();
}
