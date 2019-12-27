package com.nothingspecial.foodrecipes;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.nothingspecial.foodrecipes.adapters.OnRecipeListener;
import com.nothingspecial.foodrecipes.adapters.RecipeRecyclerAdapter;
import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.util.Constants;
import com.nothingspecial.foodrecipes.util.Testing;
import com.nothingspecial.foodrecipes.util.VerticalSpacingItemDecorator;
import com.nothingspecial.foodrecipes.viewmodel.RecipeListViewModel;

import java.util.List;

public class RecipeListActivity extends BaseActivity implements OnRecipeListener {

    private static final String TAG = "RecipeListActivity";
    private RecipeListViewModel recipeListViewModel;
    private RecyclerView recyclerView;
    private RecipeRecyclerAdapter recyclerAdapter;
    private SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_list);
        recyclerView = findViewById(R.id.recipe_list);
        searchView = findViewById(R.id.search_view);
        recipeListViewModel = ViewModelProviders.of(this).get(RecipeListViewModel.class);
        initRecyclerView();
        subscribeObservers();
        //testRecipeAPI();
        initSearchView();
        if (!recipeListViewModel.isViewingRecipes()) {
            displaySearchCategories();
        }
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));
    }

    private void subscribeObservers() {
        recipeListViewModel.getRecipe().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(@Nullable List<Recipe> recipes) {
                if (recipes != null) {
                    if (recipeListViewModel.isViewingRecipes()) {
                        Testing.printRecipes(recipes, TAG);
                        recyclerAdapter.setRecipes(recipes);
                        recipeListViewModel.setIsPerformingQuery(false);
                    }
                }
            }
        });

        recipeListViewModel.isQueryExhausted().observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(@Nullable Boolean aBoolean) {
                if(aBoolean){
                    Log.d(TAG,"***********************Query exhausted");
                }
            }
        });
    }

    private void initRecyclerView() {
        recyclerAdapter = new RecipeRecyclerAdapter(this);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(30);
        recyclerView.addItemDecoration(itemDecorator);
        recyclerView.setAdapter(recyclerAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(@NonNull RecyclerView recyclerView, int newState) {
                if(!recyclerView.canScrollVertically(1)){
                    //search next page
                    recipeListViewModel.searchNextPage();
                }
            }
        });
    }

    private void initSearchView() {
        searchView = findViewById(R.id.search_view);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                searchView.clearFocus();
                recyclerAdapter.displayLoading();
                recipeListViewModel.searchRecipeAPI(s, 1);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String s) {
                return false;
            }
        });
    }

    public void searchRecipeAPI(String query, int pageNumber) {
        recipeListViewModel.searchRecipeAPI(query, pageNumber);
    }

    public void testRecipeAPI() {
        searchRecipeAPI("chicken", 1);
    }

    @Override
    public void onRecipeClick(int position) {
        Intent intent = new Intent(this,RecipeActivity.class);
        intent.putExtra(Constants.RECIPE_INTENT,recyclerAdapter.getSelectedRecipe(position));
        startActivity(intent);
    }

    @Override
    public void onCategoryClick(String category) {
        recyclerAdapter.displayLoading();
        recipeListViewModel.searchRecipeAPI(category, 1);
        searchView.clearFocus();
    }

    private void displaySearchCategories() {
        recipeListViewModel.setIsViewingRecipes(false);
        recyclerAdapter.displaySearchCategories();
    }

    @Override
    public void onBackPressed() {
        if (recipeListViewModel.onBackPressed()) {
            super.onBackPressed();
        } else {
            displaySearchCategories();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.recipe_search_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_categories) {
            displaySearchCategories();
        }
        return super.onOptionsItemSelected(item);
    }
}
