package com.example.poc.httpclient.android.clients

import android.util.Log
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread

object HttpURLConnectionService {

    fun executeRequest(url: String, onFinished: (() -> Unit)? = null) {
        Log.i("HttpURLConnectionTest", "Iniciando request para: $url")

        thread {
            val startTime = System.currentTimeMillis()

            var connection: HttpURLConnection? = null
            try {
                val urlObject = URL(url)
                connection = urlObject.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connect()

                val endTime = System.currentTimeMillis()
                val duration = endTime - startTime
                val responseCode = connection.responseCode

                Log.i("HttpURLConnectionTest", "✅ [SUCCESS] URL: $url")
                Log.i("HttpURLConnectionTest", "⏱ Tempo: ${duration}ms")
                Log.i("HttpURLConnectionTest", "📦 Código HTTP: $responseCode")

            } catch (e: IOException) {
                val endTime = System.currentTimeMillis()
                Log.e("HttpURLConnectionTest", "❌ [FAILURE] URL: $url")
                Log.e("HttpURLConnectionTest", "⏱ Tempo: ${endTime - startTime}ms")
                Log.e("HttpURLConnectionTest", "🧨 Erro: ${e.localizedMessage}")
            } finally {
                connection?.disconnect()
                onFinished?.invoke()
            }
        }
    }
}
