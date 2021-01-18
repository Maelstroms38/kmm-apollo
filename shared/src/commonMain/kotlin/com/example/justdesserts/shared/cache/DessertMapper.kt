package com.example.justdesserts.shared.cache

import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.GetDessertsQuery
import com.example.justdesserts.NewDessertMutation
import com.example.justdesserts.UpdateDessertMutation

data class Desserts(val info: GetDessertsQuery.Info?, val desserts: List<Dessert>)
data class DessertDetail(val dessert: Dessert, val reviews: List<Review>)

fun GetDessertsQuery.Desserts.toDesserts() = Desserts(
    info = info,
    desserts = resultsFilterNotNull()?.map {
        it.toDessert()
    } ?: emptyList()
)

fun GetDessertsQuery.Result.toDessert() = Dessert(
    id = id,
    name = name ?: "",
    description = description ?: "",
    imageUrl = imageUrl ?: ""
)

fun GetDessertQuery.Dessert.toDessert() = Dessert(
    id = id,
    name = name ?: "",
    description = description ?: "",
    imageUrl = imageUrl ?: ""
)

fun NewDessertMutation.NewDessert.toDessert() = Dessert(
    id = id,
    name = name ?: "",
    description = description ?: "",
    imageUrl = imageUrl ?: ""
)

fun UpdateDessertMutation.UpdateDessert.toDessert() = Dessert(
    id = id,
    name = name ?: "",
    description = description ?: "",
    imageUrl = imageUrl ?: ""
)

fun Dessert.toQueryString(): String {
    return "?id=${this.id}&name=${this.name}&description=${this.description}&imageUrl=${this.imageUrl}"
}