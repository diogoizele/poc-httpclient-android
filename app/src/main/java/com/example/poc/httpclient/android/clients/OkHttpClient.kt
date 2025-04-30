package com.example.poc.httpclient.android.clients

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

object OkHttpClientService {

    private val client = OkHttpClient()

    fun executeRequest(url: String,
                       onFinished: (() -> Unit)? = null) {
        Log.i("OkHttpTest", "Iniciando request para: $url")
        val request = Request.Builder()
            .url(url)
            .build()

        val startTime = System.currentTimeMillis()

        client.newCall(request).enqueue(object : okhttp3.Callback {
            override fun onFailure(call: okhttp3.Call, e: IOException) {
                val endTime = System.currentTimeMillis()
                Log.e("OkHttpTest", "❌ [FAILURE] URL: $url")
                Log.e("OkHttpTest", "⏱ Tempo: ${endTime - startTime}ms")
                Log.e("OkHttpTest", "🧨 Erro: ${e.localizedMessage}")
                onFinished?.invoke()
            }

            override fun onResponse(call: okhttp3.Call, response: okhttp3.Response) {
                val endTime = System.currentTimeMillis()
                val duration = endTime - startTime

                Log.i("OkHttpTest", "✅ [SUCCESS] URL: $url")
                Log.i("OkHttpTest", "⏱ Tempo: ${duration}ms")
                Log.i("OkHttpTest", "📦 Código HTTP: ${response.code()}")
                response.close()
                onFinished?.invoke()
            }
        })
    }
}
