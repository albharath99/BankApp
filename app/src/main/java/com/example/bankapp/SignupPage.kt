package com.example.bankapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlin.random.Random

class SignupPage : AppCompatActivity() {
    private lateinit var name: EditText
    private lateinit var mobilenumber: EditText
    private lateinit var age: EditText
    private lateinit var submit: Button
    private lateinit var accountNumber: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        name = findViewById(R.id.name)
        mobilenumber = findViewById(R.id.mobilenumber)
        age = findViewById(R.id.age)
        accountNumber = findViewById(R.id.accountNumber)
        submit = findViewById(R.id.submit)

        submit.setOnClickListener{
            accountNumber.text = "${accountNumber.text} ${getNewAccountNumberFromDatabase().toString()}"
            accountNumber.visibility = TextView.VISIBLE
        }
    }

    private fun getNewAccountNumberFromDatabase(): Int {
        //send Data to DB and get account number from DB
        var x = (1..100).random()
        return x
    }
}