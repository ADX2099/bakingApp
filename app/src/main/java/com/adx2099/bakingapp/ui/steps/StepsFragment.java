package com.adx2099.bakingapp.ui.steps;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.models.RecipeResponse;

import static com.adx2099.bakingapp.helper.BakingConstants.LAYOUT;
import static com.adx2099.bakingapp.helper.BakingConstants.RECIPE_DATA_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment implements StepsView {
    private int lay;
    RecipeResponse recipeResponse;

    StepsPresenter stepsPresenter;

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


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_steps, container, false);
    }

}
