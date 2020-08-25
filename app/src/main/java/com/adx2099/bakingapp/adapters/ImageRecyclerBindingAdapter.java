package com.adx2099.bakingapp.adapters;

import android.databinding.BindingAdapter;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.adx2099.bakingapp.App;
import com.adx2099.bakingapp.R;
import com.adx2099.bakingapp.helper.BakingConstants;
import com.adx2099.bakingapp.module.GlideApp;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.squareup.picasso.Picasso;

import java.util.Objects;

import jp.wasabeef.blurry.Blurry;

public class ImageRecyclerBindingAdapter {
    @BindingAdapter(value = {"android:src", "pbRecipe", "rlRecipe", "recipeName"})
    public static void bindRecipeImage(final ImageView recipeImage, String image, ProgressBar pbRecipe, final RelativeLayout rlRecipe, String recipeName){
        if (TextUtils.isEmpty(image)){
            Drawable resourceId = null;
            String[] images = App.getCurrentActivity().getResources().getStringArray(R.array.recipeNames);
            int[] recipeIds = {
                    R.drawable.yellow,
                    R.drawable.nutellacake,
                    R.drawable.brownies,
                    R.drawable.cheesecake
            };

            for (int i = 0; i < images.length; i++) {
                if(Objects.equals(recipeName, images[i])){
                    resourceId = ContextCompat.getDrawable(recipeImage.getContext(), recipeIds[i]);
                }
            }
            recipeImage.setBackground(resourceId);

            /*
            Glide.with(recipeImage.getContext())
                    .load(resourceId)
                    .listener(requestListener(pbRecipe))
                    .into(recipeImage);*/


           /*new Handler().postDelayed(new Runnable(){

                @Override
                public void run() {
                    if(recipeImage.getDrawable() != null){
                        Blurry.with(recipeImage.getContext())
                                .radius(BakingConstants.RADIUS_BLUR)
                                .sampling(BakingConstants.SAMPLING_BLUR)
                                .animate(BakingConstants.ANIMATE_BLUR)
                                .async()
                                .capture(rlRecipe)
                                .into(recipeImage);
                    }
                }
            }, BakingConstants.MILLISECOND_TO_BLUR);*/
        }
    }
    /*
    private static RequestListener<Drawable> requestListener(final ProgressBar pbRecipe){
        return new RequestListener<Drawable>() {
            @Override
            public boolean onLoadFailed(@Nullable GlideException e, Object model, Target<Drawable> target, boolean isFirstResource) {
                Log.d("ADX2098", "Se ejecuta Failed");
                pbRecipe.setVisibility(View.GONE);
                return false;
            }

            @Override
            public boolean onResourceReady(Drawable resource, Object model, Target<Drawable> target, DataSource dataSource, boolean isFirstResource) {
                Log.d("ADX2098", "Se ejecuta Ready");
                pbRecipe.setVisibility(View.GONE);
                return false;
            }
        };
    }*/



}
