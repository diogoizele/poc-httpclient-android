package com.example.poc.httpclient.android.clients

import android.util.Log
import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.logging.*
import io.ktor.client.request.*
import io.ktor.client.statement.HttpResponse
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

object KtorClientService {

    private val client = HttpClient {
        install(Logging) {
            logger = Logger.DEFAULT
            level = LogLevel.ALL
        }
        install(ContentNegotiation) {
            json(Json { ignoreUnknownKeys = true })
        }

    }

    @Serializable
    data class Todo(
        val userId: Int,
        val id: Int,
        val title: String,
        val completed: Boolean
    )

    suspend fun executeRequest(url: String, onFinished: (() -> Unit)? = null) {
        Log.i("KtorTest", "Iniciando request para: $url")


        val startTime = System.currentTimeMillis()

        try {
            val response: HttpResponse = client.get(url) {
                headers {
                    append(HttpHeaders.Accept, ContentType.Application.Json)
                }
            }

            val duration = System.currentTimeMillis() - startTime

            Log.i("KtorTest", "✅ [SUCCESS] URL: $url")
            Log.i("KtorTest", "⏱ Tempo: ${duration}ms")

        } catch (e: Exception) {
            val duration = System.currentTimeMillis() - startTime

            Log.e("KtorTest", "❌ [FAILURE] URL: $url")
            Log.e("KtorTest", "⏱ Tempo: ${duration}ms")
            Log.e("KtorTest", "🧨 Erro: ${e.localizedMessage}")
        } finally {
            onFinished?.invoke()
        }
    }
}
