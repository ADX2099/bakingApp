package com.adx2099.bakingapp.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.callback.IStepItemClickListener;
import com.adx2099.bakingapp.callback.IngredientItemClickListener;
import com.adx2099.bakingapp.databinding.IngredientsRowBinding;
import com.adx2099.bakingapp.models.Ingredient;

import java.util.List;

public class IngredientsRecyclerAdapter extends RecyclerView.Adapter<IngredientsRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Ingredient> ingredientList;
    private IngredientItemClickListener ingredientItemClickListener;


    public IngredientsRecyclerAdapter(Context context, List<Ingredient> ingredientList, IngredientItemClickListener ingredientItemClickListener) {
        this.context = context;
        this.ingredientList = ingredientList;
        this.ingredientItemClickListener = ingredientItemClickListener;
    }

    @NonNull
    @Override
    public IngredientsRecyclerAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        LayoutInflater inflate = LayoutInflater.from(context);
        IngredientsRowBinding  ingredientsRowBinding = DataBindingUtil.inflate(inflate, R.layout.ingredients_row, viewGroup, false);
        ViewHolder vh = new ViewHolder(ingredientsRowBinding);
        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientsRecyclerAdapter.ViewHolder viewHolder, int position) {
        Ingredient ingredient = ingredientList.get(position);
        viewHolder.ingredientsRowBinding.setIngredients(ingredient);

//        viewHolder.ingredientsRowBinding.setIngredientItemClickListener(ingredientItemClickListener);
        viewHolder.ingredientsRowBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return ingredientList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        IngredientsRowBinding ingredientsRowBinding;

        public ViewHolder(@NonNull IngredientsRowBinding itemView) {
            super(itemView.getRoot());
            ingredientsRowBinding = itemView;
        }
    }
}
