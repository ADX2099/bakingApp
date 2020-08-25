package com.adx2099.bakingapp.ui.steps;


import android.app.ActionBar;
import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

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
import com.adx2099.bakingapp.ui.details.DetailFragment;
import com.adx2099.bakingapp.ui.ingredients.IngredientsFragment;
import com.adx2099.bakingapp.ui.recipe.RecipeFragment;

import java.util.ArrayList;
import java.util.List;

import static com.adx2099.bakingapp.helper.BakingConstants.DETAIL_FRAG;
import static com.adx2099.bakingapp.helper.BakingConstants.INGR_FRAG;
import static com.adx2099.bakingapp.helper.BakingConstants.LAYOUT;
import static com.adx2099.bakingapp.helper.BakingConstants.LIST_STEP_KEY;
import static com.adx2099.bakingapp.helper.BakingConstants.MAIN_FRAG;
import static com.adx2099.bakingapp.helper.BakingConstants.RECIPE_DATA_KEY;
import static com.adx2099.bakingapp.helper.BakingConstants.STEP_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class StepsFragment extends Fragment implements StepsView, IStepItemClickListener, IngredientItemClickListener, View.OnClickListener {
    private int lay;
    private RecipeResponse recipeResponse;
    private FragmentStepsBinding fragmentStepsBinding;
    private StepsPresenter stepsPresenter;
    private List<Steps> mList;
    private StepsRecyclerAdapter adapter;
    ImageView favStar;
    ImageView favStarFull;

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
            String size = String.valueOf(mList.size());
            String id = String.valueOf(mList.get(0).stepId);
            Log.d("ADX2099", "Step Values::" + size + " id: " + id);
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        setUpToolbar();


        fragmentStepsBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_steps, container, false);
        adapter = new StepsRecyclerAdapter(App.getCurrentActivity(),mList,this,this);
        fragmentStepsBinding.rvSteps.setLayoutManager(new LinearLayoutManager(getActivity()));
        fragmentStepsBinding.rvSteps.setHasFixedSize(true);
        fragmentStepsBinding.rvSteps.setAdapter(adapter);
        fragmentStepsBinding.titleIngredients.setOnClickListener(this);


        View view = fragmentStepsBinding.getRoot();
        return view;
    }

    private void setUpToolbar() {
        AppCompatActivity activity = ((AppCompatActivity) App.getCurrentActivity());
        Toolbar myToolbar =  activity.findViewById(R.id.toolbar);
        favStar = myToolbar.findViewById(R.id.fav_icon);
        favStar.setOnClickListener(this);
        favStar.setVisibility(View.VISIBLE);
        if(recipeResponse.getFav()){
            favStarFull = myToolbar.findViewById(R.id.fav_icon_full);
            favStarFull.setVisibility(View.VISIBLE);
            favStarFull.setOnClickListener(this);
            favStar.setVisibility(View.INVISIBLE);
        }

        TextView title = myToolbar.findViewById(R.id.toolbar_subtitle);
        title.setText(recipeResponse.getName());
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


    }

    @Override
    public void onStepItemClick(Steps step) {

        initDetailFragment(step);
    }

    private void initDetailFragment(Steps step) {

        DetailFragment detailFragment = new DetailFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT, lay);
        bundle.putParcelable(STEP_KEY, step);
        bundle.putParcelableArrayList(LIST_STEP_KEY, (ArrayList<? extends Parcelable>) mList);
        detailFragment.setArguments(bundle);
        getFragmentManager().beginTransaction()
                .replace(lay, detailFragment,DETAIL_FRAG)
                .addToBackStack(null)
                .commit();
    }

    @Override
    public void onIngredientTitleClick() {
        initFragmentIngredients();
    }

    public void initFragmentIngredients() {
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

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.titleIngredients:
                 initFragmentIngredients();
                break;
            case R.id.fav_icon:

                break;
        }
    }
}
