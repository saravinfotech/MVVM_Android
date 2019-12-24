package com.nothingspecial.foodrecipes.models;

import android.os.Parcel;
import android.os.Parcelable;
import java.util.Arrays;

public class Recipe implements Parcelable {

    public String title;
    public String publisher;
    public String[] ingredients;
    public String recipe_id;
    public String image_url;
    public float social_rank;

    public Recipe(String title, String publisher, String[] ingredients, String recipe_id, String image_url, float social_rank) {
        this.title = title;
        this.publisher = publisher;
        this.ingredients = ingredients;
        this.recipe_id = recipe_id;
        this.image_url = image_url;
        this.social_rank = social_rank;
    }

    public Recipe() {
    }

    protected Recipe(Parcel in) {
        title = in.readString();
        publisher = in.readString();
        ingredients = in.createStringArray();
        recipe_id = in.readString();
        image_url = in.readString();
        social_rank = in.readFloat();
    }

    public static final Creator<Recipe> CREATOR = new Creator<Recipe>() {
        @Override
        public Recipe createFromParcel(Parcel in) {
            return new Recipe(in);
        }

        @Override
        public Recipe[] newArray(int size) {
            return new Recipe[size];
        }
    };

    @Override
    public String toString() {
        return "Recipe{" +
                "title='" + title + '\'' +
                ", publisher='" + publisher + '\'' +
                ", ingredients=" + Arrays.toString(ingredients) +
                ", recipe_id='" + recipe_id + '\'' +
                ", image_url='" + image_url + '\'' +
                ", social_rank=" + social_rank +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(title);
        parcel.writeString(publisher);
        parcel.writeStringArray(ingredients);
        parcel.writeString(recipe_id);
        parcel.writeString(image_url);
        parcel.writeFloat(social_rank);
    }

    /*@SerializedName("ingredients")
    @Expose
    public String[] ingredients = null;
    @SerializedName("image_url")
    @Expose
    public String imageUrl;
    @SerializedName("social_rank")
    @Expose
    public float socialRank;
    @SerializedName("_id")
    @Expose
    public String id;
    @SerializedName("publisher")
    @Expose
    public String publisher;
    @SerializedName("source_url")
    @Expose
    public String sourceUrl;
    @SerializedName("recipe_id")
    @Expose
    public String recipeId;
    @SerializedName("publisher_url")
    @Expose
    public String publisherUrl;
    @SerializedName("title")
    @Expose
    public String title;*/
}
