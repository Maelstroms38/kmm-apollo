package com.example.justdesserts.shared.cache

import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.GetReviewQuery
import com.example.justdesserts.NewReviewMutation
import com.example.justdesserts.UpdateReviewMutation

fun GetReviewQuery.GetReview.toReview() = Review(
    id = id,
    userId = userId,
    dessertId = dessertId,
    text = text,
    rating = rating.toLong()
)

fun GetDessertQuery.Review.toReview() = Review(
    id = id,
    userId = userId,
    dessertId = dessertId,
    text = text,
    rating = rating.toLong()
)

fun NewReviewMutation.CreateReview.toReview() = Review(
    id = id,
    userId = userId,
    dessertId = dessertId,
    text = text,
    rating = rating.toLong()
)


fun UpdateReviewMutation.UpdateReview.toReview() = Review(
    id = id,
    userId = userId,
    dessertId = dessertId,
    text = text,
    rating = rating.toLong()
)