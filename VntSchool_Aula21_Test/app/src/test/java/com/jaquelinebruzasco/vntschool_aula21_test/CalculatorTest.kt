package com.jaquelinebruzasco.vntschool_aula21_test

import com.jaquelinebruzasco.vntschool_aula21_test.math.Calculator
import org.junit.Assert
import org.junit.Test

internal class CalculatorTest {
    private val calculator = Calculator()

    @Test
    fun `test if adding two numbers returns the correct result`() {
        val expected = 5
        val result = calculator.sum(2, 3)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `test when negative numbers then sum works`() {
        val expected = -1
        val result = calculator.sum(2, -3)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `test if subtracting two numbers returns the correct result`() {
        val expected = 1
        val result = calculator.subtraction(3, 2)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `test when negative numbers then subtraction works`() {
        val expected = 5
        val result = calculator.subtraction(3, -2)
        Assert.assertEquals(expected, result)
    }

    @Test
    fun `test when 6 divided by 2 returns 3`() {
        val expected = 3.0
        val result = calculator.division(6.0, 2.0)
        Assert.assertEquals(expected, result, 0.0)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `test if any number divided by 0 throws error`() {
        calculator.division(1000.0, 0.0)
    }

    @Test
    fun `test if an even number returns true`() {
        val result = calculator.isEven(number = 2)
        Assert.assertTrue(result)
    }

    @Test
    fun `test if an odd number returns false`() {
        val result = calculator.isEven(number = 3)
        Assert.assertFalse(result)
    }

    @Test
    fun `test if 2 raised to 2nd power returns 4`() {
        val expected = 4.0
        val result = calculator.exponentiation(base = 2.0, exponent = 2.0)
        Assert.assertEquals(expected, result, 0.0)
    }

    @Test
    fun `test if 2 raised to 10 power returns 1024`() {
        val expected = 1024.0
        val result = calculator.exponentiation(base = 2.0, exponent = 10.0)
        Assert.assertEquals(expected, result, 0.0)
    }

    @Test
    fun `test if a prime number returns true`() {
        val result = calculator.isPrimeNumber(number = 7)
        Assert.assertTrue(result)
    }

    @Test
    fun `test if a composite number returns false`() {
        val result = calculator.isPrimeNumber(number = 10)
        Assert.assertFalse(result)
    }
}