package com.adx2099.bakingapp.callback;

import com.adx2099.bakingapp.models.Ingredient;
import com.adx2099.bakingapp.models.RecipeResponse;
import com.adx2099.bakingapp.models.Steps;

import java.util.List;

public interface IStepItemClickListener {
    void onStepItemClick(Steps step);
}
