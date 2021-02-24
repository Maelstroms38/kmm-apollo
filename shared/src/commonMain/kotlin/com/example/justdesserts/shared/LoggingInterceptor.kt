package com.example.justdesserts.shared

import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import com.apollographql.apollo.interceptor.ApolloRequest
import com.apollographql.apollo.interceptor.ApolloRequestInterceptor
import com.apollographql.apollo.interceptor.ApolloResponse
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

internal class LoggingInterceptor : ApolloRequestInterceptor {
    @ExperimentalTime
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        val uuid = request.requestUuid
        val operation = request.operation.name().name()
        val variables = request.operation.variables().valueMap().toString()
        val (response, elapsed) = measureTimedValue {
            chain.proceed(request)
        }
        print("Request UUID: $uuid")
        print("Request Name: $operation")
        print("Request Variables: $variables")
        print("Request Time: ${elapsed.inMilliseconds}")
        return response
    }
}