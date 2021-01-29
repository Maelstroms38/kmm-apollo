package com.example.justdesserts.androidApp.ui.desserts.review

import androidx.lifecycle.ViewModel
import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.shared.repository.ReviewRepository

@ApolloExperimental
class ReviewDetailViewModel constructor(private val repository: ReviewRepository): ViewModel() {
    // TODO: CRUD Reviews
}