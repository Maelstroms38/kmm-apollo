package com.example.justdesserts.shared


import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.api.ApolloExperimental
import com.apollographql.apollo.network.http.ApolloHttpNetworkTransport
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

  suspend fun getDesserts(): List<GetDessertsQuery.Dessert?>? {
    val response = apolloClient.query(GetDessertsQuery()).execute().single()
    return response.data?.desserts
  }

}