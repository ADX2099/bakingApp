package com.adx2099.bakingapp.connection;

import com.adx2099.bakingapp.models.RecipeResponse;

import java.util.List;


import retrofit2.http.GET;
import rx.Observable;

import static com.adx2099.bakingapp.helper.BakingConstants.URL_RECIPES;

public interface APIService {

    @GET(URL_RECIPES)
    Observable<List<RecipeResponse>> getRecipes();



}
