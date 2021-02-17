package com.example.justdesserts.shared.repository


import com.apollographql.apollo.api.ApolloExperimental
import com.example.justdesserts.*
import com.example.justdesserts.shared.ApolloProvider
import com.example.justdesserts.shared.cache.Dessert
import com.example.justdesserts.shared.cache.DessertDetail
import com.example.justdesserts.shared.cache.Desserts
import com.example.justdesserts.shared.cache.toDessert
import com.example.justdesserts.shared.cache.toDesserts
import com.example.justdesserts.shared.cache.toReview
import com.example.justdesserts.type.DessertInput
import kotlinx.coroutines.flow.single

@ApolloExperimental
class DessertRepository(apolloProvider: ApolloProvider) {
  private val apolloClient = apolloProvider.apolloClient
  private val database = apolloProvider.database

  @Throws(Exception::class) suspend fun getDesserts(page: Int, size: Int): Desserts? {
      val response = apolloClient.query(
        GetDessertsQuery(
          page,
          size
        )
      ).execute().single()
      return response.data?.desserts?.toDesserts()
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

  @Throws(Exception::class) suspend fun newDessert(dessertInput: DessertInput): Dessert? {
    val response = apolloClient.mutate(NewDessertMutation(dessertInput)).execute().single()
    return response.data?.createDessert?.toDessert()
  }

  @Throws(Exception::class) suspend fun updateDessert(dessertId: String, dessertInput: DessertInput): Dessert? {
    val response = apolloClient.mutate(UpdateDessertMutation(dessertId, dessertInput)).execute().single()
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

  fun updateFavorite(dessert: Dessert) {
    database.updateDessert(dessert)
  }

  fun getFavoriteDessert(dessertId: String): Dessert? {
    return database.getDessertById(dessertId)
  }

  fun getFavoriteDesserts(): List<Dessert> {
    return database.getDesserts()
  }

}