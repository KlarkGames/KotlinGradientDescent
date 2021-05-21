package org.machine

import org.apache.log4j.BasicConfigurator

fun main(args: Array<String>) {
    BasicConfigurator.configure(); //Без этого он жалуется
    val Y = doubleArrayOf(3.117, 39.0, 1.875, -3.877)
    val X = doubleArrayOf(0.3, 3.0, -1.5, -2.3)
    val A = doubleArrayOf(1.0, 1.0, 1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    val alpha = 0.005
    val iterations = 1000
    val lambda = 0.0

    val minimiser = Minimiser(X, Y, A, alpha, lambda, iterations)
    val values = minimiser.learn()
    println(values)

    val al = minimiser.getNormalEquation()
    print(al.joinToString{ it.toString() })
}

fun alphaTest(
        X: DoubleArray,
        Y: DoubleArray,
        A: DoubleArray,
        iterations: Int,
        startAlpha: Double,
        endAlpha: Double,
        step: Double,
        amount: Int = 1
) {
    var a = startAlpha
    while (a < endAlpha) {
        var k = 0.0
        println("Alpha: $a, Iterations: $iterations:")
        for (i in 0..amount) {
            val minimiser = Minimiser(X, Y, A, a, 0.0, iterations)
            minimiser.learn()
            println("____Try $i: Cost = ${minimiser.cost()}")
            k += minimiser.cost()
        }
        println("Average Cost: ${k / amount}\n")
        a += step
    }
}

