package com.nothingspecial.foodrecipes.request;

import com.nothingspecial.foodrecipes.util.Constants;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ServiceGenerator {

    private static Retrofit.Builder retrofitBuild = new Retrofit.Builder()
            .baseUrl(Constants.BASE_URL)
            .client(addRetrofitLogger())
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuild.build();

    private static RecipeApi recipeApi = retrofit.create(RecipeApi.class);

    private static OkHttpClient addRetrofitLogger(){
        HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor();
        interceptor.level(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient client = new OkHttpClient.Builder().addInterceptor(interceptor).build();
        return client;
    }

    public static RecipeApi getRecipeApi(){
        return recipeApi;
    }
}
