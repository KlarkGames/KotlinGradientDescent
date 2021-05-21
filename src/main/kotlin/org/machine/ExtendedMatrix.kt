package org.machine

import smile.math.matrix.Matrix
import kotlin.math.*

class ExtendedMatrix : Matrix {
    constructor(m: Int, n: Int) : super(m, n)
    constructor(m: Int, n: Int, a: Double) : super(m, n, a)
    constructor(m: Int, n: Int, A: Array<DoubleArray>) : super(m, n, A)
    constructor(A: Array<DoubleArray>) : super(A)
    constructor(A: DoubleArray) : super(A)

    fun pow(i: Int, j: Int, b: Double): Double {
        val y = this.get(i, j).pow(b)
        this.set(i, j, y)
        return y
    }

    fun log(i: Int, j: Int): Double {
        val y = log2(this.get(i, j))
        this.set(i, j, y)
        return y
    }

    fun sin(i: Int, j: Int): Double {
        val y = sin(this.get(i, j))
        this.set(i, j, y)
        return y
    }

    fun cos(i: Int, j: Int): Double {
        val y = cos(this.get(i, j))
        this.set(i, j, y)
        return y
    }

    fun pow(b: Double): Matrix? {
        for (j in 0 until this.ncols()) {
            for (i in 0 until this.nrows()) {
                this.pow(i, j, b)
            }
        }
        return this
    }

    fun log(): Matrix? {
        for (j in 0 until this.ncols()) {
            for (i in 0 until this.nrows()) {
                this.log(i, j)
            }
        }
        return this
    }

    fun sin(): Matrix? {
        for (j in 0 until this.ncols()) {
            for (i in 0 until this.nrows()) {
                this.sin(i, j)
            }
        }
        return this
    }

    fun cos(): Matrix? {
        for (j in 0 until this.ncols()) {
            for (i in 0 until this.nrows()) {
                this.cos(i, j)
            }
        }
        return this
    }

     override fun clone() : ExtendedMatrix{
        val m = this.nrows()
        val n = this.ncols()
        val matrix = ExtendedMatrix(m, n)

        for (j in 0 until n) {
            for (i in 0 until m) {
                matrix[i, j] = this[i, j]
            }
        }

        return matrix
    }
}