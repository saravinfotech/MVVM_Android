package com.nothingspecial.foodrecipes.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.repositories.RecipeRepository;

public class RecipeDetailViewModel extends ViewModel {
    private RecipeRepository recipeRepository;
    private boolean didRetrieveRecipe;
    String recipeID;

    public RecipeDetailViewModel(){
        recipeRepository = RecipeRepository.getInstance();
    }

    public LiveData<Recipe> getRecipeDetailLiveData(){
        return recipeRepository.getRecipeDetailLiveData();
    }

    public void getRecipeDetail(String recipeID){
        this.recipeID = recipeID;
        recipeRepository.getRecipeDetail(recipeID);
    }

    public String getRecipeID() {
        return recipeID;
    }

    public LiveData<Boolean> getIsRecipeRequestTimedOut(){
        return recipeRepository.getIsRecipeRequestTimedOut();
    }

    public boolean isDidRetrieveRecipe() {
        return didRetrieveRecipe;
    }

    public void setDidRetrieveRecipe(boolean didRetrieveRecipe) {
        this.didRetrieveRecipe = didRetrieveRecipe;
    }
}
