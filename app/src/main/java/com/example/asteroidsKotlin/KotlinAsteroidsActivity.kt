package com.example.asteroidsKotlin

import android.app.Activity
import android.graphics.Point
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast

class KotlinAsteroidsActivity : Activity() {

    /****************************************************
     * kotlinAsteroidsView == the view of the game
     * this will also hold the logic of the game
     * and respond to screen touches as well
     ****************************************************/
    private var game: Game? = null

    /** objects/helper functions to be referenced in other
     * files in package **/
    companion object {
        var screenDimensions = Point() // populate in onCreate()
    }

    /** 'main' **/
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // get 'display' object to access screen details
        val display = windowManager.defaultDisplay

        // load the resolution into a dimensions object
        screenDimensions = Point()
        display.getSize(screenDimensions)

        // finish setup
        println("Asteroids App compiled")
        setContentView(R.layout.activity_kotlin_asteroids)

        // set up button event listeners
        val accelerateButton = findViewById<Button>(R.id.shipAccelerateButton)
        accelerateButton.setOnClickListener {
            Toast.makeText(this,
                    "ship.accelerate()",
                    Toast.LENGTH_SHORT).show()
        }

        val shipFireLaserButton = findViewById<Button>(R.id.shipFireLaserButton)
        shipFireLaserButton.setOnClickListener {
            Toast.makeText(this,
                    "ship.fire()",
                    Toast.LENGTH_SHORT).show()
        }

        val shipRotateLeftButton = findViewById<Button>(R.id.shipRotateLeftButton)
        shipRotateLeftButton.setOnClickListener {
            Toast.makeText(this,
                "ship.rotateLeft()",
                Toast.LENGTH_SHORT).show()
        }

        val shipRotateRightButton = findViewById<Button>(R.id.shipRotateRightButton)
        shipRotateRightButton.setOnClickListener {
            Toast.makeText(this,
                "ship.rotateRight()",
                Toast.LENGTH_SHORT).show()
        }
    }

    /****************************************************
     * resume and pause to implement runnable objects by
     * calling Runnable::start() and Runnable::pause()
     ****************************************************/

    override fun onResume() {
        super.onResume()
        println("main.resume()")
        game?.resume()
    }

    override fun onPause() {
        super.onPause()
        game?.pause()
    }
}