package com.example.a2048clone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.example.a2048clone.R
import com.example.a2048clone.contract.GameContract

class InfoScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info_screen)

        val back = findViewById<ImageView>(R.id.backButtton)

        back.setOnClickListener {
            finish()
        }

    }


}