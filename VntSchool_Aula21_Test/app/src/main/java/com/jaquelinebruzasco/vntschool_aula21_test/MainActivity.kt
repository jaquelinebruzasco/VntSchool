package com.jaquelinebruzasco.vntschool_aula21_test

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.jaquelinebruzasco.vntschool_aula21_test.databinding.ActivityMainBinding
import com.jaquelinebruzasco.vntschool_aula21_test.math.Calculator

internal class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val calculator = Calculator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initListeners()
    }

    private fun initListeners() = with(binding) {
        buttonAdd.setOnClickListener {
            if (areNumbersInserted()) {
                val first = edittextFirst.text.toString().toInt()
                val second = edittextSecond.text.toString().toInt()
                val result = calculator.sum(first, second)
                textviewResult.text = result.toString()
            }
        }
        buttonSub.setOnClickListener {
            if (areNumbersInserted()) {
                val first = edittextFirst.text.toString().toInt()
                val second = edittextSecond.text.toString().toInt()
                val result = calculator.subtraction(first, second)
                textviewResult.text = result.toString()
            }
        }
        buttonDivide.setOnClickListener {
            if (areNumbersInserted()) {
                val first = edittextFirst.text.toString().toDouble()
                val second = edittextSecond.text.toString().toDouble()
                val result = calculator.division(first, second)
                textviewResult.text = result.toString()
            }
        }

        buttonExponentiation.setOnClickListener {
            if (areNumbersInserted()) {
                val base = edittextFirst.text.toString().toDouble()
                val exponent = edittextSecond.text.toString().toDouble()
                val result = calculator.exponentiation(base, exponent)
                textviewResult.text = result.toString()
            }
        }
    }

    private fun areNumbersInserted(): Boolean = with(binding) {
        edittextFirst.text.isNotEmpty() && edittextSecond.text.isNotEmpty()
    }
}