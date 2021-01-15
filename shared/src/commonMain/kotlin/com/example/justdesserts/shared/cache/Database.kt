package com.example.justdesserts.shared.cache

import com.example.justdesserts.GetDessertsQuery

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = JustDesserts(databaseDriverFactory.createDriver())
    private val dbQuery = database.justDessertsQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
            dbQuery.removeAllDesserts()
            dbQuery.removeAllRatings()
        }
    }

    internal fun getDessertsPage(page: Int, size: Int): List<GetDessertsQuery.Result> {
        return dbQuery.selectDessertsPage(page.toLong(), size.toLong(), ::mapDessertsPage).executeAsList()
    }

    private fun mapDessertsPage(
        id: String,
        name: String?,
        description: String?,
        imageUrl: String?
    ): GetDessertsQuery.Result {
        return GetDessertsQuery.Result("Dessert", id, name, description, imageUrl)
    }

    internal fun createDesserts(desserts: GetDessertsQuery.Desserts?) {
        dbQuery.transaction {
            desserts?.results?.forEach { result ->
                result?.let { dessert ->
                    val reviews = dbQuery.selectReviewsByDessertId(dessert.id).executeAsList()
                    reviews.forEach { review ->
                        val reviewQuery = dbQuery.selectReviewById(review.id).executeAsOneOrNull()
                        if (reviewQuery == null) {
                            insertReview(review)
                        }
                    }
                    insertDessert(dessert)
                }
            }
        }
    }

    private fun insertDessert(dessert: GetDessertsQuery.Result) {
        dbQuery.insertDessert(dessert.id, dessert.name, dessert.description, dessert.imageUrl)
    }

    private fun insertReview(review: Review) {
        dbQuery.insertReview(review.id, review.dessertId, review.text, review.rating)
    }
}