package com.example.recipebook.model

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.recipebook.R

class FlavorViewHolder(view: View) : ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.flavor_title)
    override fun setData(item: Item) {
        title.text = (item as Flavor).title
    }
}

class RecipeViewHolder(view: View) : ViewHolder(view) {
    private val title: TextView = view.findViewById(R.id.item_title)
    private val description: TextView = view.findViewById(R.id.item_description)
    override fun setData(item: Item) {
        title.text = (item as Recipe).title
        description.text = item.description
    }
}

abstract class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    abstract fun setData(item: Item)
}
