package com.example.a2048clone.presenter

import com.example.a2048clone.contract.GameContract

class GamePresenter(
    private val view: GameContract.View,
    private val repository: GameContract.Repository
) : GameContract.Presenter {
    override fun getScore(): Int {
        return repository.getScore()
    }

    override fun startGame() {
        view.changeState(repository.getMatrix())
    }

    override fun getRecord(): Int {
        return repository.getRecord()
    }

    override fun moveToLeft() {
        repository.moveToLeft()
        view.changeState(repository.getMatrix())
        checkFinish()
    }

    override fun moveToRight() {
        repository.moveToRight()
        view.changeState(repository.getMatrix())
        checkFinish()
    }

    override fun moveToUp() {
        repository.moveToUp()
        view.changeState(repository.getMatrix())
        checkFinish()
    }

    override fun moveToDown() {
        repository.moveToDown()
        view.changeState(repository.getMatrix())
        checkFinish()
    }

    override fun undo() {
        repository.undo()
        view.changeState(repository.getMatrix())
    }

    override fun restart() {
        repository.restart()
        view.changeState(repository.getMatrix())
    }

    override fun saveData() {
        repository.saveData()
    }

    override fun checkFinish() {
        val matrix = repository.getMatrix()
        for (i in 0..3) {
            var temp = ""
            for (j in 0..3) {
                temp += matrix[i][j].toString() + " "
            }
        }
        for (i in matrix) {
            for (j in i) {
                if (j==0)
                    return
            }
        }

        for(i in 0..3){
            for (j in 1..3){
                if (matrix[i][j] == matrix[i][j-1]){
                    return
                }
            }
        }
        for(j in 0..3) {
            for(i in 1..3) {
                if(matrix[i][j] == matrix[i - 1][j])
                    return
            }
        }
        view.showFinishDialog()

    }
}