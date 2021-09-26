package com.example.streamingaudiotest

import android.animation.ArgbEvaluator
import android.animation.ValueAnimator
import android.content.res.ColorStateList
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.Button
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.viewpager.widget.ViewPager

class MainActivity : AppCompatActivity() {
    //val mediaPlayerWrapper = MediaPlayerWrapper(this,"https://stream.zeno.fm/f174214qvzzuv", true)
    val mediaPlayerWrapper = MediaPlayerWrapper(this, "https://online.hitfm.ua/HitFM_HD", false)

    private val radioList = ArrayList<Radio>()
    private var lastVibrantColor: Int? = null
    private var lastDominantColor: Int? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        radioList.add(Radio("HitFM", "https://online.hitfm.ua/HitFM_HD", R.drawable.hitfm_logo))
        radioList.add(
            Radio(
                "Radio Roks",
                "https://online.radioroks.ua/RadioROKS_HD",
                R.drawable.radio_roks_logo
            )
        )

        val viewPager = findViewById<ViewPager>(R.id.RadioViewPager)
        val pagerAdapter = RadioPagerAdapter(supportFragmentManager, radioList)
        viewPager.offscreenPageLimit = 2
        viewPager.adapter = pagerAdapter
        viewPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            override fun onPageSelected(position: Int) {
                val icon = radioList[position].ImageResID

                val bitmap = BitmapFactory.decodeResource(
                    resources,
                    icon
                )
                Palette.Builder(bitmap).generate {
                    it?.let { palette ->
                        val dominantColor = palette.getDominantColor(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.colorPrimaryDark
                            )
                        )
                        val vibrantColor = palette.getVibrantColor(Color.BLACK)

                        if (lastDominantColor == null || lastVibrantColor == null) {
                            lastDominantColor = dominantColor
                            lastVibrantColor = vibrantColor

                            findViewById<TextView>(R.id.radioName)?.setTextColor(vibrantColor)
                            findViewById<ConstraintLayout>(R.id.ParentView)?.setBackgroundColor(
                                dominantColor
                            )

                            val playButton = findViewById<Button>(R.id.button)
                            playButton?.backgroundTintList = ColorStateList.valueOf(vibrantColor)
                        } else {
                            val dominantColorAnimation =
                                ValueAnimator.ofArgb(lastDominantColor!!, dominantColor)
                            val vibrantColorAnimation =
                                ValueAnimator.ofArgb(lastVibrantColor!!, vibrantColor)

                            dominantColorAnimation.duration = 350
                            vibrantColorAnimation.duration = 350

                            dominantColorAnimation.addUpdateListener {
                                findViewById<ConstraintLayout>(R.id.ParentView)?.setBackgroundColor(
                                    it.animatedValue as Int
                                )
                            }

                            vibrantColorAnimation.addUpdateListener {
                                findViewById<TextView>(R.id.radioName)?.setTextColor(it.animatedValue as Int)
                                val playButton = findViewById<Button>(R.id.button)
                                playButton?.backgroundTintList =
                                    ColorStateList.valueOf(it.animatedValue as Int)
                            }

                            dominantColorAnimation.start()
                            vibrantColorAnimation.start()
                        }


                    }
                }
            }

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
            }

            override fun onPageScrollStateChanged(state: Int) {}
        })

        val button = findViewById<Button>(R.id.button)

        button.setOnClickListener {

            val fadeoutAnim = AnimationUtils.loadAnimation(this, R.anim.play_fadeout_rotate)
            fadeoutAnim.duration = 250

            val fadeinAnim = AnimationUtils.loadAnimation(this, R.anim.play_fadein_rotate)
            fadeinAnim.duration = 250

            fadeoutAnim.setAnimationListener(object : Animation.AnimationListener {

                override fun onAnimationRepeat(p0: Animation?) {}
                override fun onAnimationStart(p0: Animation?) {}

                override fun onAnimationEnd(p0: Animation?) {
                    button.background =
                        if (mediaPlayerWrapper.isPlaying() == true) getDrawable(R.drawable.ic_pause) else getDrawable(
                            R.drawable.ic_play
                        )
                    button.startAnimation(fadeinAnim)
                }
            })

            if (mediaPlayerWrapper.isPlaying() == true) {
                button.startAnimation(fadeoutAnim)
                mediaPlayerWrapper.pause()
            } else if (mediaPlayerWrapper.isPlaying() == false) {
                button.startAnimation(fadeoutAnim)
                mediaPlayerWrapper.play()
            }
        }

        viewPager.currentItem = 1
        viewPager.currentItem = 0
    }

    override fun onDestroy() {
        mediaPlayerWrapper.release()
        super.onDestroy()
    }

}
