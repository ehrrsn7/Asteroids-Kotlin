package com.example.asteroidsKotlin

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.util.Log
import android.view.SurfaceView
import android.widget.Button
import kotlin.random.Random

class Game (context: Context, attributeSet: AttributeSet)
    : SurfaceView(context, attributeSet), Runnable {

    // companion object (static attributes)
    companion object {
        val screenDimensions = Rect(
                0, 0,
                KotlinAsteroidsActivity.screenDimensions.x,
                KotlinAsteroidsActivity.screenDimensions.y
        )

        fun getRandomPointOnScreen() = Point(
                Random.nextInt(
                        screenDimensions.left,
                        screenDimensions.right),
                Random.nextInt(
                        screenDimensions.top,
                        screenDimensions.bottom)
        )

        fun getCenterPoint() = Point(
                screenDimensions.centerX(),
                screenDimensions.centerY()
        )
    }

    // game-related attributes
    private var debug: Boolean = false

    // game objects
    private var ship        = Ship(context)
    private var asteroids   = arrayListOf<Asteroid>()
    private var stars       = arrayListOf<Star>()

    /** constructor **/
    init {
        // asteroid belt
        for (i in 1..Asteroid.initAmount)
            asteroids.add(Asteroid(context))

        // stars
        for (i in 1..Random.nextInt(
                Star.avgStarAmount - Star.randOffset,
                Star.avgStarAmount + Star.randOffset
        )) stars.add(Star(context))
    }

    /** start **/
    override fun onLayout(
            changed: Boolean,
            left: Int, top: Int,
            right: Int, bottom: Int) {

        super.onLayout(changed, left, top, right, bottom)

        thread.start()
    }

    /** tick/update **/
    private val thread  : Thread    = Thread(this) // begin loop
    private val fps     : Long      = 60
    private var before  : Long      = System.currentTimeMillis()
    private var now     : Long      = System.currentTimeMillis()
    private var dt      : Long      = now - before

    override fun run() {
        now     = System.currentTimeMillis()
        dt      = now - before
        before  = now
        while (true) {
            update()
            Thread.sleep(1000/fps)
            if (debug) print("Thread.sleep(1000/fps)")
        }
    }

    private fun update() {
        if (debug) println("running...")

        draw()

        ship.update(dt)
        for (rock in asteroids) rock.update(dt)
        for (star in stars) star.incrementFrame()
    }


    /** render/draw **/                     //(context - WHERE to draw)
    private var canvas = Canvas()           // canvas - WHAT to draw
    private val paint = Paint().apply {     // paint - HOW to draw
        color = Color.BLACK }

    private fun draw() {
        if (holder.surface.isValid) {
            if (debug) print("game.draw() // ")

            // Lock the canvas ready to draw
            canvas = holder.lockCanvas()

            // Draw the background color
            canvas.drawColor(Color.BLACK)

            // stars
            for (star in stars)
                canvas.drawBitmap(star.frames[star.frame],
                        star.point.x.toFloat(),
                        star.point.y.toFloat(),
                        paint
                )

            // Draw all the game objects here

            // Now draw the player spaceship
            canvas.drawBitmap(
                    ship.getFrame(),
                    ship.point.x.toFloat(),
                    ship.point.y.toFloat(),
                    paint
            )

            // asteroids
            for (rock in asteroids)
                canvas.drawBitmap(
                        rock.bitmap,
                        rock.point.x.toFloat(),
                        rock.point.y.toFloat(),
                        paint
                )

            // Draw everything to the screen
            holder.unlockCanvasAndPost(canvas)
        }
    }

    /** handle events **/
    // TODO

    /**************************************************
     * called by app logic:
     * (when app is opened/closed)
     * see onResume and onPause in asteroids activity
     **************************************************/

    fun resume() { }

    fun pause() {
        try {
            thread.join()
        } catch (e: InterruptedException) {
            Log.e("Error:", "joining thread")
        }
    }

}
