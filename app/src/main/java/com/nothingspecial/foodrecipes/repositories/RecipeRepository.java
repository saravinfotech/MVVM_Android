package com.nothingspecial.foodrecipes.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.request.RecipeAPIClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeAPIClient recipeAPIClient;
    private String query;
    private int pageNumber;
    private String recipeID;

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

    public LiveData<Recipe> getRecipeDetailLiveData(){
        return recipeAPIClient.getRecipeDetailLiveData();
    }

    public void searchRecipeAPI(String query, int pageNumber){
        if(pageNumber ==0){
            pageNumber = 1;
        }
        this.query = query;
        this.pageNumber = pageNumber;
        recipeAPIClient.searchRecipeAPI(query,pageNumber);
    }

    public void getRecipeDetail(String recipeID){
        this.recipeID = recipeID;
        recipeAPIClient.getRecipeDetailAPI(recipeID);
    }

    public void searchNextPage(){
        searchRecipeAPI(query, pageNumber+1);
    }

    public void cancelRequest(){
        recipeAPIClient.cancelRequest();
    }
}
