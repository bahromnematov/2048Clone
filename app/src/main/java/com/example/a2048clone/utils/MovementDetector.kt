package com.example.a2048clone.utils

import android.content.Context
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import com.example.a2048clone.data.SideEnum
import kotlin.math.abs

class MovementDetector(contex: Context) : View.OnTouchListener {
    private var listener: ((SideEnum) -> Unit)? = null
    private val MOVE_REQUIREMENT = 150
    private val gesture =
        GestureDetector(contex, object : GestureDetector.SimpleOnGestureListener() {
            override fun onFling(
                e1: MotionEvent,
                e2: MotionEvent,
                velocityX: Float,
                velocityY: Float
            ): Boolean {
                if (abs(e1.x - e2.x) > abs(e1.y - e2.y)) {
                    if (abs(e1.x - e2.x) < MOVE_REQUIREMENT) return false
                    listener?.invoke(if (e2.x > e1.x) SideEnum.RIGHT else SideEnum.LEFT)
                } else if (abs(e1.x - e2.x) < abs(e1.y - e2.y)) {
                    if (abs(e1.y - e2.y) < MOVE_REQUIREMENT) return false
                    listener?.invoke(if (e2.y > e1.y) SideEnum.DOWN else SideEnum.UP)
                }
                return true
            }
        })

    override fun onTouch(v: View?, v2: MotionEvent): Boolean {
        gesture.onTouchEvent(v2)
        return true
    }


    fun setOnSideEnumListener(block: (SideEnum) -> Unit){
        listener = block
    }
}