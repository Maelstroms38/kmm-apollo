package com.example.justdesserts.shared.cache

import com.example.justdesserts.GetDessertQuery

fun GetDessertQuery.Review.toReview() = Review(
    id = id,
    dessertId = dessertId,
    text = text,
    rating = rating?.toLong() ?: 0
)