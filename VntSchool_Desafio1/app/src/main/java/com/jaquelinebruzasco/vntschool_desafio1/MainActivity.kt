package com.jaquelinebruzasco.vntschool_desafio1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<Button>(R.id.btn_share).setOnClickListener { share() }
    }

    private fun share() {
        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, findViewById<EditText>(R.id.et_text).text.toString())
            type = "text/plain"
        }
        val shareIntent: Intent = Intent.createChooser(sendIntent, null)
        startActivity(shareIntent)
    }
}