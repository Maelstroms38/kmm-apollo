package com.example.justdesserts.shared.cache

import com.example.justdesserts.GetDessertQuery
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = JustDesserts(databaseDriverFactory.createDriver())
    private val dbQuery = database.justDessertsQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllReviews()
            dbQuery.removeAllDesserts()
        }
    }

    internal fun getDesserts(): List<GetDessertQuery.Dessert> {
        return dbQuery.selectAllDesserts(::mapDessert).executeAsList()
    }

    internal fun getDessertById(dessertId: String): GetDessertQuery.Dessert? {
        return dbQuery.selectDessertById(dessertId, ::mapDessert).executeAsOneOrNull()
    }

    internal fun saveDessert(dessert: GetDessertQuery.Dessert) {
        dbQuery.transaction {
            dessert.reviewsFilterNotNull()?.forEach { review ->
                if (getReviewById(review.id) == null) {
                    insertReview(review)
                }
            }
            insertDessert(dessert)
        }
    }
    internal fun deleteDessert(dessertId: String) {
        dbQuery.transaction {
            removeDessert(dessertId)
        }
    }

    private fun getReviewById(reviewId: String): GetDessertQuery.Review? {
        return dbQuery.selectReviewById(reviewId, ::mapReview).executeAsOneOrNull()
    }

    private fun mapDessert(id: String, name: String, description: String, imageUrl: String): GetDessertQuery.Dessert {
        val reviews = dbQuery.selectReviewsByDessertId(id, ::mapReview).executeAsList()
        return GetDessertQuery.Dessert("Dessert", id, name, description, imageUrl, reviews)
    }

    private fun mapReview(id: String, dessertId: String, text: String, rating: Long?): GetDessertQuery.Review {
        return GetDessertQuery.Review("Review", id, dessertId, text, rating?.toInt())
    }

    private fun removeDessert(dessertId: String) {
        dbQuery.removeDessertById(dessertId)
    }

    private fun insertDessert(dessert: GetDessertQuery.Dessert) {
        dbQuery.insertDessert(
            dessert.id,
            dessert.name ?: "",
            dessert.description ?: "",
            dessert.imageUrl ?: ""
        )
    }

    private fun insertReview(review: GetDessertQuery.Review) {
        dbQuery.insertReview(review.id, review.dessertId, review.text, review.rating?.toLong())
    }
}