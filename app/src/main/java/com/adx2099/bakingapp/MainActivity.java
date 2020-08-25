package com.adx2099.bakingapp;

import android.graphics.Path;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.FrameLayout;
import android.widget.LinearLayout;


import com.adx2099.bakingapp.ui.recipe.RecipeFragment;

import static com.adx2099.bakingapp.helper.BakingConstants.LAYOUT;
import static com.adx2099.bakingapp.helper.BakingConstants.MAIN_FRAG;


public class MainActivity extends AppCompatActivity {
    Toolbar myToolbar;
    FrameLayout mainLayout;
    private int lay;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        App.setCurrenActivity(this);

    }

    private void initViews() {
        myToolbar = (Toolbar) findViewById(R.id.toolbar);
        mainLayout = (FrameLayout) findViewById(R.id.mainLay);
        lay = mainLayout.getId();
        setUpToolBar();
        initRecipeFragment();
    }

    private void initRecipeFragment() {
        RecipeFragment recipeFragment = new RecipeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt(LAYOUT, lay);
        recipeFragment.setArguments(bundle);
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(mainLayout.getId(), recipeFragment,MAIN_FRAG).commit();

    }

    private void setUpToolBar() {
        setSupportActionBar(myToolbar);
    }

    public void removeFragments(){
        if(App.getCurrentFragment() != null){
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            ft.remove(App.getCurrentFragment()).commit();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }
}
