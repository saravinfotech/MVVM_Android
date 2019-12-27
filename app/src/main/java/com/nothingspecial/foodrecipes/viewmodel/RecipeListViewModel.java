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
    private boolean isPerformingQuery;

    public RecipeListViewModel(){
        recipeRepository = RecipeRepository.getInstance();
        isPerformingQuery = false;
    }
    public LiveData<List<Recipe>> getRecipe(){
        return recipeRepository.getRecipe();
    }

    public void searchRecipeAPI(String query, int pageNumber){
        isViewingRecipes = true;
        isPerformingQuery = true;
        recipeRepository.searchRecipeAPI(query,pageNumber);
    }

    public void searchNextPage(){
        if(!isPerformingQuery && isViewingRecipes){
            recipeRepository.searchNextPage();
        }
    }


    public boolean isViewingRecipes() {
        return isViewingRecipes;
    }

    public void setIsViewingRecipes(boolean viewingRecipes) {
        isViewingRecipes = viewingRecipes;
    }

    public boolean onBackPressed(){
        if(isPerformingQuery){
            recipeRepository.cancelRequest();
            isPerformingQuery = false;
        }

        if(isViewingRecipes){
            isViewingRecipes = false;
            return false;
        }
        return true;
    }

    public void setIsPerformingQuery(boolean isPerformingQuery){
        this.isPerformingQuery = isPerformingQuery;
    }

    public boolean isPerformingQuery(){
        return isPerformingQuery;
    }
}
