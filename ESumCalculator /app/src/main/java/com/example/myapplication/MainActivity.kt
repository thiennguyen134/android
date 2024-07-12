package com.example.myapplication

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var display: TextView
    private lateinit var button1: Button
    private lateinit var button2: Button
    private lateinit var button3: Button
    private lateinit var button4: Button
    private lateinit var button5: Button
    private lateinit var button6: Button
    private lateinit var button7: Button
    private lateinit var button8: Button
    private lateinit var button9: Button
    private lateinit var buttonAdd: Button
    private lateinit var buttonEquals: Button
    private lateinit var buttonAC: Button
    private var operand1: Int = 0
    private var operator: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        display = findViewById(R.id.display)
        button1 = findViewById(R.id.button_1)
        button2 = findViewById(R.id.button_2)
        button3 = findViewById(R.id.button_3)
        button4 = findViewById(R.id.button_4)
        button5 = findViewById(R.id.button_5)
        button6 = findViewById(R.id.button_6)
        button7 = findViewById(R.id.button_7)
        button8 = findViewById(R.id.button_8)
        button9 = findViewById(R.id.button_9)
        buttonAdd = findViewById(R.id.button_add)
        buttonEquals = findViewById(R.id.button_equals)
        buttonAC = findViewById(R.id.button_AC)

        button1.setOnClickListener { appendNumber(1) }
        button2.setOnClickListener { appendNumber(2) }
        button3.setOnClickListener { appendNumber(3) }
        button4.setOnClickListener { appendNumber(4) }
        button5.setOnClickListener { appendNumber(5) }
        button6.setOnClickListener { appendNumber(6) }
        button7.setOnClickListener { appendNumber(7) }
        button8.setOnClickListener { appendNumber(8) }
        button9.setOnClickListener { appendNumber(9) }
        buttonAdd.setOnClickListener { setOperator("+") }
        buttonEquals.setOnClickListener { evaluateExpression() }
        buttonAC.setOnClickListener { resetCalculator() }
    }

    private fun appendNumber(number: Int) {
        display.append(number.toString())
    }

    private fun setOperator(op: String) {
        val displayText = display.text.toString()
        if (displayText.isNotEmpty()) {
            operand1 = displayText.toInt()
            operator = op
            display.text = ""
        }
    }

    private fun evaluateExpression() {
        val displayText = display.text.toString()
        if (operator != null && displayText.isNotEmpty()) {
            val operand2 = displayText.toInt()
            val result = when (operator) {
                "+" -> operand1 + operand2
                "-" -> operand1 - operand2
                "*" -> operand1 * operand2
                "/" -> operand1 / operand2
                else -> throw IllegalArgumentException("Invalid operator")
            }
            display.text = result.toString()
            operand1 = result
            operator = null
        }
    }

    private fun resetCalculator() {
        display.text = ""
        operand1 = 0
        operator = null
    }
}
