package com.nothingspecial.foodrecipes.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository recipeRepository;
    private boolean isViewingRecipes;

    public RecipeListViewModel(){
        recipeRepository = RecipeRepository.getInstance();
        isViewingRecipes = false;
    }
    public LiveData<List<Recipe>> getRecipe(){
        return recipeRepository.getRecipe();
    }

    public void searchRecipeAPI(String query, int pageNumber){
        isViewingRecipes = true;
        recipeRepository.searchRecipeAPI(query,pageNumber);
    }

    public boolean isViewingRecipes() {
        return isViewingRecipes;
    }

    public void setIsViewingRecipes(boolean viewingRecipes) {
        isViewingRecipes = viewingRecipes;
    }
}
