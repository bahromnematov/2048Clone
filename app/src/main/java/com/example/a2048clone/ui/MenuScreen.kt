package com.example.a2048clone.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.AppCompatButton
import androidx.appcompat.widget.AppCompatImageView
import com.example.a2048clone.R

class MenuScreen : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_menu_screen)


        val play = findViewById<AppCompatButton>(R.id.playBtn)
        val info = findViewById<AppCompatImageView>(R.id.btnInfo)
        val shareBtn=findViewById<AppCompatImageView>(R.id.btnShare)

        play.setOnClickListener {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        info.setOnClickListener {
            val intent = Intent(this, InfoScreen::class.java)
            startActivity(intent)
        }
        shareBtn.setOnClickListener {
            val intent= Intent()
            intent.action= Intent.ACTION_SEND
            intent.putExtra(Intent.EXTRA_TEXT,"Enter_value")
            intent.type="text/plain"
            startActivity(Intent.createChooser(intent,"Share To:"))
        }
    }
}