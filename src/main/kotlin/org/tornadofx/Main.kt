package org.tornadofx

import javafx.application.Application
import javafx.beans.property.SimpleStringProperty
import javafx.collections.FXCollections
import javafx.scene.canvas.Canvas
import javafx.scene.layout.*
import javafx.scene.paint.Color
import tornadofx.*
import org.machine.Minimiser
import org.w3c.dom.events.EventTarget
import java.awt.TextField
import java.lang.Exception

class MainView : View("Gradient Descent Minimization") {
    override val root = BorderPane()

    private lateinit var canvas : Canvas
    private lateinit var label : javafx.scene.control.Label
    var circleRadius = 3.0

    private var X = doubleArrayOf()
    private var Y = doubleArrayOf()
    private var A = doubleArrayOf(1.0, 1.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0)
    var alpha = 0.0002
    var lambda = 0.004
    var iterations = 5000
    private lateinit var model : Minimiser


    init{
        with(root) {
            setWindowMinSize(1000.0, 550.0)
            top {
                menubar {
                    menu("Edit") {
                        item("Iterations") {
                            action {
                                val newScope = Scope(this@MainView)
                                find<IterationsGetter>(newScope).openModal(block = true)
                            }
                        }
                        item("Alpha") {
                            action {
                                val newScope = Scope(this@MainView)
                                find<AlphaGetter>(newScope).openModal(block = true)
                            }
                        }
                        item("Lambda") {
                            action {
                                val newScope = Scope(this@MainView)
                                find<LambdaGetter>(newScope).openModal(block = true)
                            }
                        }
                    }
                    menu("Model") {
                        menu("Learn") {
                            item("NormalEquation") {
                                action {
                                    try{
                                        if (X.isNotEmpty() || Y.isNotEmpty()) {
                                            model = Minimiser(X, Y, A, alpha, lambda, iterations)
                                            model.learnNormalEquation()
                                            label.text = "Learned Normal Equation"
                                        } else {
                                            label.text = "Not enough data"
                                        }
                                    } catch (e: Exception) {
                                        print(e.message)
                                        label.text = "Illegal Parameters"
                                    }
                                }
                            }
                            item("Gradient Descent") {
                                action {
                                    model = Minimiser(X, Y, A, alpha, lambda, iterations)
                                    model.learn()
                                    try{
                                        if (X.isNotEmpty() || Y.isNotEmpty()) {
                                            model = Minimiser(X, Y, A, alpha, lambda, iterations)
                                            model.learn()
                                            label.text = "Learned Gradient Descent"
                                        } else {
                                            label.text = "Not enough data"
                                        }
                                    } catch (e: Exception) {
                                        print(e.message)
                                        label.text = "Illegal Parameters"
                                    }
                                }
                            }
                            item("Gradient Descent with Regularization") {
                                action {
                                    try{
                                        if (X.isNotEmpty() || Y.isNotEmpty()) {
                                            model = Minimiser(X, Y, A, alpha, lambda, iterations)
                                            model.learnWithRegularization()
                                            label.text = "Learned Gradient Descent with Regularization"
                                        } else {
                                            label.text = "Not enough data"
                                        }
                                    } catch (e: Exception) {
                                        print(e.message)
                                        label.text = "Illegal Parameters"
                                    }
                                }
                            }
                        }
                        menu("Functions") {
                            radiomenuitem("Const") {
                                this.isSelected = true
                                action {
                                    if (this.isSelected) {
                                        A[0] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[0] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                            radiomenuitem("X") {
                                this.isSelected = true
                                action {
                                    if (this.isSelected) {
                                        A[1] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[1] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                            radiomenuitem("X²") {
                                action {
                                    if (this.isSelected) {
                                        A[2] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[2] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                            radiomenuitem("X³") {
                                action {
                                    if (this.isSelected) {
                                        A[3] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[3] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                            radiomenuitem("1/X") {
                                action {
                                    if (this.isSelected) {
                                        A[4] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[4] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                            radiomenuitem("1/X²") {
                                action {
                                    if (this.isSelected) {
                                        A[5] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[5] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                            radiomenuitem("1/X³") {
                                action {
                                    if (this.isSelected) {
                                        A[6] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[6] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                            radiomenuitem("√X") {
                                action {
                                    if (this.isSelected) {
                                        A[7] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[7] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                            radiomenuitem("³√X") {
                                action {
                                    if (this.isSelected) {
                                        A[8] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[8] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                            radiomenuitem("log₂X") {
                                action {
                                    if (this.isSelected) {
                                        A[9] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[9] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                            radiomenuitem("sinX") {
                                action {
                                    if (this.isSelected) {
                                        A[10] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[10] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                            radiomenuitem("coxX") {
                                action {
                                    if (this.isSelected) {
                                        A[11] = 1.0
                                        this.isSelected = true
                                    } else {
                                        A[11] = 0.0
                                        this.isSelected = false
                                    }
                                }
                            }
                        }
                        item("Draw") {
                            action {
                                if (X.isNotEmpty() || Y.isNotEmpty()) {
                                    clearBoard()
                                    printData()
                                    printGraph()
                                    label.text = "Drawing completed"
                                }
                            }
                        }
                        item("Clear") {
                            action {
                                clearBoard()
                                X = doubleArrayOf()
                                Y = doubleArrayOf()
                                label.text = "Canvas is clear"
                            }
                        }
                    }
                }
            }
            center {
                canvas = canvas {
                    setOnMouseClicked {
                        X += it.x
                        Y += it.y
                        printCircle(it.x, it.y, circleRadius)
                        label.text = "Point set"
                    }
                }
            }
            bottom {
                hbox {
                    style {
                        backgroundColor += Color.LIGHTGRAY
                    }
                    label = label(text = "Click to the canvas to set the point")
                }
            }
        }
        canvas.widthProperty().bind((canvas.parent as BorderPane).widthProperty())
        canvas.heightProperty().bind((canvas.parent as BorderPane).heightProperty() - 50)
    }

    fun printCircle(x: Double, y: Double, radius: Double) {
        canvas.graphicsContext2D.fill = Color.RED
        canvas.graphicsContext2D.fillOval(
            x - radius, y - radius,
            radius * 2, radius * 2
        )
    }

    fun printData() {
        for(i in X.indices) {
            printCircle(X[i], Y[i], circleRadius)
        }
    }

    fun clearBoard() {
        canvas.graphicsContext2D.clearRect(0.0, 0.0, canvas.width, canvas.height)
    }

    fun printGraph() {
        try {
            var tempX = doubleArrayOf()
            for (i in 1..canvas.width.toInt()) {
                tempX += i.toDouble()
            }
            val tempY = model.getYData(tempX)
            canvas.graphicsContext2D.fill = Color.BLACK
            canvas.graphicsContext2D.lineWidth = 2.0
            canvas.graphicsContext2D.moveTo(tempX[0], tempY[0])
            canvas.graphicsContext2D.beginPath()
            for (i in 1 until tempX.size) {
                canvas.graphicsContext2D.lineTo(tempX[i], tempY[i])
            }
            canvas.graphicsContext2D.stroke()
            canvas.graphicsContext2D.closePath()
        } catch (e: Exception) {
            label.text = "You should learn model first"
        }
    }
}

class AlphaGetter : Fragment("Alpha") {
    private val main: MainView by inject()
    var info = SimpleStringProperty(main.alpha.toString())
    override val root = form {
        fieldset {
            field("Alpha") {
                textfield(info)
            }
        }
        button("Set") {
            action {
                try {
                    main.alpha = info.value.toDouble()
                    close()
                } catch (e: Exception) {
                    info.value = "Illegal argument"
                }
            }
        }
    }
}

class LambdaGetter : Fragment("Lambda") {
    private val main: MainView by inject()
    var info = SimpleStringProperty(main.lambda.toString())
    override val root = form {
        fieldset {
            field("Lambda") {
                textfield(info)
            }
        }
        button("Set") {
            action {
                try {
                    main.lambda = info.value.toDouble()
                    close()
                } catch (e: Exception) {
                    info.value = "Illegal argument"
                }
            }
        }
    }
}

class IterationsGetter : Fragment("Iteration") {
    private val main: MainView by inject()
    var info = SimpleStringProperty(main.iterations.toString())
    override val root = form {
        fieldset {
            field("Iterations") {
                textfield(info)
            }
        }
        button("Set") {
            action {
                try {
                    main.iterations = info.value.toInt()
                    close()
                } catch (e: Exception) {
                    info.value = "Illegal argument"
                }
            }
        }
    }
}

class Gradient : App(MainView::class)

fun main(args: Array<String>) {
    Application.launch(Gradient::class.java, *args)
}
