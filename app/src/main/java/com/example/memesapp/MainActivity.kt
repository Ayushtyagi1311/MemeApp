package com.example.memesapp

import android.content.Intent
import android.graphics.drawable.Drawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.engine.GlideException
import com.bumptech.glide.request.RequestListener
import com.bumptech.glide.request.target.Target

class MainActivity : AppCompatActivity() {

    private var mURL : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadMeme()
    }
    private fun loadMeme() {

        val progressbar = findViewById<ProgressBar>(R.id.pbbar)

        progressbar.visibility = View.VISIBLE

// Instantiate the RequestQueue.
        val queue = Volley.newRequestQueue(this)
        val url = "https://meme-api.herokuapp.com/gimme"
        val ivmemesimg = findViewById<ImageView>(R.id.ivmemesimg)

// Request a string response from the provided URL.
        val jsonObjectRequest = JsonObjectRequest(
            Request.Method.GET, url, null,

                    Response.Listener{ response ->

                val url = response.getString("url")

                        mURL = url
                        Glide.with(this).load(url).listener(object:RequestListener<Drawable>{
                            override fun onLoadFailed(
                                e: GlideException?,
                                model: Any?,
                                target: Target<Drawable>?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressbar.visibility = View.GONE

                                return false
                            }


                            override fun onResourceReady(
                                resource: Drawable?,
                                model: Any?,
                                target: Target<Drawable>?,
                                dataSource: DataSource?,
                                isFirstResource: Boolean
                            ): Boolean {
                                progressbar.visibility = View.GONE

                                return false
                            }

                        }).into(ivmemesimg)

            },
            Response.ErrorListener {
            })

        queue.add(jsonObjectRequest)
    }

    fun ShareMeme(view: android.view.View) {
        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "text/plain"
        intent.putExtra(Intent.EXTRA_TEXT, "Cool Meme $mURL" )
        val chooser = Intent.createChooser(intent, "Share this mem using..")
        startActivity(chooser)
    }
    fun NextMeme(view: android.view.View) {
        loadMeme()

    }
}