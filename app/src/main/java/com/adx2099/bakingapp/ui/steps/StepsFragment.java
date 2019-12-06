package com.adx2099.bakingapp.ui.steps;


import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.adx2099.bakingapp.App;
import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.adapters.RecipeRecycleAdapter;
import com.adx2099.bakingapp.adapters.StepsRecyclerAdapter;
import com.adx2099.bakingapp.callback.IStepItemClickListener;
import com.adx2099.bakingapp.callback.IngredientItemClickListener;
import com.adx2099.bakingapp.databinding.FragmentStepsBinding;
import com.adx2099.bakingapp.helper.BakingConstants;
import com.adx2099.bakingapp.models.RecipeResponse;
import com.adx2099.bakingapp.models.Steps;
import com.adx2099.bakingapp.ui.ingredients.IngredientsFragment;
import com.adx2099.bakingapp.ui.recipe.RecipeFragment;

import java.util.List;

import static com.adx2099.bakingapp.helper.BakingConstants.INGR_FRAG;
import static com.adx2099.bakingapp.helper.BakingConstants.LAYOUT;
import static com.adx2099.bakingapp.helper.BakingConstants.MAIN_FRAG;
import static com.adx2099.bakingapp.helper.BakingConstants.RECIPE_DATA_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment implements StepsView, IStepItemClickListener, IngredientItemClickListener {
    private int lay;
    private RecipeResponse recipeResponse;
    private FragmentStepsBinding fragmentStepsBinding;
    private StepsPresenter stepsPresenter;
    private List<Steps> mList;
    private StepsRecyclerAdapter adapter;

    public StepsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        stepsPresenter = new StepsPresenter(this);
        Bundle bundle = getArguments();
        lay = bundle.getInt(LAYOUT);
        recipeResponse = bundle.getParcelable(RECIPE_DATA_KEY);
        if(recipeResponse != null){
            mList = recipeResponse.getSteps();
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        fragmentStepsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_steps, container, false);
        adapter = new StepsRecyclerAdapter(App.getCurrentActivity(),mList,this,this);
        fragmentStepsBinding.rvSteps.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentStepsBinding.rvSteps.setHasFixedSize(true);
        fragmentStepsBinding.rvSteps.setAdapter(adapter);

        View view = fragmentStepsBinding.getRoot();
        return view;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStepItemClick() {

        Log.d("ADX2099", "Click");
    }

    @Override
    public void onIngredientTitleClick() {
        initFragmentIngredients();
    }

    private void initFragmentIngredients() {
        IngredientsFragment ingredientsFragment = new IngredientsFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT, lay);
        bundle.putParcelable(RECIPE_DATA_KEY, recipeResponse);
        ingredientsFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(lay, ingredientsFragment,INGR_FRAG)
                .addToBackStack(null)
                .commit();
    }

}
