package com.nothingspecial.foodrecipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.request.RecipeApi;
import com.nothingspecial.foodrecipes.request.ServiceGenerator;
import com.nothingspecial.foodrecipes.request.response.RecipeResponse;
import com.nothingspecial.foodrecipes.request.response.RecipeSearchResponse;
import com.nothingspecial.foodrecipes.util.Constants;
import com.nothingspecial.foodrecipes.util.Testing;
import com.nothingspecial.foodrecipes.viewmodel.RecipeListViewModel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeListActivity extends BaseActivity {

    private static final String TAG = "RecipeListActivity";
    private RecipeListViewModel recipeListViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);

        recipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        subscribeObservers();

        findViewById(R.id.button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                testRecipeAPI();
            }
        });
    }

    private void subscribeObservers() {
        recipeListViewModel.getRecipe().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if(recipes!=null) {
                    Testing.printRecipes(recipes,TAG);
                }
            }
        });
    }

    public void searchRecipeAPI(String query, int pageNumber){
        recipeListViewModel.searchRecipeAPI(query,pageNumber);
    }

    public void testRecipeAPI() {
        searchRecipeAPI("chicken",1);
    }
        /*final RecipeApi recipeApi = ServiceGenerator.getRecipeApi();

        Call<RecipeResponse> recipeResponseCall = recipeApi.getRecipe(
          Constants.API_KEY,
                "35382"
        );

        recipeResponseCall.enqueue(new Callback<RecipeResponse>() {
            @Override
            public void onResponse(Call<RecipeResponse> call, Response<RecipeResponse> response) {
                Log.i(TAG,"RecipeResponse "+response.toString());

                if(response.code() == 200){
                    Log.i(TAG,"Recipe Response is "+response.body().toString());
                    RecipeResponse recipeResponse = response.body();
                    Log.i(TAG,"RecipeResponse details"+recipeResponse.getRecipe().toString());
                }
            }

            @Override
            public void onFailure(Call<RecipeResponse> call, Throwable t) {
                Log.i(TAG, "Recipe API call failed"+t.toString());
            }
        });
    }

    public void testSearchAPI(){
        RecipeApi recipeApi = ServiceGenerator.getRecipeApi();

        Call<RecipeSearchResponse> searchResponseCall = recipeApi.searchRecipe(
                Constants.API_KEY,
                "chicken",
                "1"
        );

        searchResponseCall.enqueue(new Callback<RecipeSearchResponse>() {
            @Override
            public void onResponse(Call<RecipeSearchResponse> call, Response<RecipeSearchResponse> response) {
                Log.i(TAG,response.toString());
                if(response.code()==200){
                    Log.i(TAG,"onResponse:"+response.body().toString());
                    List<Recipe> recipes = new ArrayList<Recipe>(response.body().getRecipes());
                    for(Recipe recipe : recipes){
                        Log.i(TAG, "Recipe titles : "+recipe.title);
                    }
                }else{
                    try {
                        Log.i(TAG,"Response error : "+response.errorBody().string());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onFailure(Call<RecipeSearchResponse> call, Throwable t) {
                Log.i(TAG,"Error occured : "+ t.getMessage());
            }
        });
    }*/
}
