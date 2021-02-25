package com.example.justdesserts.shared.logger

import com.apollographql.apollo.api.Operation
import com.apollographql.apollo.interceptor.ApolloInterceptorChain
import com.apollographql.apollo.interceptor.ApolloRequest
import com.apollographql.apollo.interceptor.ApolloRequestInterceptor
import com.apollographql.apollo.interceptor.ApolloResponse
import kotlinx.coroutines.flow.Flow
import kotlin.time.ExperimentalTime
import kotlin.time.measureTimedValue

internal class LoggingInterceptor(private val myLogger: MyLogger) : ApolloRequestInterceptor {
    @ExperimentalTime
    override fun <D : Operation.Data> intercept(
        request: ApolloRequest<D>,
        chain: ApolloInterceptorChain
    ): Flow<ApolloResponse<D>> {
        val uuid = request.requestUuid.toString()
        val operation = request.operation.name().name()
        val variables = request.operation.variables().valueMap().toString()
        val (response, elapsed) = measureTimedValue {
            chain.proceed(request)
        }
        val timeInMillis = elapsed.inMilliseconds.toString()
        val props = mutableMapOf<String, String>()
        props["Request UUID"] = uuid
        props["Request Name"] = operation
        props["Request Variables"] = variables
        props["Response Time"] = timeInMillis
        val propsString = props.toString()
        myLogger.logMessage(propsString)
        return response
    }
}