package com.example.streamingaudiotest

import android.animation.ValueAnimator
import android.content.Context
import android.media.MediaPlayer
import android.util.Log
import android.widget.Toast
import okhttp3.*
import java.io.IOException

class MediaPlayerWrapper(private val context: Context) {
    private var mediaPlayer: MediaPlayer? = null
    private var playAfter = false

    init {
        mediaPlayer = MediaPlayer()
    }

    fun playerInit(url: String, temporaryLink: Boolean) {
//        if (mediaPlayer?.isPlaying == true){
//            val newPlayer = MediaPlayer()
//            newPlayer.setDataSource(url)
//            newPlayer.prepareAsync()
//
//            val anim = ValueAnimator.ofFloat(1.0f, 0.0f)
//            anim.duration = 350
//
//            anim.addUpdateListener {
//                Log.d("DEBUG", "${anim.animatedValue as Float}")
//                mediaPlayer?.setVolume(anim.animatedValue as Float, anim.animatedValue as Float)
//            }
//
//            anim.start()
//            newPlayer.start()
//            mediaPlayer?.stop()
//            mediaPlayer?.release()
//            anim.addUpdateListener {
//                newPlayer.setVolume(anim.animatedValue as Float, anim.animatedValue as Float)
//            }
//            anim.reverse()
//            anim.start()
//
//            mediaPlayer = newPlayer
//        }

        if (!temporaryLink && mediaPlayer?.isPlaying == false) {
            try {
                mediaPlayer = MediaPlayer()
                mediaPlayer?.setDataSource(url)
                mediaPlayer?.prepareAsync()
            } catch (e: IllegalStateException) {
                mediaPlayer?.stop()
                mediaPlayer?.release()
                mediaPlayer?.setDataSource(url)
                mediaPlayer?.prepareAsync()
            }

            playAfter = false

        } else if (!temporaryLink && mediaPlayer?.isPlaying == true) {
            mediaPlayer?.stop()
            mediaPlayer?.release()

            mediaPlayer = MediaPlayer()
            mediaPlayer?.setDataSource(url)
            mediaPlayer?.prepareAsync()

            mediaPlayer?.setOnPreparedListener {
                playAfter = true
            }
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


        }
        mediaPlayer?.setOnPreparedListener {
            Toast.makeText(context, "Media prepared", Toast.LENGTH_SHORT).show()
            if(playAfter) {
                mediaPlayer?.start()
                playAfter = false
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