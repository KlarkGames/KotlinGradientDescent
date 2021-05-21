package org.machine

import org.junit.Assert
import org.junit.Test
import kotlin.math.PI

class ExtendedMatrixTest {

    @Test
    fun pow() {
        Assert.assertEquals(
            ExtendedMatrix(doubleArrayOf(2.0, -4.0)).pow(2.0),
            ExtendedMatrix(doubleArrayOf(4.0, 16.0))
        )
    }

    @Test
    fun log() {
        Assert.assertEquals(
            ExtendedMatrix(doubleArrayOf(1.0, 4.0)).log(),
            ExtendedMatrix(doubleArrayOf(0.0, 2.0))
        )
    }

    @Test
    fun sin() {
        Assert.assertEquals(
            ExtendedMatrix(doubleArrayOf(0.0, PI / 2)).sin(),
            ExtendedMatrix(doubleArrayOf(0.0, 1.0))
        )
    }

    @Test
    fun cos() {
        Assert.assertEquals(
            ExtendedMatrix(doubleArrayOf(0.0, PI / 2)).cos(),
            ExtendedMatrix(doubleArrayOf(1.0, 0.0))
        )
    }
}