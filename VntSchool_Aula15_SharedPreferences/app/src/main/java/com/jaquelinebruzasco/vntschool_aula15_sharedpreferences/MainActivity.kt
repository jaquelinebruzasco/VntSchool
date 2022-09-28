package com.jaquelinebruzasco.vntschool_aula15_sharedpreferences

import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (isFirstTime()) {
            showWelcomeMessage()
            updateFirstTime()
        }
    }

    private fun isFirstTime(): Boolean {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        return sharedPref.getBoolean(KEY_FIRST_TIME, true)
    }

    private fun updateFirstTime() {
        val sharedPref = getPreferences(Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean(KEY_FIRST_TIME, false)
            apply()
        }
    }

    private fun showWelcomeMessage() {
        val builder: AlertDialog.Builder = AlertDialog.Builder(this)
        builder.apply {
            setMessage(R.string.dialog_message)
            setPositiveButton("Ok", ) {_, _ -> }
            setCancelable(false)
        }

        val dialog: AlertDialog = builder.create()
        dialog.show()
    }

    private companion object {
        private const val KEY_FIRST_TIME = "KEY_FIRST_TIME"
    }
}

