package com.example.recipebook.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.R
import com.example.recipebook.model.Flavor
import com.example.recipebook.model.FlavorViewHolder
import com.example.recipebook.model.Item
import com.example.recipebook.model.Recipe
import com.example.recipebook.model.RecipeViewHolder
import com.example.recipebook.model.ViewHolder
import java.lang.IllegalArgumentException

class RecipeAdapter(
    private val dataset: List<Item>

) : RecyclerView.Adapter<ViewHolder>()  {
    override fun getItemViewType(position: Int): Int {
        return when(dataset[position]) {
            is Flavor -> 0
            is Recipe -> 1
            else -> -1
        }
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return when(viewType) {
            0 -> FlavorViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.flavor_item, parent, false))
            1 -> RecipeViewHolder(LayoutInflater.from(parent.context)
                .inflate(R.layout.recipe_item, parent, false))
            else -> { throw IllegalArgumentException("Something went wrong") }
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(dataset[position])
    }

    override fun getItemCount() = dataset.size

}