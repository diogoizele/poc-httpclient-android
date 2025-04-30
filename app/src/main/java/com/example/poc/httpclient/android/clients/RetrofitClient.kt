package com.example.poc.httpclient.android.clients

import android.util.Log
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET

interface ApiService {
    @GET("todos/1")
    fun getTodo(): Call<Todo>
}

data class Todo(
    val userId: Int,
    val id: Int,
    val title: String,
    val completed: Boolean
)

object RetrofitClient {
    private const val BASE_URL = "https://jsonplaceholder.typicode.com/"

    private val retrofit: Retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    val apiService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }

    fun executeRequest(call: Call<Todo>, onFinished: (() -> Unit)? = null) {
        Log.i("RetrofitTest", "Iniciando request")

        val startTime = System.currentTimeMillis()

        call.enqueue(object : Callback<Todo> {
            override fun onResponse(call: Call<Todo>, response: Response<Todo>) {
                val endTime = System.currentTimeMillis()
                val duration = endTime - startTime

                if (response.isSuccessful) {
                    Log.i("RetrofitTest", "✅ [SUCCESS] URL: ${call.request().url()}")
                    Log.i("RetrofitTest", "⏱ Tempo: ${duration}ms")
                    Log.i("RetrofitTest", "📦 Código HTTP: ${response.code()}")
                } else {
                    Log.e("RetrofitTest", "❌ [ERROR] URL: ${call.request().url()}")
                    Log.e("RetrofitTest", "⏱ Tempo: ${duration}ms")
                    Log.e("RetrofitTest", "📦 Código HTTP: ${response.code()}")
                }

                onFinished?.invoke()
            }

            override fun onFailure(call: Call<Todo>, t: Throwable) {
                val endTime = System.currentTimeMillis()
                Log.e("RetrofitTest", "❌ [FAILURE] URL: ${call.request().url()}")
                Log.e("RetrofitTest", "⏱ Tempo: ${endTime - startTime}ms")
                Log.e("RetrofitTest", "🧨 Erro: ${t.localizedMessage}")
                onFinished?.invoke()
            }
        })
    }
}
