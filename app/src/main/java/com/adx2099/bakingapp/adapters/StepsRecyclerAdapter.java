package com.adx2099.bakingapp.adapters;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.callback.IStepItemClickListener;

import com.adx2099.bakingapp.callback.IngredientItemClickListener;
import com.adx2099.bakingapp.databinding.IngredientsTitleRowBinding;
import com.adx2099.bakingapp.databinding.StepRowBinding;
import com.adx2099.bakingapp.models.Ingredient;
import com.adx2099.bakingapp.models.Steps;

import java.util.List;

public class StepsRecyclerAdapter extends RecyclerView.Adapter<StepsRecyclerAdapter.ViewHolder> {
    private Context context;
    private List<Steps> stepsList;
    private IStepItemClickListener iStepItemClickListener;
    private IngredientItemClickListener ingredientItemClickListener;
    private static final int VIEW_TYPE_INGREDIENTS = 0;
    private static final int VIEW_TYPE_STEP = 1;





    public StepsRecyclerAdapter(Context context, List<Steps> stepsList, IStepItemClickListener iStepItemClickListener, IngredientItemClickListener ingredientItemClickListener) {
        this.context = context;
        this.stepsList = stepsList;
        this.iStepItemClickListener = iStepItemClickListener;
        this.ingredientItemClickListener = ingredientItemClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        int layoutId;
        LayoutInflater inflate = LayoutInflater.from(context);
        ViewHolder  vh;
        switch (viewType) {
            case VIEW_TYPE_INGREDIENTS:
                layoutId = R.layout.ingredients_title_row;
                IngredientsTitleRowBinding  ingredientsTitleRowBinding = DataBindingUtil.inflate(inflate, layoutId, viewGroup, false);
                vh = new ViewHolder(ingredientsTitleRowBinding);
                break;
            case VIEW_TYPE_STEP:
                layoutId = R.layout.step_row;
                StepRowBinding stepRowBinding = DataBindingUtil.inflate(inflate, layoutId, viewGroup, false);
                vh = new ViewHolder(stepRowBinding);
                break;
            default:
                throw new IllegalArgumentException("Invalid view type, value of " + viewType);
        }


        return vh;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        Steps step = stepsList.get(position);
        step.stepNumber = String.valueOf(position + 1);
        viewHolder.stepRowBinding.setStep(step);
        viewHolder.stepRowBinding.executePendingBindings();
        viewHolder.stepRowBinding.setIStepItemClickListener(iStepItemClickListener);

    }

    public void swapData(){
        if(stepsList.size() == 0)
            return;

        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {

        return stepsList.size();
    }

    @Override
    public int getItemViewType(int position) {
            return VIEW_TYPE_STEP;

    }

    public class ViewHolder extends RecyclerView.ViewHolder   {
        StepRowBinding stepRowBinding;
        IngredientsTitleRowBinding ingredientsTitleRowBinding;

        public ViewHolder(@NonNull StepRowBinding itemView){
            super(itemView.getRoot());
            stepRowBinding = itemView;

        }

        public ViewHolder(@NonNull IngredientsTitleRowBinding itemView){
            super(itemView.getRoot());
            ingredientsTitleRowBinding = itemView;
        }
    }


}
