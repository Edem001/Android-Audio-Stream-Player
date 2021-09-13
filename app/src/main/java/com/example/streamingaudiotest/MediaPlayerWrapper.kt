package com.example.streamingaudiotest

import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import okhttp3.*
import java.io.IOException

class MediaPlayerWrapper(context: Context,url: String, temporaryLink: Boolean) {
    private var mediaPlayer: MediaPlayer? = null


    init {
        mediaPlayer = MediaPlayer()

        if (!temporaryLink) {
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.prepare()
        } else {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("http://stream.zeno.fm/f174214qvzzuv")
                .build()

            val responseCallback = object : Callback {
                override fun onResponse(call: Call, response: Response) {
                    mediaPlayer?.setDataSource(response.request.url.toString())
                    mediaPlayer?.prepareAsync()
                }

                override fun onFailure(call: Call, e: IOException) {
                    Log.e("ERROR", e.message, e)
                }
            }

            client.newCall(request).enqueue(responseCallback)

            mediaPlayer?.setOnPreparedListener {
                Toast.makeText(context, "Media prepared", Toast.LENGTH_SHORT).show()
            }
        }
    }

    fun play() {
        mediaPlayer?.start()
    }

    fun pause() {
        mediaPlayer?.pause()
    }

    fun release() {
        mediaPlayer?.release()
    }

    fun isPlaying() = mediaPlayer?.isPlaying

    fun setUrl(url: String) {
        mediaPlayer?.setDataSource(url)
        mediaPlayer?.prepareAsync()
    }
}