package com.nothingspecial.foodrecipes.adapters;

import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.nothingspecial.foodrecipes.R;
import com.nothingspecial.foodrecipes.models.Recipe;
import com.nothingspecial.foodrecipes.util.Constants;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class RecipeRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int RECIPE_TYPE = 1;
    private static final int LOADING_TYPE = 2;
    private static final int CATEGORY_TYPE = 3;

    private static final String LOADING_TITLE = "LOADING...";

    private List<Recipe> recipes;
    private OnRecipeListener onRecipeListener;

    public RecipeRecyclerAdapter(OnRecipeListener onRecipeListener) {
        this.onRecipeListener = onRecipeListener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = null;
        switch (i) {
            case RECIPE_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recipe_list_item, viewGroup, false);
                return new RecipeViewHolder(view, onRecipeListener);
            case LOADING_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_loading_list_item, viewGroup, false);
                return new LoadingViewHolder(view);
            case CATEGORY_TYPE:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_category_list_item, viewGroup, false);
                return new CategoryViewHolder(view,onRecipeListener);
            default:
                view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recipe_list_item, viewGroup, false);
                return new RecipeViewHolder(view, onRecipeListener);
        }

    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        int itemViewType = getItemViewType(i);
        if(itemViewType == RECIPE_TYPE) {
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);

            Glide.with(viewHolder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(recipes.get(i).image_url)
                    .into(((RecipeViewHolder) viewHolder).imageView);
            ((RecipeViewHolder) viewHolder).title.setText(recipes.get(i).title);
            ((RecipeViewHolder) viewHolder).publisher.setText(recipes.get(i).publisher);
            ((RecipeViewHolder) viewHolder).socialScore.setText(String.valueOf(Math.round(recipes.get(i).social_rank)));
        }else if(itemViewType == CATEGORY_TYPE){
            RequestOptions requestOptions = new RequestOptions()
                    .placeholder(R.drawable.ic_launcher_background);
            Uri path = Uri.parse("android.resource://com.nothingspecial.foodrecipes/drawable/"+recipes.get(i).image_url);
            Glide.with(viewHolder.itemView.getContext())
                    .setDefaultRequestOptions(requestOptions)
                    .load(path)
                    .into(((CategoryViewHolder) viewHolder).categoryImage);
            ((CategoryViewHolder) viewHolder).categoryTitle.setText(recipes.get(i).title);

        }
    }

    @Override
    public int getItemViewType(int position) {
        if(recipes.get(position).social_rank == -1){
            return CATEGORY_TYPE;
        } else if(recipes.get(position).title.equals(LOADING_TITLE)){
            return LOADING_TYPE;
        }else{
            return RECIPE_TYPE;
        }
    }

    public void displaySearchCategories(){
        List<Recipe> categories = new ArrayList<>();
        for(int i=0;i< Constants.DEFAULT_SEARCH_CATEGORIES.length;i++){
            Recipe recipe = new Recipe();
            recipe.title = Constants.DEFAULT_SEARCH_CATEGORIES[i];
            recipe.image_url = Constants.DEFAULT_SEARCH_CATEGORY_IMAGES[i];
            recipe.social_rank = -1;
            categories.add(recipe);
            recipes = categories;
            notifyDataSetChanged();
        }

    }

    public void displayLoading(){
        if(!isLoading()){
            Recipe recipe = new Recipe();
            recipe.title = LOADING_TITLE;
            List<Recipe> loadingList = new ArrayList<>();
            loadingList.add(recipe);
            recipes = loadingList;
            notifyDataSetChanged();
        }
    }

    private boolean isLoading(){
        if(recipes!=null) {
            if (recipes.size() > 0) {
                if (recipes.get(recipes.size() - 1).title.equals(LOADING_TITLE)) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public int getItemCount() {
        if (recipes != null) {
            return recipes.size();
        }
        return 0;
    }

    public void setRecipes(List<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }
}
