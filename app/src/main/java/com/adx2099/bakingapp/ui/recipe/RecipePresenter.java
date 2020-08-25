package com.adx2099.bakingapp.ui.recipe;

import android.database.Cursor;

import com.adx2099.bakingapp.models.RecipeResponse;

import java.util.List;

public class RecipePresenter implements OnRecipeListener, RecipePres {
    RecipeView recipeView;
    RecipeInteractor recipeInteractor;

    public RecipePresenter(RecipeView recipeView) {
        this.recipeView = recipeView;
        recipeInteractor = new RecipeInteractor(this);
    }


    @Override
    public Cursor retrieveRecipesFromDB() {
        return recipeInteractor.retrieveRecipes();
    }

    @Override
    public void OnGetRecipes() {

    }
}
