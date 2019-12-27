package com.nothingspecial.foodrecipes.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.repositories.RecipeRepository;

public class RecipeDetailViewModel extends ViewModel {
    private RecipeRepository recipeRepository;

    public RecipeDetailViewModel(){
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<Recipe> getRecipeDetailLiveData(){
        return recipeRepository.getRecipeDetailLiveData();
    }

    public void getRecipeDetail(String recipeID){
        recipeRepository.getRecipeDetail(recipeID);
    }
}
