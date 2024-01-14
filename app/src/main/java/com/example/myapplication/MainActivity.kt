package com.example.myapplication

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.TranslateAnimation
import android.widget.Button
import android.widget.EditText
import android.widget.Switch
import android.widget.TextView
import kotlin.math.roundToInt

class MainActivity : AppCompatActivity() {
    @SuppressLint("MissingInflatedId", "SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // UI elements initialization
        val height: EditText = findViewById(R.id.height_editTextNumber)
        val weight: EditText = findViewById(R.id.weight_editTextNumber)
        val age: EditText = findViewById(R.id.age_editTextNumber)

        val result : TextView = findViewById(R.id.result_textView)
        val additionalInfo: TextView = findViewById(R.id.additional_textView)
        val button: Button = findViewById(R.id.button)

        val calories: Button = findViewById(R.id.Calories_Button)

        val caloriesTextView: TextView = findViewById(R.id.calories_textView)
        val gender: Switch = findViewById(R.id.gender_switch)

        // OnClickListener for calculating daily calorie intake
        calories.setOnClickListener{
            var calorie = 0.0
            calorie = if(gender.isChecked){
                447.593 + 9.247 * weight.text.toString().toFloat() + 3.098 * height.text.toString().toFloat() + 4.330 * age.text.toString().toFloat()

            } else{
                88.362 + 13.397 * weight.text.toString().toFloat() + 4.799 * height.text.toString().toFloat() + 5.677 * age.text.toString().toFloat()
            }
            val roundedCalorie = String.format("%.0f", calorie)


            // Animating TextView to display calculated calorie intake
            caloriesTextView.animate()
                .alpha(0f) // Set the target alpha
                .setDuration(100) // duration in milliseconds
                .withEndAction {
                    caloriesTextView.text = "Your basic daily calorie intake is $roundedCalorie (activity not included)"

                    caloriesTextView.animate().alpha(1f).duration = 500
                }
                .start()

            caloriesTextView.animate()
        }

        // OnClickListener for calculating BMI
        button.setOnClickListener {
            caloriesTextView.text = ""

            // Validation checks for input values
            if (height.text.toString().isEmpty() || weight.text.toString().isEmpty() || age.text.toString().isEmpty()){
                result.text = "Please fill in all the fields"
                result.setTextColor(Color.parseColor("#ff0000"))

                additionalInfo.text = ""
                calories.visibility = Button.INVISIBLE
                caloriesTextView.visibility = TextView.INVISIBLE
                return@setOnClickListener
            }
            if(height.text.toString().toFloat() > 300 || weight.text.toString().toFloat() > 300 || age.text.toString().toFloat() > 150){
                result.text = "Please enter valid values"
                result.setTextColor(Color.parseColor("#ff0000"))

                additionalInfo.text = ""
                calories.visibility = Button.INVISIBLE
                caloriesTextView.visibility = TextView.INVISIBLE

                return@setOnClickListener
            }
            if(height.text.toString().toFloat() < 50 || weight.text.toString().toFloat() < 20 || age.text.toString().toFloat() < 5){
                result.text = "Please enter valid values"
                result.setTextColor(Color.parseColor("#ff0000"))

                additionalInfo.text = ""
                calories.visibility = Button.INVISIBLE
                caloriesTextView.visibility = TextView.INVISIBLE

                return@setOnClickListener
            }

            // Displaying BMI and additional information
            result.setTextColor(Color.parseColor("#ffffff"))
            caloriesTextView.visibility = TextView.VISIBLE

            val heightValue = height.text.toString().toFloat()
            val weightValue = weight.text.toString().toFloat()
            val bmi = ((weightValue / (heightValue * heightValue)) * 10000)
            if (bmi < 18.5){
                additionalInfo.setTextColor(Color.parseColor("#00d2ff"))
                additionalInfo.text = "Underweight"

            }
            else if (bmi in 18.5..24.9){
                additionalInfo.setTextColor(Color.parseColor("#00FF00"))
                additionalInfo.text = "Normal"
            }
            else if (bmi in 25.0..29.9){
                additionalInfo.setTextColor(Color.parseColor("#d24e01"))
                additionalInfo.text = "Overweight"
            }
            else if (bmi >= 30){
                additionalInfo.setTextColor(Color.parseColor("#FF0000"))
                additionalInfo.text = "Obese"
            }
            val roundedBmi = String.format("%.1f", bmi)
            calories.visibility = Button.VISIBLE

            // Animating TextView to display calculated BMI
            result.animate()
                .alpha(0f) //  target alpha
                .setDuration(100) // duration in milliseconds
                .withEndAction {
                    result.text = "Your BMI is $roundedBmi"
                    result.animate().alpha(1f).duration = 500
                }
                .start()

            result.animate()



        }
    }
}

