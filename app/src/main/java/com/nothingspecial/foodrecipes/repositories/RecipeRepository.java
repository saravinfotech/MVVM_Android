package com.nothingspecial.foodrecipes.repositories;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;

import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.request.RecipeAPIClient;

import java.util.List;

public class RecipeRepository {

    private static RecipeRepository instance;
    private RecipeAPIClient recipeAPIClient;
    private String query;
    private int pageNumber;
    private String recipeID;
    private MutableLiveData<Boolean> isQueryExhausted = new MutableLiveData<>();
    private MediatorLiveData<List<Recipe>> recipesMediatorLiveData = new MediatorLiveData<>();

    private RecipeRepository(){
        recipeAPIClient = RecipeAPIClient.getInstance();
        initMediators();
    }

    public static RecipeRepository getInstance(){
        if(instance == null){
            instance = new RecipeRepository();
        }
        return instance;
    }

    private void initMediators(){
        LiveData<List<Recipe>> recipeListAPISource = recipeAPIClient.getRecipe();
        recipesMediatorLiveData.addSource(recipeListAPISource, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipeList) {
                if(recipeList!=null){
                    recipesMediatorLiveData.setValue(recipeList);
                    doneQuery(recipeList);
                }else{
                    //search database cache , part of 2nd video series not relevant right now
                    doneQuery(null);
                }
            }
        });
    }

    public LiveData<Boolean> isQueryExhaustedLiveData(){
        return isQueryExhausted;
    }
    private void doneQuery(List<Recipe> list){
        if(list!=null){
            if(list.size()<30){
                isQueryExhausted.setValue(true);
            }
        }else{
            isQueryExhausted.setValue(true);
        }
    }
    public LiveData<List<Recipe>> getRecipe(){
       // return recipeAPIClient.getRecipe();
        return recipesMediatorLiveData;
    }

    public LiveData<Recipe> getRecipeDetailLiveData(){
        return recipeAPIClient.getRecipeDetailLiveData();
    }
    public LiveData<Boolean> getIsRecipeRequestTimedOut(){
        return recipeAPIClient.getIsRecipeRequestTimedOut();
    }

    public void searchRecipeAPI(String query, int pageNumber){
        if(pageNumber ==0){
            pageNumber = 1;
        }
        this.query = query;
        this.pageNumber = pageNumber;
        isQueryExhausted.setValue(false);
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
