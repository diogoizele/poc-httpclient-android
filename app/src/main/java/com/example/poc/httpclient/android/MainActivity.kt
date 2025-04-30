package com.example.poc.httpclient.android

import android.os.Bundle
import android.view.View
import android.widget.Button
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.poc.httpclient.android.clients.HttpURLConnectionService
import com.example.poc.httpclient.android.clients.KtorClientService
import com.example.poc.httpclient.android.clients.OkHttpClientService
import com.example.poc.httpclient.android.clients.RetrofitClient
import com.example.poc.httpclient.android.clients.VolleyClientService
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        VolleyClientService.init(applicationContext)
        bindButtons()
    }

    private fun bindButtons() {
        val rootView = findViewById<View>(android.R.id.content)

        findViewById<Button>(R.id.btn_okhttp).setOnClickListener {
            OkHttpClientService.executeRequest(
                "https://jsonplaceholder.typicode.com/todos/1",
                onFinished = {
                    runOnUiThread {
                        Snackbar.make(rootView, "Finalizado", Snackbar.LENGTH_LONG).show()
                    }
                })
        }

        findViewById<Button>(R.id.btn_http_url_connection).setOnClickListener {
            HttpURLConnectionService.executeRequest(
                "https://jsonplaceholder.typicode.com/todos/1",
                onFinished = {
                    runOnUiThread {
                        Snackbar.make(rootView, "Finalizado", Snackbar.LENGTH_LONG).show()
                    }
                })
        }

        findViewById<Button>(R.id.btn_retrofit).setOnClickListener {
            RetrofitClient.executeRequest(
                RetrofitClient.apiService.getTodo(),
                onFinished = {
                    runOnUiThread {
                        Snackbar.make(rootView, "Finalizado", Snackbar.LENGTH_LONG).show()
                    }
                })
        }

        findViewById<Button>(R.id.btn_volley).setOnClickListener {
            VolleyClientService.executeRequest(
                "https://jsonplaceholder.typicode.com/todos/1",
                onFinished = {
                    runOnUiThread {
                        Snackbar.make(rootView, "Finalizado", Snackbar.LENGTH_LONG).show()
                    }
                })
        }

        findViewById<Button>(R.id.btn_ktor_client).setOnClickListener {
            lifecycleScope.launch {
                KtorClientService.executeRequest(
                    "https://jsonplaceholder.typicode.com/todos/1",
                    onFinished = {
                        runOnUiThread {
                            Snackbar.make(rootView, "Finalizado", Snackbar.LENGTH_LONG).show()
                        }
                    }
                )
            }
        }

    }
}