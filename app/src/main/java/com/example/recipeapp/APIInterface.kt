package com.example.recipeapp

import com.example.recipeapp.Recipes
import com.example.recipeapp.RecipesItem
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface APIInterface {
    @GET("?format=json")
    fun getRecipes(): Call<Recipes>

    @POST("?format=json")
    fun addRecipe(@Body recipeData: RecipesItem): Call<RecipesItem>

}