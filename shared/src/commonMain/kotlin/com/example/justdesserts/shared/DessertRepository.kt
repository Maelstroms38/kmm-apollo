package com.example.justdesserts.shared


import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import com.example.justdesserts.*
import com.example.justdesserts.shared.cache.Database
import com.example.justdesserts.shared.cache.DatabaseDriverFactory
import com.example.justdesserts.shared.cache.Dessert
import com.example.justdesserts.shared.cache.DessertDetail
import com.example.justdesserts.shared.cache.toDessert
import com.example.justdesserts.shared.cache.toReview
import com.example.justdesserts.type.DessertInput
import kotlinx.coroutines.flow.single

@ApolloExperimental
class DessertRepository(databaseDriverFactory: DatabaseDriverFactory) {
  private val database = Database(databaseDriverFactory)

  companion object {
    private const val GRAPHQL_ENDPOINT = "http://localhost:8080/graphql"
  }

  private val token = "database.getAuthToken()"
  private val apolloClient = ApolloClient(
    networkTransport = ApolloHttpNetworkTransport(
      serverUrl = GRAPHQL_ENDPOINT,
      headers = mapOf(
        "Accept" to "application/json",
        "Content-Type" to "application/json",
      )
    )
  )

  @Throws(Exception::class) suspend fun getDesserts(page: Int, size: Int): List<Dessert>? {
      val response = apolloClient.query(
        GetDessertsQuery(
          page,
          size
        )
      ).execute().single()
      return response.data?.desserts?.map { it.toDessert() }
  }

  @Throws(Exception::class) suspend fun getDessert(dessertId: String): DessertDetail? {
    val response = apolloClient.query(GetDessertQuery(dessertId)).execute().single()
    response.data?.dessert?.let { dessert ->
      return DessertDetail(
        dessert = dessert.toDessert(),
        reviews = dessert.reviews.map {
          it.toReview()
        }
      )
    }
    return null
  }

  @Throws(Exception::class) suspend fun newDessert(name: String, description: String, imageUrl: String): Dessert? {
    val response = apolloClient.mutate(NewDessertMutation(DessertInput(name, description, imageUrl))).execute().single()
    return response.data?.createDessert?.toDessert()
  }

  @Throws(Exception::class) suspend fun updateDessert(dessertId: String, name: String, description: String, imageUrl: String): Dessert? {
    val response = apolloClient.mutate(UpdateDessertMutation(dessertId, DessertInput(name, description, imageUrl))).execute().single()
    return response.data?.updateDessert?.toDessert()
  }

  @Throws(Exception::class) suspend fun deleteDessert(dessertId: String): Boolean? {
    val response = apolloClient.mutate(DeleteDessertMutation(dessertId)).execute().single()
    return response.data?.deleteDessert
  }

  fun saveFavorite(dessert: Dessert) {
      database.saveDessert(dessert)
  }

  fun removeFavorite(dessertId: String) {
    database.deleteDessert(dessertId)
  }

  fun isFavorite(dessertId: String): Boolean {
    return database.getDessertById(dessertId) != null
  }

  fun getFavoriteDesserts(): List<Dessert> {
    return database.getDesserts()
  }

}