package com.jaquelinebruzasco.vntschool_aula21_test.math

import kotlin.math.pow

internal class Calculator {
    fun sum(first: Int, second: Int): Int = first + second

    fun subtraction(first: Int, second: Int): Int = first - second

    fun division(first: Double, second: Double): Double {
        if (second == 0.0) {
            throw IllegalArgumentException("Divisor can not be zero.")
        }
        return first / second
    }

    fun isEven(number: Int): Boolean {
        return number % 2 == 0
    }

    fun exponentiation(base: Double, exponent: Double): Double {
        return base.pow(exponent)
    }

    fun isPrimeNumber(number: Int): Boolean {
        for (i in 2..number / 2) {
            if (number % i == 0) {
                return false
            }
        }
        return true
    }
}