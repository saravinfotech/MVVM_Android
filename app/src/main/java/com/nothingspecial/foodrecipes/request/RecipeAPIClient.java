package com.nothingspecial.foodrecipes.request;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.util.Log;

import com.nothingspecial.foodrecipes.AppExecuters;
import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.request.response.RecipeResponse;
import com.nothingspecial.foodrecipes.request.response.RecipeSearchResponse;
import com.nothingspecial.foodrecipes.util.Constants;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Response;

import static com.nothingspecial.foodrecipes.util.Constants.NETWORK_TIMEOUT;

public class RecipeAPIClient {
    private static RecipeAPIClient instance;
    private MutableLiveData<List<Recipe>> recipeListLiveData;
    private RetrieveRecipesRunnable retrieveRecipesRunnable;

    private RecipeAPIClient(){
        recipeListLiveData = new MutableLiveData<>();
    }

    public static RecipeAPIClient getInstance(){
        if(instance == null){
            instance = new RecipeAPIClient();
        }
        return instance;
    }

    public LiveData<List<Recipe>> getRecipe(){
        return recipeListLiveData;
    }

    public void searchRecipeAPI(String query, int pageNumber){

        if(retrieveRecipesRunnable !=null){
            retrieveRecipesRunnable = null;
        }
        retrieveRecipesRunnable = new RetrieveRecipesRunnable(query,pageNumber);
        final Future futureHandler = AppExecuters.getInstance().getNetworkIO().submit(retrieveRecipesRunnable);

        AppExecuters.getInstance().getNetworkIO().schedule(new Runnable() {
            @Override
            public void run() {
                futureHandler.cancel(true);
            }
        }, NETWORK_TIMEOUT,TimeUnit.MILLISECONDS);
    }


    private class RetrieveRecipesRunnable implements Runnable{
        private static final String TAG = "RetrieveRecipesRunnable";
        private String query;
        private int pageNumber;
        private boolean cancelRequest;

        public RetrieveRecipesRunnable(String query, int pageNumber) {
            this.query = query;
            this.pageNumber = pageNumber;
        }

        @Override
        public void run() {
            try {
                Response response = getRecipes(query, pageNumber).execute();
                if(cancelRequest){
                    return;
                }
                if(response.code() == 200){
                    List<Recipe> list = new ArrayList<>(((RecipeSearchResponse)response.body()).getRecipes());
                    if(pageNumber == 1){
                        recipeListLiveData.postValue(list);
                    }
                    else{
                        List<Recipe> currentRecipes = recipeListLiveData.getValue();
                        currentRecipes.addAll(list);
                        recipeListLiveData.postValue(currentRecipes);
                    }
                }
                else{
                    String error = response.errorBody().string();
                    Log.e(TAG, "run: " + error );
                    recipeListLiveData.postValue(null);
                }
            } catch (IOException e) {
                e.printStackTrace();
                recipeListLiveData.postValue(null);
            }

        }

        private Call<RecipeSearchResponse> getRecipes(String query, int pageNumber){
            return ServiceGenerator.getRecipeApi().searchRecipe(
                    Constants.API_KEY,
                    query,
                    String.valueOf(pageNumber)
            );
        }

        private void cancelRequest(){
            Log.d(TAG,"Request Cancelled");
            cancelRequest = true;
        }
    }
}
