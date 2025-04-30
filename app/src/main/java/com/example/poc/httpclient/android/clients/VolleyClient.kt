package com.example.poc.httpclient.android.clients

import android.content.Context
import android.util.Log
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley


object VolleyClientService {

    private lateinit var requestQueue: com.android.volley.RequestQueue

    fun init(context: Context) {
        if (!::requestQueue.isInitialized) {
            requestQueue = Volley.newRequestQueue(context.applicationContext)
        }
    }

    fun executeRequest(url: String, onFinished: (() -> Unit)? = null) {
        Log.i("VolleyTest", "Iniciando request para: $url")

        val startTime = System.currentTimeMillis()

        val stringRequest = StringRequest(Request.Method.GET, url,
            { response ->
                val endTime = System.currentTimeMillis()
                val duration = endTime - startTime

                Log.i("VolleyTest", "✅ [SUCCESS] URL: $url")
                Log.i("VolleyTest", "⏱ Tempo: ${duration}ms")


                onFinished?.invoke()
            },
            { error ->
                val endTime = System.currentTimeMillis()
                Log.e("VolleyTest", "❌ [FAILURE] URL: $url")
                Log.e("VolleyTest", "⏱ Tempo: ${endTime - startTime}ms")
                Log.e("VolleyTest", "🧨 Erro: ${error.localizedMessage}")

                onFinished?.invoke()
            })

        requestQueue.add(stringRequest)
    }
}
