package com.adx2099.bakingapp.utils;

import android.util.Log;

import com.adx2099.bakingapp.helper.BakingConstants;
import com.adx2099.bakingapp.models.Ingredient;
import com.adx2099.bakingapp.models.Steps;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.adx2099.bakingapp.helper.BakingConstants.MEASURE_KEY;
import static com.adx2099.bakingapp.helper.BakingConstants.QUANTITY_KEY;

public class DataUtils {

    public static List<Ingredient> parseIngredients(String ingredients){
        List<Ingredient> listIngredients = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(ingredients);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject ingredientJSON = jsonArray.getJSONObject(i);
                Ingredient ingredientObj = new Ingredient();
                ingredientObj.ingredient = ingredientJSON.getString(BakingConstants.INGREDIENT_KEY);
                ingredientObj.measure = ingredientJSON.getString(MEASURE_KEY);
                ingredientObj.quantity = ingredientJSON.getLong(QUANTITY_KEY);
                listIngredients.add(ingredientObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listIngredients;
    }

    private static String formatQuantity(float quantity) {
        Log.d("ADX2099", "The value: " + String.valueOf(quantity));
        if (quantity == 0.0) {
            return "1/2";
        } else {
            int round = Math.round(quantity);
            return String.valueOf(round);
        }



    }

    public static List<Steps> parseSteps(String steps){
        List<Steps> listSteps = new ArrayList<>();
        try {
            JSONArray jsonArray = new JSONArray(steps);
            for(int i = 0; i < jsonArray.length(); i++){
                JSONObject stepsJSON = jsonArray.getJSONObject(i);
                Steps stepsObj = new Steps();
                stepsObj.description = stepsJSON.getString(BakingConstants.DESCRIPTION_KEY);
                stepsObj.shortDescription = stepsJSON.getString(BakingConstants.SHORT_DESC_KEY);
                stepsObj.thumbnailURL = stepsJSON.getString(BakingConstants.THUMBNAIL_KEY);
                stepsObj.videoURL = stepsJSON.getString(BakingConstants.VIDEO_KEY);
                stepsObj.stepId = stepsJSON.getInt(BakingConstants.STEPS_ID);
                listSteps.add(stepsObj);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return listSteps;
    }



}
