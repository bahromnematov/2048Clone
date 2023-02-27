package com.example.a2048clone.ui

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatTextView
import com.example.a2048clone.R
import com.example.a2048clone.contract.GameContract
import com.example.a2048clone.data.SideEnum
import com.example.a2048clone.presenter.GamePresenter
import com.example.a2048clone.repository.GameRepository
import com.example.a2048clone.shared.AppPref
import com.example.a2048clone.utils.MovementDetector
import kotlinx.android.synthetic.main.finish_dialog.view.moves

class MainActivity : AppCompatActivity(), GameContract.View {
    private lateinit var btnNewGame: AppCompatButton
    private lateinit var score: TextView
    private lateinit var target: TextView
    private lateinit var record: TextView
    private var btnRestartFinish = false

    private val buttons: ArrayList<TextView> = ArrayList(16)
    private val presenter = GamePresenter(this, GameRepository())


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        loadViews()

        presenter.startGame()

    }

    private fun shareTextOnly(title: String) {

        // The value which we will sending through data via
        // other applications is defined
        // via the Intent.ACTION_SEND
        val intentt = Intent(Intent.ACTION_SEND)

        // setting type of data shared as text
        intentt.type = "text/plain"
        intentt.putExtra(Intent.EXTRA_SUBJECT, "Subject Here")

        // Adding the text to share using putExtra
        intentt.putExtra(Intent.EXTRA_TEXT, title)
        startActivity(Intent.createChooser(intentt, "Share Via"))
    }


    override fun onPause() {
        super.onPause()
        if (btnRestartFinish) {
            AppPref.setGameUndoType(true)
            presenter.restart()

        } else {
            presenter.saveData()
        }
    }

    override fun onStop() {
        super.onStop()
        if (btnRestartFinish) {
            AppPref.setGameUndoType(true)
            presenter.restart()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        if (btnRestartFinish) {
            AppPref.setGameUndoType(true)
            presenter.restart()
        }
        presenter.saveData()

    }


    @SuppressLint("StringFormatInvalid")
    override fun showFinishDialog() {
        btnRestartFinish = true

        val dialog = Dialog(this)
        val view: View = LayoutInflater.from(this)
            .inflate(R.layout.finish_dialog, findViewById(R.id.container), false)
        dialog.setContentView(view)
       view.moves.text =
            resources.getString(R.string._2048, presenter.getScore().toString())
        view.findViewById<View>(R.id.button_exit_yes).setOnClickListener { view1: View? ->
            dialog.cancel()
            dialog.dismiss()
            presenter.restart()

        }
        view.findViewById<View>(R.id.button_exit_no)
            .setOnClickListener { view2: View? ->
                finish()
            }
        dialog.show()

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    @SuppressLint("ClickableViewAccessibility")
    private fun loadViews() {
        score = findViewById(R.id.step)
        record = findViewById(R.id.bestScore)
        btnNewGame = findViewById(R.id.buttonReload)


        findViewById<AppCompatButton>(R.id.buttonReload).setOnClickListener {

            presenter.restart()
            AppPref.setGameUndoType(true)
        }
        val mainContainer = findViewById<LinearLayout>(R.id.mainView)
        for (i in 0 until mainContainer.childCount) {
            val childContainer = mainContainer.getChildAt(i) as LinearLayout
            for (j in 0 until childContainer.childCount) {
                buttons.add(childContainer.getChildAt(j) as TextView)
            }
        }
        val movementDetector = MovementDetector(this)
        movementDetector.setOnSideEnumListener {
            AppPref.setGameUndoType(false)
            when (it) {
                SideEnum.LEFT -> presenter.moveToLeft()
                SideEnum.RIGHT -> presenter.moveToRight()
                SideEnum.DOWN -> presenter.moveToDown()
                SideEnum.UP -> presenter.moveToUp()
            }
        }
        mainContainer.setOnTouchListener(movementDetector)


    }

    override fun onBackPressed() {
        val dialog = Dialog(this)
        val view: View = LayoutInflater.from(this)
            .inflate(R.layout.exit_dialog, findViewById(R.id.container), false)
        dialog.setContentView(view)

        view.findViewById<View>(R.id.button_exit_no).setOnClickListener { view1: View? ->
            dialog.cancel()
            dialog.dismiss()
        }
        view.findViewById<View>(R.id.button_exit_yes)
            .setOnClickListener { view2: View? -> finish() }
        dialog.show()

        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }


    override fun changeState(matrix: Array<Array<Int>>) {
        score.text = presenter.getScore().toString()
        record.text = presenter.getRecord().toString()
        for (i in matrix.indices) {
            for (j in 0 until matrix[i].size) {
                val button = buttons[4 * i + j]
                val value = matrix[i][j]

                if (value == 1024 || value == 2048 || value == 4096 || value == 8192) {
                    button.textSize = 24f
                } else {
                    button.textSize = 33f
                }

                when (value) {
                    2, 4 -> {
                        button.setTextColor(Color.parseColor("#766962"))
                    }

                    8 -> {
                        button.setTextColor(Color.parseColor("#F9F4F3"))
                    }

                    else -> {
                        button.setTextColor(Color.parseColor("#F9F4F3"))
                    }
                }
                if (value == 0) button.text = ""
                else button.text = matrix[i][j].toString()
                button.setBackgroundResource(
                    when (value) {
                        0 -> R.drawable.bg_item_0
                        2 -> R.drawable.bg_item_2
                        4 -> R.drawable.bg_item_4
                        8 -> R.drawable.bg_item_8
                        16 -> R.drawable.bg_item_16
                        32 -> R.drawable.bg_item_32
                        64 -> R.drawable.bg_item_64
                        128 -> R.drawable.bg_item_128
                        256 -> R.drawable.bg_item_256
                        512 -> R.drawable.bg_item_512
                        1024 -> R.drawable.bg_item_1024
                        2048 -> R.drawable.bg_item_2048
                        else -> R.drawable.bg_item_0

                    }
                )
            }
        }
    }
}