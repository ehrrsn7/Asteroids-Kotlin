package com.example.asteroidsKotlin

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import kotlin.random.Random

class Star(context: Context) {

    companion object {
        val avgStarAmount   = 20
        val randOffset      = 10
    }

    val point = Game.getRandomPointOnScreen()
    val starSize = Random.nextInt(10, 20)

    // frame design tutorial:
    // https://leafsea.tumblr.com/post/163424458076/pixel-art-tutorial-twinkling-stars
    val frames = arrayListOf<Bitmap>()

    var frame = Random.nextInt(4)
    fun incrementFrame() { frame++; if (frame > 4) frame = 0; }

    init {
        frames.add(createBitmap(context, R.drawable.star_frame_0))
        frames.add(createBitmap(context, R.drawable.star_frame_1))
        frames.add(createBitmap(context, R.drawable.star_frame_2))
        frames.add(createBitmap(context, R.drawable.star_frame_3))
        frames.add(createBitmap(context, R.drawable.star_frame_4))

        for (frame in frames) resizeBitmap(frame, starSize)
    }

    private fun createBitmap(context: Context, drawable:Int) =
            BitmapFactory.decodeResource(context.resources, drawable)
    private fun resizeBitmap(bitmap: Bitmap, radius: Int) =
            Bitmap.createScaledBitmap(bitmap, radius, radius, false)
}