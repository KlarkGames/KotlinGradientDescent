package org.machine
import smile.math.matrix.Matrix

class Minimiser (_X: DoubleArray, _Y: DoubleArray, _A: DoubleArray, _alpha: Double, _lambda: Double, _iterations: Int) {
    private val X = ExtendedMatrix(_X)
    private val Y = ExtendedMatrix(_Y)
    private val m = X.nrows()
    private val n = _A.sum().toInt()
    private val alpha = _alpha
    private val lambda = _lambda
    private val iterations = _iterations
    private var A = Matrix.randn(n, 1).mul(100.0)
    private val allowedA = Matrix(_A)
    private val values = xDerivatives()
    private val der = values.first
    private val maximum = values.second
    private var normalizedX = der

    fun setA(_A: DoubleArray){
        this.A = Matrix(_A)
    }

    fun getYData(_X: DoubleArray) : DoubleArray {
        val tempX = ExtendedMatrix(_X)
        val tempDer = xDerivatives(tempX).first
        return tempDer.mm(A).transpose().toArray()[0]
    }

    fun gradient() : Matrix {
        val F = this.function()
        return normalizedX.tm(F.sub(1.0, Y)).mul(alpha / m)
    }

    fun function(): Matrix {
        return der.mm(A)
    }

    fun cost() : Double {
        val F = this.function()
        val difference = F.sub(1.0, Y)
        return((difference.mul(1.0, difference)).colSums()[0] / (m * 2.0))
    }

    fun costWithRegularization() : Double = cost() + A.mul(1.0, A).colSums()[0] * lambda / (m * 2.0)

    private fun gradientWithRegularization() : Matrix {
        val F = this.function()
        return normalizedX.transpose().mm(F.sub(1.0, F, 1.0, Y)).add(lambda, A).div(Y.nrows() * 1.0)
    }


    fun learn() : Matrix {
        normalization()
        for (i in 0..iterations) {
            val gradient = gradient()
            A = A.sub(alpha, gradient)
            println("Итерация: $i, Стоимость: ${cost()}")
        }
        println("Итоговая стоимость: ${cost()}\n")
        return A
    }

    fun learnWithRegularization() : Matrix {
        normalization()
        for (i in 0..iterations) {
            A = A.sub(alpha, gradientWithRegularization())
            println("Итерация: $i, Стоимость: ${costWithRegularization()}")
        }
        println("Итоговая стоимость: ${costWithRegularization()}\n")
        return A
    }

    private fun normalization() {
        for (i in 0 until m) {
            for (j in 0 until n) {
                normalizedX.set(i, j, der[i, j] / (Matrix(maximum)[j, 0]))
            }
        }
    }

    fun xDerivatives(X: ExtendedMatrix = this.X) : Pair<Matrix, DoubleArray> {
        val m = X.nrows()
        val modifiedX = ExtendedMatrix(m, n, 0.0)

        var i = 0

        if (allowedA.get(0, 0) == 1.0) {
            modifiedX.set(0, i, Matrix(m, 1, 1.0))
            i += 1
        }
        if (allowedA.get(1, 0) == 1.0) {
            modifiedX.set(0, i, X.clone())
            i += 1
        }
        if (allowedA.get(2, 0) == 1.0) {
            modifiedX.set(0, i, X.clone().pow(2.0))
            i += 1
        }
        if (allowedA.get(3, 0) == 1.0) {
            modifiedX.set(0, i, X.clone().pow(3.0))
            i += 1
        }
        if (allowedA.get(4, 0) == 1.0) {
            modifiedX.set(0, i, X.clone().pow(-1.0))
            i += 1
        }
        if (allowedA.get(5, 0) == 1.0) {
            modifiedX.set(0, i, X.clone().pow(-2.0))
            i += 1
        }
        if (allowedA.get(6, 0) == 1.0) {
            modifiedX.set(0, i, X.clone().pow(-3.0))
            i += 1
        }
        if (allowedA.get(7, 0) == 1.0) {
            modifiedX.set(0, i, X.clone().pow(1.0 / 2.0))
            i += 1
        }
        if (allowedA.get(8, 0) == 1.0) {
            modifiedX.set(0, i, X.clone().pow(1.0 / 3.0))
            i += 1
        }
        if (allowedA.get(9, 0) == 1.0) {
            modifiedX.set(0, i, X.clone().log())
            i += 1
        }
        if (allowedA.get(10, 0) == 1.0) {
            modifiedX.set(0, i, X.clone().sin())
            i += 1
        }
        if (allowedA.get(11, 0) == 1.0) {
            modifiedX.set(0, i, X.clone().cos())
            i += 1
        }

        var maximum = doubleArrayOf()
        for ( i in 0 until n) {
            maximum += modifiedX.transpose().toArray()[i].max()!!
        }

        return Pair(modifiedX, maximum)
    }

    fun learnNormalEquation() {
        A = der.tm(der).inverse().mt(der).mm(Y)
    }

    fun getNormalEquation() : Array<DoubleArray> {
        return der.tm(der).inverse().mt(der).mm(Y).toArray()
    }
}