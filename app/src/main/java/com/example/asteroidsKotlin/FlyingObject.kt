package com.example.asteroidsKotlin

import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Point
import kotlin.math.cos
import kotlin.math.sin

abstract class FlyingObject {

    var name    = "Unknown Flying Object"
    var point   = Point(0,0)

    var rotateRight = false
    var rotateLeft  = false

    protected var velocity      : Velocity  = Velocity()

    protected var radius        : Int       = 0
    protected var rotation      : Float     = 90f
    protected var deathTimer    : Float     = 0f

    protected var alive         : Boolean   = true
    protected var wrap          : Boolean   = false
    protected var debug         : Boolean   = false


    /** public methods **/

    // update
    public open fun update(dt: Long) {
        // debug
        if (debug) print("$name.update() ")

        // translate
        point.translate(velocity.dx, -velocity.dy)
        if (debug) println("$point ")

        // wrap edges
        wrap()

        // handle death timer
        if      (deathTimer > 0) deathTimer--
        else if (deathTimer < 0) alive = false
        if (debug) print("$deathTimer ")

        // handle rotation
        if (rotateLeft) rotate(rotateAmount)
        if (rotateRight) rotate(-rotateAmount)
        while (rotation < 0)    rotation += 360f
        while (rotation >= 360) rotation -= 360f
        if (debug) print("$rotation ")
    }

    // accelerate
    fun accelerate(amount: Float) {
        if (debug) print("$name.accelerate($velocity) ")
        velocity.translate(
            (amount * cos(Math.toRadians(
                    rotation.toDouble()))).toFloat(),
            (amount * sin(Math.toRadians(
                    rotation.toDouble()))).toFloat()
        )
    }

    // launch (alt accelerate)
    fun launch(amount: Float) { accelerate(amount) }


    /** private methods **/

    // wrap
    protected var screen = Game.screenDimensions

    private fun wrap() {

        // quick exit
        if (!wrap) return

        // debug
        if (debug) print("$name.wrap() ")

        // right -> left
        if (point.x > screen.right)
            point.x = screen.left - radius

        // left -> right
        else if (point.x < screen.left - radius)
            point.x = screen.right

        // bottom -> top
        if (point.y > screen.bottom)
            point.y = screen.top - radius

        // top -> bottom
        else if (point.y < screen.top - radius)
            point.y = screen.bottom
    }

    var rotateAmount = 5f
    private fun rotate(amount: Float) {
        rotation += amount
        if (!debug) print("rotation: $rotation ")
    }
}

// I really like having this 'adder'
private fun Point.translate(x: Float, y: Float) {
    this.x += x.toInt()
    this.y += y.toInt()
}

// add operator for rotating bitmap 'images'
fun Bitmap.rotate(degrees: Float): Bitmap {
    print("bitmap.rotate($degrees)")
    return Bitmap.createBitmap(this,
            0, 0, width, height,
            Matrix().apply { postRotate(degrees) },
            true)
    // src: https://gist.github.com/codeswimmer/858833
}

// very brief velocity class definition
class Velocity(var dx: Float = 0f, var dy: Float = 0f) {
    public fun translate(ddx:Float, ddy:Float)
        { dx += ddx; dy += ddy }
}
