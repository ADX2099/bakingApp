package com.adx2099.bakingapp.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adx2099.bakingapp.App;
import com.adx2099.bakingapp.BR;
import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.callback.IRecipeItemClickListener;
import com.adx2099.bakingapp.databinding.RecipeRowBinding;
import com.adx2099.bakingapp.models.RecipeResponse;

import java.util.List;

public class RecipeRecycleAdapter extends RecyclerView.Adapter<RecipeRecycleAdapter.ViewHolder> {
    private Context context;
    private List<RecipeResponse> recipeResponseList;
    private IRecipeItemClickListener iRecipeItemClickListener;




    public RecipeRecycleAdapter(Context context, List<RecipeResponse> recipeResponseList, IRecipeItemClickListener iRecipeItemClickListener) {
        this.context = context;
        this.recipeResponseList = recipeResponseList;
        this.iRecipeItemClickListener = iRecipeItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflate = LayoutInflater.from(context);
        RecipeRowBinding recipeRowBinding = DataBindingUtil.inflate(inflate,
                R.layout.recipe_row,
                parent,
                false);
        ViewHolder vh = new ViewHolder(recipeRowBinding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        RecipeResponse recipeResponse = recipeResponseList.get(position);
        viewHolder.mRecipeRowBinding.setIRecipeItemClickListener(iRecipeItemClickListener);
        viewHolder.mRecipeRowBinding.setRecipe(recipeResponse);
        viewHolder.mRecipeRowBinding.executePendingBindings();
        viewHolder.checkViews();

    }

    public void swapData(){
        if(recipeResponseList.size() == 0)
            return;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return recipeResponseList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        RecipeRowBinding mRecipeRowBinding;

        public ViewHolder(@NonNull RecipeRowBinding itemView) {
            super(itemView.getRoot());
            mRecipeRowBinding = itemView;
        }

        void checkViews() {
            if (mRecipeRowBinding.tvRecipeName.getText().length() > 0) {
                mRecipeRowBinding.vRecipeLine.setVisibility(View.VISIBLE);
            }
        }

    }
}
