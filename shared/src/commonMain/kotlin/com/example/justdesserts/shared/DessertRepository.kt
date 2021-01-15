package com.example.justdesserts.shared


import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import com.example.justdesserts.*
import com.example.justdesserts.shared.cache.Database
import com.example.justdesserts.shared.cache.DatabaseDriverFactory
import com.example.justdesserts.shared.cache.Dessert
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
    val cachedDesserts = database.getDessertsPage(page, size)
    if (cachedDesserts.isNotEmpty()) {
      val info = GetDessertsQuery.Info(count = cachedDesserts.size, next = page + 1, prev = page - 1, pages = 1)
      return GetDessertsQuery.Desserts(results = cachedDesserts, info = info)
    }
    val response = apolloClient.query(
      GetDessertsQuery(
        Input.optional(page),
        Input.optional(size)
      )
    ).execute().single().also {
      database.clearDatabase()
      database.createDesserts(it.data?.desserts)
    }
    return response.data?.desserts
  }

  suspend fun getDessert(dessertId: String): GetDessertQuery.Dessert? {
      val response =
        apolloClient.query(GetDessertQuery(Input.optional(dessertId))).execute().single()
    return response.data?.dessert
  }

  suspend fun newDessert(name: String, description: String, imageUrl: String): NewDessertMutation.NewDessert? {
    val response = apolloClient.mutate(NewDessertMutation(name, description, imageUrl)).execute().single()
    return response.data?.newDessert
  }

  suspend fun updateDessert(dessertId: String, name: String, description: String, imageUrl: String): UpdateDessertMutation.UpdateDessert? {
    val response = apolloClient.mutate(UpdateDessertMutation(dessertId, name, description, imageUrl)).execute().single()
    return response.data?.updateDessert
  }

  suspend fun deleteDessert(dessertId: String): Boolean? {
    val response = apolloClient.mutate(DeleteDessertMutation(dessertId)).execute().single()
    return response.data?.deleteDessert
  }

}