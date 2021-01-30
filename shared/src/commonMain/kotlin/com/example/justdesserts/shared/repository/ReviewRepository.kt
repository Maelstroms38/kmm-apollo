package com.example.justdesserts.shared.repository

import com.example.justdesserts.DeleteReviewMutation
import com.example.justdesserts.GetReviewQuery
import com.example.justdesserts.NewReviewMutation
import com.example.justdesserts.UpdateReviewMutation
import com.example.justdesserts.shared.ApolloProvider
import com.example.justdesserts.shared.cache.Review
import com.example.justdesserts.shared.cache.toReview
import com.example.justdesserts.type.ReviewInput
import kotlinx.coroutines.flow.single

class ReviewRepository(apolloProvider: ApolloProvider) {
    private val apolloClient = apolloProvider.apolloClient

    @Throws(Exception::class) suspend fun getReview(reviewId: String): Review? {
        val response = apolloClient.query(GetReviewQuery(reviewId)).execute().single()
        return response.data?.getReview?.toReview()
    }

    @Throws(Exception::class) suspend fun newReview(dessertId: String, reviewInput: ReviewInput): Review? {
        val response = apolloClient.mutate(NewReviewMutation(dessertId, reviewInput)).execute().single()
        return response.data?.createReview?.toReview()
    }

    @Throws(Exception::class) suspend fun updateReview(reviewId: String, reviewInput: ReviewInput): Review? {
        val response = apolloClient.mutate(UpdateReviewMutation(reviewId, reviewInput)).execute().single()
        return response.data?.updateReview?.toReview()
    }

    @Throws(Exception::class) suspend fun deleteReview(reviewId: String): Boolean? {
        val response = apolloClient.mutate(DeleteReviewMutation(reviewId)).execute().single()
        return response.data?.deleteReview
    }
}