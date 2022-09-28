package com.jaquelinebruzasco.vntschool_aula9

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment

class SecondFragment : Fragment(R.layout.fragment_second) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.btn_previous)
        val textInfo = arguments?.getString("text")

        val textView = view.findViewById<TextView>(R.id.tv_text)
        textView.text = textInfo

        button.setOnClickListener {
            parentFragmentManager.popBackStack()
        }
    }
}