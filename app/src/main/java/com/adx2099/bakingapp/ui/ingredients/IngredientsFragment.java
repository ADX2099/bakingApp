package com.adx2099.bakingapp.ui.ingredients;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.adx2099.bakingapp.App;
import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.adapters.IngredientsRecyclerAdapter;
import com.adx2099.bakingapp.callback.IngredientItemClickListener;
import com.adx2099.bakingapp.databinding.FragmentIngredientsBinding;
import com.adx2099.bakingapp.databinding.FragmentStepsBinding;
import com.adx2099.bakingapp.models.Ingredient;
import com.adx2099.bakingapp.models.RecipeResponse;

import java.util.List;

import static com.adx2099.bakingapp.helper.BakingConstants.LAYOUT;
import static com.adx2099.bakingapp.helper.BakingConstants.RECIPE_DATA_KEY;


public class IngredientsFragment extends Fragment implements IngredientsView, IngredientItemClickListener {
    private int lay;
    private RecipeResponse recipeResponse;
    private FragmentIngredientsBinding fragmentIngredientsBinding;
    private IngredientsPresenter ingredientsPresenter;
    private IngredientsRecyclerAdapter adapter;
    private List<Ingredient> mList;



    public IngredientsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ingredientsPresenter = new IngredientsPresenter(this);
        Bundle bundle = getArguments();
        lay = bundle.getInt(LAYOUT);
        recipeResponse = bundle.getParcelable(RECIPE_DATA_KEY);
        if(recipeResponse != null){
            mList = recipeResponse.getIngredients();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentIngredientsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_ingredients, container, false);
        adapter = new IngredientsRecyclerAdapter(App.getCurrentActivity(),mList,this);
        fragmentIngredientsBinding.rvIngredients.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentIngredientsBinding.rvIngredients.setAdapter(adapter);
        fragmentIngredientsBinding.rvIngredients.setHasFixedSize(true);
        View view = fragmentIngredientsBinding.getRoot();
        return view;
    }


    @Override
    public void onIngredientTitleClick() {

    }
}
