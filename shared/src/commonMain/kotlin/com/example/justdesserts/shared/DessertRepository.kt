package com.example.justdesserts.shared


import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import com.example.justdesserts.*
import com.example.justdesserts.shared.cache.Database
import com.example.justdesserts.shared.cache.DatabaseDriverFactory
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
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

  @Throws(Exception::class) suspend fun getDesserts(page: Int, size: Int): GetDessertsQuery.Desserts? {
      val response = apolloClient.query(
        GetDessertsQuery(
          Input.optional(page),
          Input.optional(size)
        )
      ).execute().single()
      return response.data?.desserts
  }

  @Throws(Exception::class) suspend fun getDessert(dessertId: String): GetDessertQuery.Dessert? {
    val response = apolloClient.query(GetDessertQuery(Input.optional(dessertId))).execute().single()
    return response.data?.dessert
  }

  @Throws(Exception::class) suspend fun newDessert(name: String, description: String, imageUrl: String): NewDessertMutation.NewDessert? {
    val response = apolloClient.mutate(NewDessertMutation(name, description, imageUrl)).execute().single()
    return response.data?.newDessert
  }

  @Throws(Exception::class) suspend fun updateDessert(dessertId: String, name: String, description: String, imageUrl: String): UpdateDessertMutation.UpdateDessert? {
    val response = apolloClient.mutate(UpdateDessertMutation(dessertId, name, description, imageUrl)).execute().single()
    return response.data?.updateDessert
  }

  @Throws(Exception::class) suspend fun deleteDessert(dessertId: String): Boolean? {
    val response = apolloClient.mutate(DeleteDessertMutation(dessertId)).execute().single()
    return response.data?.deleteDessert
  }

  @Throws(Exception::class) suspend fun saveFavorite(dessertId: String) {
    val dessert = getDessert(dessertId)
    dessert?.let {
      database.saveDessert(it)
    }
  }

  fun removeFavorite(dessertId: String) {
    database.deleteDessert(dessertId)
  }

  fun isFavorite(dessertId: String): Boolean {
    return database.getDessertById(dessertId) != null
  }

  fun getFavoriteDesserts(): List<GetDessertQuery.Dessert> {
    return database.getDesserts()
  }

}