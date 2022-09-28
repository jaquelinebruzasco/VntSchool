package com.jaquelinebruzasco.vntschool_aula10

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity() {

    private lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        setSupportActionBar(findViewById(R.id.toolbar))


        val counter = savedInstanceState?.getString("counter") ?: "0"
        textView = findViewById(R.id.tv_tv)
        textView.text = counter

        findViewById<Button>(R.id.btn_add).setOnClickListener {
            val currentValue = textView.text.toString().toInt()
            val result = currentValue + 1
            textView.text = result.toString()
        }
    }


    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        val counterText = textView.text.toString()
        outState.putString("counter", counterText)

    }
}