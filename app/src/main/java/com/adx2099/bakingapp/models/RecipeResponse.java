package com.adx2099.bakingapp.models;

import android.databinding.BaseObservable;
import android.databinding.Bindable;
import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeResponse extends BaseObservable implements Parcelable {

    @Expose
    @SerializedName("id")
    private byte recipeId;
    @Expose
    @SerializedName("name")
    private String name;
    @Expose
    @SerializedName("servings")
    private String servings;
    @Expose
    @SerializedName("image")
    private String image;
    @Expose
    @SerializedName("ingredients")
    private List<Ingredient> ingredients = null;
    @Expose
    @SerializedName("steps")
    private List<Steps> steps = null;

    private Boolean fav = null;

    public Boolean getFav() {
        return fav;
    }

    public void setFav(Boolean fav) {
        this.fav = fav;
    }

    @Bindable
    public byte getRecipeId() {
        return recipeId;
    }

    public void setRecipeId(byte recipeId) {
        this.recipeId = recipeId;
    }



    @Bindable
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    @Bindable
    public String getServings() {
        return servings;
    }

    public void setServings(String servings) {
        this.servings = servings;
    }

    @Bindable
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
    @Bindable
    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @Bindable
    public List<Steps> getSteps() {
        return steps;
    }

    public void setSteps(List<Steps> steps) {
        this.steps = steps;
    }

    public RecipeResponse(){

    }

    protected RecipeResponse(Parcel in) {
        recipeId = in.readByte();
        name = in.readString();
        servings = in.readString();
        image = in.readString();
    }

    public static final Creator<RecipeResponse> CREATOR = new Creator<RecipeResponse>() {
        @Override
        public RecipeResponse createFromParcel(Parcel in) {
            return new RecipeResponse(in);
        }

        @Override
        public RecipeResponse[] newArray(int size) {
            return new RecipeResponse[size];
        }
    };



    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeByte(recipeId);
        dest.writeString(name);
        dest.writeString(servings);
        dest.writeString(image);
    }
}
