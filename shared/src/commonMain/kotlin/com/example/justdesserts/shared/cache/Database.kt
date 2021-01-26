package com.example.justdesserts.shared.cache

internal class Database(databaseDriverFactory: DatabaseDriverFactory) {
    private val database = JustDesserts(databaseDriverFactory.createDriver())
    private val dbQuery = database.justDessertsQueries

    internal fun clearDatabase() {
        dbQuery.transaction {
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

    internal fun getAuthToken(): String? {
        return dbQuery.selectAuthToken().executeAsOneOrNull()
    }

    internal fun saveAuthToken(payload: String) {
        dbQuery.transaction {
            insertAuthToken(payload)
        }
    }

    internal fun deleteAuthToken() {
        dbQuery.transaction {
            removeAuthToken()
        }
    }

    private fun removeDessert(dessertId: String) {
        dbQuery.removeDessertById(dessertId)
    }

    private fun insertDessert(dessert: Dessert) {
        dbQuery.insertDessert(
            dessert.id,
            dessert.userId,
            dessert.name,
            dessert.description,
            dessert.imageUrl
        )
    }

    private fun insertAuthToken(payload: String) {
        dbQuery.insertAuthToken(payload)
    }

    private fun removeAuthToken() {
        dbQuery.removeAuthToken()
    }
}