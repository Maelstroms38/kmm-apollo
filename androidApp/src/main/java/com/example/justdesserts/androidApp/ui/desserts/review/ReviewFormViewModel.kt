package com.example.justdesserts.androidApp.ui.desserts.review

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.cache.Dessert
import com.example.justdesserts.shared.cache.Review
import com.example.justdesserts.shared.repository.ReviewRepository
import com.example.justdesserts.type.DessertInput
import com.example.justdesserts.type.ReviewInput

@ApolloExperimental
class ReviewFormViewModel constructor(private val repository: ReviewRepository): ViewModel() {
    suspend fun getReview(reviewId: String): Review? {
        return repository.getReview(reviewId)
    }
    suspend fun createReview(dessertId: String, review: Review): Review? {
        return repository.newReview(dessertId, ReviewInput(text = review.text, rating = review.rating.toInt()))
    }
    suspend fun updateReview(review: Review): Review? {
        return repository.updateReview(review.id, ReviewInput(text = review.text, rating = review.rating.toInt()))
    }
    suspend fun deleteReview(reviewId: String): Boolean? {
        return repository.deleteReview(reviewId)
    }
}