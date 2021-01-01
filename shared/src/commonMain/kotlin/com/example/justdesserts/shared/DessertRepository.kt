package com.example.justdesserts.shared


import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import com.example.justdesserts.*
import kotlinx.coroutines.flow.single

@ApolloExperimental
class DessertRepository {
  private val apolloClient = ApolloClient(
    networkTransport = ApolloHttpNetworkTransport(
      serverUrl = "http://localhost:9000/graphql",
      headers = mapOf(
        "Accept" to "application/json",
        "Content-Type" to "application/json",
      )
    )
  )

  suspend fun getDesserts(page: Int, size: Int): GetDessertsQuery.Desserts? {
      val response = apolloClient.query(
        GetDessertsQuery(
          Input.optional(page),
          Input.optional(size)
        )
      ).execute().single()
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