package com.example.justdesserts.shared


import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.api.Input
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
import com.example.justdesserts.GetDessertQuery
import com.example.justdesserts.GetDessertsQuery
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

}