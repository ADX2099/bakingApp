package com.adx2099.bakingapp.ui.recipe;


import android.database.Cursor;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adx2099.bakingapp.App;
import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.adapters.RecipeRecycleAdapter;
import com.adx2099.bakingapp.callback.IRecipeItemClickListener;
import com.adx2099.bakingapp.databinding.FragmentRecipeBinding;
import com.adx2099.bakingapp.helper.BakingConstants;
import com.adx2099.bakingapp.models.Ingredient;
import com.adx2099.bakingapp.models.RecipeResponse;
import com.adx2099.bakingapp.provider.BakingDbContract;
import com.adx2099.bakingapp.ui.steps.StepsFragment;
import com.adx2099.bakingapp.utils.DataUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import static com.adx2099.bakingapp.helper.BakingConstants.LAYOUT;
import static com.adx2099.bakingapp.helper.BakingConstants.RECIPE_DATA_KEY;
import static com.adx2099.bakingapp.helper.BakingConstants.STEP_FRAG;


public class RecipeFragment extends Fragment implements RecipeView, IRecipeItemClickListener, LoaderManager.LoaderCallbacks<Cursor> {
    private FragmentRecipeBinding fragmentRecipeBinding;
    private  List<RecipeResponse> mRecipeResponses = new ArrayList<>();
    private RecipeRecycleAdapter adapter;
    private static final int BAKING_LOADER_ID = 0;
    private int lay;


    RecipePresenter recipePresenter;

    public RecipeFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = getArguments();
        lay = bundle.getInt(LAYOUT);
        recipePresenter = new RecipePresenter(this);
        recipePresenter.retrieveRecipesFromDB();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentRecipeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe, container, false);
        LoaderManager loaderManager = App.getCurrentActivity().getSupportLoaderManager();
        Loader<Cursor> cursorLoader = loaderManager.getLoader(BAKING_LOADER_ID);
        if(cursorLoader == null){
            loaderManager.initLoader(BAKING_LOADER_ID,null,this);
        }else{
            loaderManager.restartLoader(BAKING_LOADER_ID,null,this);
        }
        adapter = new RecipeRecycleAdapter(App.getCurrentActivity(),mRecipeResponses,this);
        fragmentRecipeBinding.rvRecipes.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentRecipeBinding.rvRecipes.setHasFixedSize(true);
        fragmentRecipeBinding.rvRecipes.setAdapter(adapter);
        View view = fragmentRecipeBinding.getRoot();
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {


    }


    @Override
    public void onRecipeItemClick(RecipeResponse recipeResponse) {
        initStepsFragment(recipeResponse);
    }

    private void initStepsFragment(RecipeResponse recipeResponse) {
        StepsFragment stepsFragment = new StepsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT, lay);
        bundle.putParcelable(RECIPE_DATA_KEY, recipeResponse);
        stepsFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(lay, stepsFragment,STEP_FRAG)
                .addToBackStack(null)
                .commit();
    }


    @NonNull
    @Override
    public Loader<Cursor> onCreateLoader(int i, @Nullable Bundle bundle) {
        return new AsyncTaskLoader<Cursor>(App.getCurrentActivity()) {
            Cursor mBakingData = null;

            @Override
            protected void onStartLoading() {
                if(mBakingData != null){
                    deliverResult(mBakingData);
                }else{
                    forceLoad();
                }

            }

            @Nullable
            @Override
            public Cursor loadInBackground() {
                try{
                    return recipePresenter.retrieveRecipesFromDB();
                }catch (Exception ex){
                    ex.printStackTrace();
                    return null;
                }

            }
            public void deliverResult(Cursor data) {
                mBakingData = data;
                super.deliverResult(data);
            }

        };
    }


    @Override
    public void onLoadFinished(@NonNull Loader<Cursor> loader, Cursor cursor) {
        int servings = cursor.getColumnIndex(BakingDbContract.BakingEntry.COLUMN_SERVINGS);
        int ingredients = cursor.getColumnIndex(BakingDbContract.BakingEntry.COLUMN_INGREDIENTS);
        int steps = cursor.getColumnIndex(BakingDbContract.BakingEntry.COLUMN_STEPS);
        int name = cursor.getColumnIndex(BakingDbContract.BakingEntry.COLUMN_NAME);

        while(cursor.moveToNext()){
            String gotServings = cursor.getString(servings);
            String gotIngredients = cursor.getString(ingredients);
            String gotSteps = cursor.getString(steps);
            String gotName = cursor.getString(name);
            RecipeResponse recipeResponse = new RecipeResponse();
            recipeResponse.setName(gotName);
            recipeResponse.setServings(gotServings);
            recipeResponse.setIngredients(DataUtils.parseIngredients(gotIngredients));
            recipeResponse.setSteps(DataUtils.parseSteps(gotSteps));
            mRecipeResponses.add(recipeResponse);
        }

        adapter.swapData();

    }




    @Override
    public void onLoaderReset(@NonNull Loader<Cursor> loader) {

    }


}
