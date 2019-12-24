package com.nothingspecial.foodrecipes.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;

import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.repositories.RecipeRepository;

import java.util.List;

public class RecipeListViewModel extends ViewModel {
    private RecipeRepository recipeRepository;

    public RecipeListViewModel(){
        recipeRepository = RecipeRepository.getInstance();
    }
    public LiveData<List<Recipe>> getRecipe(){
        return recipeRepository.getRecipe();
    }

    public void searchRecipeAPI(String query, int pageNumber){
        recipeRepository.searchRecipeAPI(query,pageNumber);
    }
}
