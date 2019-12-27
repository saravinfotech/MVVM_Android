package com.nothingspecial.foodrecipes.request;

import com.nothingspecial.foodrecipes.request.response.RecipeResponse;
import com.nothingspecial.foodrecipes.request.response.RecipeSearchResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface RecipeApi {

    @GET("api/search")
    Call<RecipeSearchResponse> searchRecipe(
      @Query("key")String key,
      @Query("q")String searchString,
      @Query("page")String page
    );

    @GET("api/get")
    Call<RecipeResponse> getRecipeDetail(
        @Query("key") String key,
        @Query("rId") String recipeID
    );
}
