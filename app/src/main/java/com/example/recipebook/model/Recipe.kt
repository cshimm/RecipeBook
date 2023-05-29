package com.example.recipebook.model

data class Recipe(
    override val title: String,
    val description: String,
    val flavor: Flavors
    ) : Item (title)
