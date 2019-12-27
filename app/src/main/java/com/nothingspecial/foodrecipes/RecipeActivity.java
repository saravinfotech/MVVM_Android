package com.nothingspecial.foodrecipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.AppCompatImageView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.viewmodel.RecipeDetailViewModel;

import static com.nothingspecial.foodrecipes.util.Constants.RECIPE_INTENT;

public class RecipeActivity extends BaseActivity {

    private static final String TAG = "RecipeActivity";
    private AppCompatImageView recipeImage;
    private TextView recipeTitle, recipeRank;
    private LinearLayout recipeIngredientsContainer;
    private ScrollView scrollView;
    private RecipeDetailViewModel recipeDetailViewModel;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        recipeImage = findViewById(R.id.recipe_image);
        recipeTitle = findViewById(R.id.recipe_title);
        recipeRank = findViewById(R.id.recipe_social_score);
        recipeIngredientsContainer = findViewById(R.id.ingredients_container);
        scrollView = findViewById(R.id.parent);

        recipeDetailViewModel = ViewModelProviders.of(this).get(RecipeDetailViewModel.class);
        showProgressBar(true);
        subscribeObservers();
        getIncomingIntent();
    }

    private void subscribeObservers() {
        recipeDetailViewModel.getRecipeDetailLiveData().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(@Nullable Recipe recipe) {
                if(recipe!=null){
                    Log.d(TAG, "--------------------------------");
                    Log.d(TAG,recipe.title);
                    for(String ingredient : recipe.ingredients){
                        Log.d(TAG,"Ingredients "+ingredient);
                    }
                    initializeRecipeDetailsView(recipe);
                }
            }
        });
    }

    private void initializeRecipeDetailsView(Recipe recipe){
        RequestOptions requestOptions = new RequestOptions()
                .placeholderOf(R.drawable.ic_launcher_background);
        Glide.with(this).setDefaultRequestOptions(requestOptions)
                .load(recipe.image_url)
                .into(recipeImage);

        recipeTitle.setText(recipe.title);
        recipeRank.setText(String.valueOf(Math.round(recipe.social_rank)));
        recipeIngredientsContainer.removeAllViews();
        for(String ingredient : recipe.ingredients){
            TextView textView = new TextView(this);
            textView.setText(ingredient);
            textView.setTextSize(15);
            recipeIngredientsContainer.addView(textView);
        }
        showParent();
        showProgressBar(false);
    }

    private void showParent(){
        scrollView.setVisibility(View.VISIBLE);
    }

    private void getIncomingIntent(){
        if(getIntent().hasExtra(RECIPE_INTENT)){
            Recipe recipe = getIntent().getParcelableExtra(RECIPE_INTENT);
            Log.d(TAG, "Intent passed is "+recipe.title);
            recipeDetailViewModel.getRecipeDetail(recipe.recipe_id);
        }
    }
}
