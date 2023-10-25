package com.example.calculatorapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.rajkishorbgp.calculatorapp.databinding.ActivityMainBinding
import net.objecthunter.exp4j.Expression
import net.objecthunter.exp4j.ExpressionBuilder

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    private var inputList = ""
    private var f = false
    private lateinit var expression: Expression

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setNumberClickListeners()
        setOperationClickListeners()

        binding.buttonEqual.setOnClickListener {
            onEqual()
            binding.calcScreen.text=binding.outputScreen.text.toString()
            binding.outputScreen.text= ""
        }

        binding.clearAll.setOnClickListener {
            // Clear all inputs and reset the calculator
            clearInputs()
            binding.calcScreen.text=inputList
        }
        binding.backSpace.setOnClickListener {
            if (inputList.isNotEmpty()) {
                inputList = inputList.dropLast(1)
                binding.calcScreen.text = inputList
                val isOperator = "1234567890"
                if (inputList.isNotEmpty() && inputList.last() in isOperator){
                    onEqual()
                }else if(inputList.isNotEmpty() && inputList.last() in isOperator){
                    binding.outputScreen.text=inputList
                }else if(inputList.isEmpty()){
                    binding.outputScreen.text=inputList
                }
            }
        }
    }

    private fun setNumberClickListeners() {
        val numberButtons = listOf(
            binding.button0, binding.button1, binding.button2, binding.button3,
            binding.button4, binding.button5, binding.button6, binding.button7,
            binding.button8, binding.button9, binding.buttonDot
        )

        for (button in numberButtons) {
            button.setOnClickListener {
                inputList += button.text.toString()
                binding.calcScreen.text=inputList
                onEqual()
            }
        }
    }

    private fun setOperationClickListeners() {
        val operationButtons = listOf(
            binding.buttonPlus, binding.buttonMinus, binding.buttonMultiple,
            binding.buttonDivide
        )

        for (button in operationButtons) {
            button.setOnClickListener {
                inputList += button.text.toString()
                binding.calcScreen.text=inputList
            }
        }
    }

    private fun clearInputs() {
        inputList =""
        binding.calcScreen.text=""
        binding.outputScreen.text=""
    }

    private fun onEqual() {
        val txt = binding.calcScreen.text.toString()
        val replaced = txt.replace("x", "*")
        expression = ExpressionBuilder(replaced).build()
        try {
            val result = expression.evaluate()
            binding.outputScreen.text = result.toString()
        } catch (ex: ArithmeticException) {
            Log.e("EvaluateError", ex.toString())
            binding.outputScreen.text = "Error"

        }
    }
}
