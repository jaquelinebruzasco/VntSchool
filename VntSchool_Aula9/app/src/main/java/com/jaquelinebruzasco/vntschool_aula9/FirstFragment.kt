package com.jaquelinebruzasco.vntschool_aula9

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment

class FirstFragment : Fragment(R.layout.fragment_first) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val button = view.findViewById<Button>(R.id.btn_next)
        val secondFragment = SecondFragment()
        val editText = view.findViewById<EditText>(R.id.et_text)


        button.setOnClickListener {
            val bundle = Bundle().apply { putString("text", editText.text.toString()) }
            secondFragment.arguments = bundle
            parentFragmentManager.beginTransaction()
                .replace(R.id.fragment_content, secondFragment)
                .addToBackStack(null)
                .commit()
        }
    }
}