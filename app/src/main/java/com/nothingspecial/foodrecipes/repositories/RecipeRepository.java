package com.nothingspecial.foodrecipes.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.request.RecipeAPIClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeAPIClient recipeAPIClient;

    private RecipeRepository(){
        recipeAPIClient = RecipeAPIClient.getInstance();
    }

    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    public LiveData<List<Recipe>> getRecipe(){
        return recipeAPIClient.getRecipe();
    }

    public void searchRecipeAPI(String query, int pageNumber){
        if(pageNumber ==0){
            pageNumber = 1;
        }
        recipeAPIClient.searchRecipeAPI(query,pageNumber);
    }

    public void cancelRequest(){
        recipeAPIClient.cancelRequest();
    }
}
