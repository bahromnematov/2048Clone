package com.example.a2048clone.contract

import android.opengl.Matrix

interface GameContract {

    interface Repository {
        fun getMatrix(): Array<Array<Int>>
        fun moveToLeft()
        fun moveToRight()
        fun moveToUp()
        fun moveToDown()
        fun getRecord(): Int
        fun getScore(): Int
        fun saveData()
        fun undo()
        fun restart()

    }

    interface View {
        fun showFinishDialog()
        fun changeState(matrix: Array<Array<Int>>)

    }

    interface Presenter {
        fun getScore(): Int
        fun startGame()
        fun getRecord(): Int
        fun moveToLeft()
        fun moveToRight()
        fun moveToUp()
        fun moveToDown()
        fun undo()
        fun restart()
        fun saveData()

        fun checkFinish()
    }
}