package com.nothingspecial.foodrecipes.util;

import android.util.Log;

import com.nothingspecial.foodrecipes.models.Recipe;

import java.util.List;

public class Testing {

    public static void printRecipes(List<Recipe> recipeList, String TAG){
        for (Recipe recipe : recipeList) {
            Log.i(TAG, recipe.title);
        }
    }
}
