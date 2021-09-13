package com.example.streamingaudiotest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    //val mediaPlayerWrapper = MediaPlayerWrapper(this,"https://stream.zeno.fm/f174214qvzzuv", true)
    val mediaPlayerWrapper = MediaPlayerWrapper(this,"https://online.hitfm.ua/HitFM_HD", false)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hitFMUri = Uri.parse("https://online.hitfm.ua/HitFM_HD")
        //mediaPlayer?.setDataSource("https://online.hitfm.ua/HitFM_HD")

        val button = findViewById<Button>(R.id.button)
        button.setOnClickListener {
            if (mediaPlayerWrapper.isPlaying() == true) {
                mediaPlayerWrapper.pause()
            } else if (mediaPlayerWrapper.isPlaying() == false) {
                mediaPlayerWrapper.play()
            }
        }
    }

    override fun onDestroy() {
        mediaPlayerWrapper.release()
        super.onDestroy()
    }

}
