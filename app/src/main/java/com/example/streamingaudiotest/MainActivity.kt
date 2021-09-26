package com.example.streamingaudiotest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {
    //val mediaPlayerWrapper = MediaPlayerWrapper(this,"https://stream.zeno.fm/f174214qvzzuv", true)
    val mediaPlayerWrapper = MediaPlayerWrapper(this,"https://online.hitfm.ua/HitFM_HD", false)

    private val radioList = ArrayList<Radio>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val hitFMUri = Uri.parse("https://online.hitfm.ua/HitFM_HD")
        //mediaPlayer?.setDataSource("https://online.hitfm.ua/HitFM_HD")

        radioList.add(Radio("HitFM", "https://online.hitfm.ua/HitFM_HD", R.drawable.hitfm_logo))

        val viewPager = findViewById<ViewPager>(R.id.RadioViewPager)
        val pagerAdapter = RadioPagerAdapter(supportFragmentManager, radioList)
        viewPager.adapter = pagerAdapter

        val button = findViewById<Button>(R.id.button)

//        button.setOnClickListener {
//            if (mediaPlayerWrapper.isPlaying() == true) {
//                mediaPlayerWrapper.pause()
//                button.text = "Play"
//            } else if (mediaPlayerWrapper.isPlaying() == false) {
//                mediaPlayerWrapper.play()
//                button.text = "Pause"
//            }
//        }
    }

    override fun onDestroy() {
        mediaPlayerWrapper.release()
        super.onDestroy()
    }

}
