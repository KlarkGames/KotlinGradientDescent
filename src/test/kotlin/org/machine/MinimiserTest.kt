package org.machine

import org.junit.Assert
import org.junit.Test
import smile.math.matrix.Matrix
import kotlin.math.cos
import kotlin.math.log2
import kotlin.math.pow
import kotlin.math.sin

class MinimiserTest {

    @Test
    fun xDerivatives() {
        val X = doubleArrayOf(2.0)
        val A = doubleArrayOf(1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0, 1.0)

        val minimiser = Minimiser(X, doubleArrayOf(), A, 0.0, 0.0, 0)

        val der = minimiser.xDerivatives().first

        Assert.assertEquals(der.get(0, 0), 1.0, 0.01)
        Assert.assertEquals(der.get(0, 1), X[0], 0.01)
        Assert.assertEquals(der.get(0, 2), X[0].pow(2), 0.01)
        Assert.assertEquals(der.get(0, 3), X[0].pow(3), 0.01)
        Assert.assertEquals(der.get(0, 4), X[0].pow(-1), 0.01)
        Assert.assertEquals(der.get(0, 5), X[0].pow(-2), 0.01)
        Assert.assertEquals(der.get(0, 6), X[0].pow(-3), 0.01)
        Assert.assertEquals(der.get(0, 7), X[0].pow(1.0 / 2.0), 0.01)
        Assert.assertEquals(der.get(0, 8), X[0].pow(1.0 / 3.0), 0.01)
        Assert.assertEquals(der.get(0, 9), log2(X[0]), 0.01)
        Assert.assertEquals(der.get(0, 10), sin(X[0]), 0.01)
        Assert.assertEquals(der.get(0, 11), cos(X[0]), 0.01)
    }

    @Test
    fun function() {
        val X = doubleArrayOf(1.0, 4.0, 7.0)
        val allowedA = doubleArrayOf(1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        val A = doubleArrayOf(3.0, 5.0)

        val minimiser = Minimiser(X, doubleArrayOf(), allowedA, 0.0, 0.0, 0)
        minimiser.setA(A)

        Assert.assertEquals(Matrix(doubleArrayOf(8.0, 23.0, 38.0)), minimiser.function())
    }

    @Test
    fun cost() {
        val Y = doubleArrayOf(6.0, 3.0, 4.0, 6.0, 8.0)
        val X = doubleArrayOf(1.0, 2.0, 4.0, 6.0, 7.0)
        val A = doubleArrayOf(2.0)
        val allowedA = doubleArrayOf(1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)

        val minimiser = Minimiser(X, Y, allowedA, 0.0, 0.0, 0)
        minimiser.setA(A)

        Assert.assertEquals(minimiser.cost(), 7.3, 0.01)
    }

    @Test
    fun gradient() {
        val Y = doubleArrayOf(2.0, 4.0, 6.0, 8.0)
        val X = doubleArrayOf(1.0, 2.0, 3.0, 4.0)
        val A = doubleArrayOf(1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        val minimiser = Minimiser(X, Y, A, 4.0, 0.0, 0)
        minimiser.setA(doubleArrayOf(0.0, 1.0))
        Assert.assertEquals(
                minimiser.gradient(),
                Matrix(doubleArrayOf(-10.00, -30.00))
        )
    }

    @Test
    fun costWithRegularization() {
        val Y = doubleArrayOf(2.0, 6.0, 8.0, 14.0)
        val X = doubleArrayOf(1.0, 3.0, 4.0, 7.0)
        val allowedA = doubleArrayOf(0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
        val A = doubleArrayOf(1.0)
        val lambda = 1.0

        val minimiser = Minimiser(X, Y, allowedA, 0.0, lambda, 0)
        minimiser.setA(A)

        Assert.assertEquals(minimiser.costWithRegularization(), 9.5, 0.01)
    }

    @Test
    fun mm(){
        val A = Matrix(arrayOf(
                doubleArrayOf(1.0, 2.0, 3.0),
                doubleArrayOf(4.0, 5.0, 6.0)
        ))
        val B = Matrix(arrayOf(
                doubleArrayOf(1.0, 2.0),
                doubleArrayOf(3.0, 4.0),
                doubleArrayOf(5.0, 6.0)
        ))
        val C = Matrix(arrayOf(
                doubleArrayOf(22.0, 28.0),
                doubleArrayOf(49.0, 64.0)
        ))
        Assert.assertEquals(A.mm(B), C)
    }
}