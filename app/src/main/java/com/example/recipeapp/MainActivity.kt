package com.example.recipeapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var list = ArrayList<RecipesItem>()
    lateinit var recyclerView: RecyclerView
    lateinit var rvAdapter: RVAdapter

    lateinit var titleEdit: EditText
    lateinit var authorEdit: EditText
    lateinit var ingredientsEdit: EditText
    lateinit var instructionsEdit: EditText

    lateinit var addButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.RV)
        rvAdapter = RVAdapter(list)
        recyclerView.adapter = rvAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

        apiInterface?.getRecipes()?.enqueue(object: Callback<Recipes> {
            override fun onResponse(call: Call<Recipes>, response: Response<Recipes>) {
                list.addAll(response.body()!!)

                rvAdapter.notifyDataSetChanged()
            }
            override fun onFailure(call: Call<Recipes>, t: Throwable) {
                Log.d("MAIN", "ISSUE")
            }

        })

        titleEdit = findViewById(R.id.editTextTitle)
        authorEdit = findViewById(R.id.editTextAuthor)
        ingredientsEdit = findViewById(R.id.editTextIngredients)
        instructionsEdit = findViewById(R.id.editTextInstructions)
        addButton = findViewById(R.id.addButton)
        addButton.setOnClickListener{

            var title = titleEdit.text.toString()
            var author = authorEdit.text.toString()
            var ingredients = ingredientsEdit.text.toString()
            var instructions = instructionsEdit.text.toString()

            var recipe = RecipesItem(pk = 0, title = title, author = author, ingredients = ingredients, instructions = instructions)

            apiInterface?.addRecipe(recipe)?.enqueue(object : Callback<RecipesItem> {
                override fun onResponse(call: Call<RecipesItem>, response: Response<RecipesItem>) {
                    Toast.makeText(this@MainActivity, "Recipe Added", Toast.LENGTH_SHORT).show()
                    rvAdapter.notifyDataSetChanged()
                }

                override fun onFailure(call: Call<RecipesItem>, t: Throwable) {
                    Toast.makeText(this@MainActivity, "Something went wrong", Toast.LENGTH_SHORT).show()
                }

            })
        }
    }
}