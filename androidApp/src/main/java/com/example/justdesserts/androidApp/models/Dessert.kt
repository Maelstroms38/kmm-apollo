package com.example.justdesserts.androidApp.models


data class Dessert(
    val action: DessertAction = DessertAction.READ,
    val dessertId: String,
    val name: String,
    val description: String,
    val imageUrl: String,
    val reviews: List<Review> = emptyList()
) {
    fun toQueryString(): String {
        return "?id=${this.dessertId}&name=${this.name}&description=${this.description}&imageUrl=${this.imageUrl}"
    }
}