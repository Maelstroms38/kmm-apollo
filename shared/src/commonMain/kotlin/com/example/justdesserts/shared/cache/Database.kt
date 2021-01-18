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

    internal fun getDesserts(): List<Dessert> {
        return dbQuery.selectAllDesserts().executeAsList()
    }

    internal fun getDessertById(dessertId: String): Dessert? {
        return dbQuery.selectDessertById(dessertId).executeAsOneOrNull()
    }

    internal fun saveDessert(dessert: Dessert) {
        dbQuery.transaction {
            insertDessert(dessert)
        }
    }
    internal fun deleteDessert(dessertId: String) {
        dbQuery.transaction {
            removeDessert(dessertId)
        }
    }

    private fun removeDessert(dessertId: String) {
        dbQuery.removeDessertById(dessertId)
    }

    private fun insertDessert(dessert: Dessert) {
        dbQuery.insertDessert(
            dessert.id,
            dessert.name,
            dessert.description,
            dessert.imageUrl
        )
    }

    private fun insertReview(review: Review) {
        dbQuery.insertReview(review.id, review.dessertId, review.text, review.rating)
    }
}