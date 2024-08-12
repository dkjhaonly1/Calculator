package com.deepak.calculator

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import java.util.function.UnaryOperator

class MainActivity : AppCompatActivity() {
    private lateinit var txt_display_clicked_btn:TextView
    private lateinit var txt_result:TextView

    private lateinit var btn_backspace:ImageView

    private lateinit var card_clear:CardView
    private lateinit var card_percentage:CardView
    private lateinit var card_division:CardView
    private lateinit var card_btn_7:CardView
    private lateinit var card_btn_8:CardView
    private lateinit var card_btn_9:CardView
    private lateinit var card_multiply:CardView
    private lateinit var card_btn_4:CardView
    private lateinit var card_btn_5:CardView
    private lateinit var card_btn_6:CardView
    private lateinit var card_btn_minus:CardView
    private lateinit var card_btn_1:CardView
    private lateinit var card_btn_2:CardView
    private lateinit var card_btn_3:CardView
    private lateinit var card_btn_plus:CardView
    private lateinit var card_zero:CardView
    private lateinit var card_equal:CardView

    var firstNumber=""
    var secondNumber=""
    var currentNumber=""
    var currentOperator=""
    var isNewOperation = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        txt_display_clicked_btn= findViewById(R.id.txt_display_clicked_btn)
        txt_result= findViewById(R.id.txt_result)
        btn_backspace= findViewById(R.id.btn_backspace)
        card_clear= findViewById(R.id.card_clear)
        card_percentage= findViewById(R.id.card_percentage)
        card_division= findViewById(R.id.card_division)
        card_btn_7= findViewById(R.id.card_btn_7)
        card_btn_8= findViewById(R.id.card_btn_8)
        card_btn_9= findViewById(R.id.card_btn_9)
        card_multiply= findViewById(R.id.card_multiply)
        card_btn_4= findViewById(R.id.card_btn_4)
        card_btn_5= findViewById(R.id.card_btn_5)
        card_btn_6= findViewById(R.id.card_btn_6)
        card_btn_minus= findViewById(R.id.card_btn_minus)
        card_btn_1= findViewById(R.id.card_btn_1)
        card_btn_2= findViewById(R.id.card_btn_2)
        card_btn_3= findViewById(R.id.card_btn_3)
        card_btn_plus= findViewById(R.id.card_btn_plus)
        card_zero= findViewById(R.id.card_zero)
        card_equal= findViewById(R.id.card_equal)

        initializeButtons()


        btn_backspace.setOnClickListener {
            if (currentNumber.isEmpty() && currentOperator.isNotEmpty()) {
                // Remove the operator if there is no current number
                currentOperator = ""
            } else if (currentNumber.isNotEmpty()) {
                // Remove the last digit of the current number
                currentNumber = currentNumber.substring(0, currentNumber.length - 1)
            }
            updateDisplay()
        }



        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


    }
    private fun initializeButtons()
    {
        val buttonValues= arrayOf(
            "7","8","9","/",
            "4","5","6","*",
            "1","2","3","-",
            "0","C","=","+",
            "%"
        )

        val buttonIds= arrayOf(

            R.id.card_btn_7,R.id.card_btn_8,R.id.card_btn_9,R.id.card_division,
            R.id.card_btn_4,R.id.card_btn_5,R.id.card_btn_6,R.id.card_multiply,
            R.id.card_btn_1,R.id.card_btn_2,R.id.card_btn_3,R.id.card_btn_minus,
            R.id.card_zero,R.id.card_clear,R.id.card_equal,R.id.card_btn_plus,
            R.id.card_percentage
        )
        buttonIds.forEachIndexed { index, buttonId ->
           findViewById<CardView>(buttonId).setOnClickListener {
                handleButtonClick(buttonValues[index])
           }
        }

    }

    private fun handleButtonClick(value:String)
    {
        when(value){
            in "0".."9" ->appendDigit(value)

            "+" ->
                {
                    appendOperator("+" )
                    currentOperator="+"

                }
            "-" ->
                {
                    appendOperator("-")
                    currentOperator="-"
                }
            "*" ->
                {
                    appendOperator("*")
                    currentOperator="*"
                }
            "/" ->
                {
                    appendOperator("/")
                    currentOperator="/"
                }
            "%" ->
                {
                    appendOperator("%")
                    currentOperator="%"
                }

            "C" -> clearInput()
            "=" -> checkResult()
        }

    }

    private fun clearInput(){

        firstNumber=""
        secondNumber=""
        currentNumber=""
        txt_display_clicked_btn.setText("")
        txt_result.setText("")
        updateDisplay()

    }

    private fun checkResult(){

        if (firstNumber.isNotEmpty() && currentNumber.isNotEmpty()){

            secondNumber=currentNumber
            var result=performCalculation(currentOperator)
            txt_result.text = result.toString()
            firstNumber = result.toString() // Change this line
            currentNumber = ""
            currentOperator = ""
            isNewOperation = true           // Add this line
        }

    }



    private fun performCalculation(operator: String):Double
    {
        return when(operator)
        {
            "+" -> firstNumber.toDouble() + secondNumber.toDouble()
            "-" -> firstNumber.toDouble() - secondNumber.toDouble()
            "*" -> firstNumber.toDouble() * secondNumber.toDouble()
            "/" -> firstNumber.toDouble() / secondNumber.toDouble()
            "%" -> firstNumber.toDouble() % secondNumber.toDouble()
            else -> {throw IllegalArgumentException("Invalid Operator")}
        }

    }

    private fun percentageCalulation(operator: String)
    {
        //implement your percentage calculation logic
    }

    private fun appendDigit(digit:String)
    {
        if (isNewOperation) {           // Add this block
            currentNumber = ""
            isNewOperation = false
        }

        currentNumber +=digit

        updateDisplay()

    }


    private fun appendOperator(operator: String)
    {
        if (currentNumber.isNotEmpty()){
            firstNumber=currentNumber
            currentNumber= ""
        }
        currentOperator = operator      // Change this line
        updateDisplay()

    }


    private fun updateDisplay() {
        txt_display_clicked_btn.text = if (currentOperator.isNotEmpty()) {
            "$firstNumber $currentOperator $currentNumber" // Change this line
        } else {
            currentNumber
        }
    }



}