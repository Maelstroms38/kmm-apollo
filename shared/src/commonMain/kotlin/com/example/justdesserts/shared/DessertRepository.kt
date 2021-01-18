package com.example.justdesserts.shared


import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import com.example.justdesserts.*
import com.example.justdesserts.shared.cache.Database
import com.example.justdesserts.shared.cache.DatabaseDriverFactory
import com.example.justdesserts.shared.cache.Dessert
import com.example.justdesserts.shared.cache.DessertDetail
import com.example.justdesserts.shared.cache.Desserts
import com.example.justdesserts.shared.cache.toDessert
import com.example.justdesserts.shared.cache.toDesserts
import com.example.justdesserts.shared.cache.toReview
import kotlinx.coroutines.flow.single

@ApolloExperimental
class DessertRepository(databaseDriverFactory: DatabaseDriverFactory) {
  private val database = Database(databaseDriverFactory)

  companion object {
    private const val GRAPHQL_ENDPOINT = "https://kotlin-graphql.herokuapp.com/graphql"
  }
  private val apolloClient = ApolloClient(
    networkTransport = ApolloHttpNetworkTransport(
      serverUrl = GRAPHQL_ENDPOINT,
      headers = mapOf(
        "Accept" to "application/json",
        "Content-Type" to "application/json",
      )
    )
  )

  @Throws(Exception::class) suspend fun getDesserts(page: Int, size: Int): Desserts? {
      val response = apolloClient.query(
        GetDessertsQuery(
          Input.optional(page),
          Input.optional(size)
        )
      ).execute().single()
      return response.data?.desserts?.toDesserts()
  }

  @Throws(Exception::class) suspend fun getDessert(dessertId: String): DessertDetail? {
    val response = apolloClient.query(GetDessertQuery(Input.optional(dessertId))).execute().single()
    response.data?.dessert?.let { dessert ->
      return DessertDetail(
        dessert = dessert.toDessert(),
        reviews = dessert.reviewsFilterNotNull()?.map {
          it.toReview()
        } ?: emptyList()
      )
    }
    return null
  }

  @Throws(Exception::class) suspend fun newDessert(name: String, description: String, imageUrl: String): Dessert? {
    val response = apolloClient.mutate(NewDessertMutation(name, description, imageUrl)).execute().single()
    return response.data?.newDessert?.toDessert()
  }

  @Throws(Exception::class) suspend fun updateDessert(dessertId: String, name: String, description: String, imageUrl: String): Dessert? {
    val response = apolloClient.mutate(UpdateDessertMutation(dessertId, name, description, imageUrl)).execute().single()
    return response.data?.updateDessert?.toDessert()
  }

  @Throws(Exception::class) suspend fun deleteDessert(dessertId: String): Boolean? {
    val response = apolloClient.mutate(DeleteDessertMutation(dessertId)).execute().single()
    return response.data?.deleteDessert
  }

  @Throws(Exception::class) suspend fun saveFavorite(dessertId: String) {
    val dessert = getDessert(dessertId)
    dessert.let {
      database.saveDessert(it?.dessert!!)
    }
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