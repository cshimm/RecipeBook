package com.example.recipebook

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.adapter.RecipeAdapter
import com.example.recipebook.databinding.ActivityMainBinding
import com.example.recipebook.model.Flavor
import com.example.recipebook.model.FlavorViewHolder
import com.example.recipebook.model.Flavors
import com.example.recipebook.model.Item
import com.example.recipebook.model.Recipe
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private val savoryList = mutableListOf<Item>()
    private val sweetList = mutableListOf<Item>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val recyclerView = binding.recyclerView
        val btnAddSavory = binding.btnAddSavory
        val btnAddSweet = binding.btnAddSweet

        sweetList.add(Flavor("Sweet"))
        savoryList.add(Flavor("Savory"))
        updateRecyclerView(recyclerView)

        btnAddSavory.setOnClickListener {
            addRecipe(recyclerView, Flavors.Savory)
        }
        btnAddSweet.setOnClickListener {
            addRecipe(recyclerView, Flavors.Sweet)
        }

        initSwipeToDelete(recyclerView)

    }

    private fun addRecipe(recyclerView: RecyclerView, flavorType: Flavors) {
        val titleText = binding.titleEditText.text
        val descriptionText = binding.descriptionEditText.text
        if (titleText.isEmpty() || descriptionText.isEmpty()) return

        val recipe = Recipe(titleText.toString(), descriptionText.toString(), flavorType)
        when (flavorType) {
            Flavors.Sweet -> sweetList.add(recipe)
            Flavors.Savory -> savoryList.add(recipe)
            else -> {}
        }
        updateRecyclerView(recyclerView)

        titleText.clear()
        descriptionText.clear()
    }

    private fun getFullList() : List<Item> {
        return sweetList + savoryList
    }
    private fun updateRecyclerView(recyclerView: RecyclerView) {
        recyclerView.adapter = RecipeAdapter(getFullList())
    }

    private fun initSwipeToDelete(recyclerView: RecyclerView) {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun getSwipeDirs(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder
            ): Int {
                if (viewHolder is FlavorViewHolder) return 0
                return super.getSwipeDirs(recyclerView, viewHolder)
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val currentList = getFullList() as MutableList<Item>
                val deletedItem: Item = currentList[viewHolder.adapterPosition]

                if (deletedItem is Recipe) {
                    when (deletedItem.flavor) {
                        Flavors.Sweet -> sweetList.remove(deletedItem)
                        Flavors.Savory -> savoryList.remove(deletedItem)
                        else -> {}
                    }
                    updateRecyclerView(recyclerView)

                    Snackbar.make(
                        recyclerView,
                        "Deleted " + deletedItem.title,
                        Snackbar.LENGTH_LONG
                    ).show()
                }
            }
        }).attachToRecyclerView(recyclerView)
    }
}