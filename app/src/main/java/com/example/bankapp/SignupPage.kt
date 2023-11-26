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
    private lateinit var cardNumber: TextView
    private lateinit var db: LocalDataBaseHelper
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup_page)

        name = findViewById(R.id.name)
        mobilenumber = findViewById(R.id.mobilenumber)
        age = findViewById(R.id.age)
        cardNumber = findViewById(R.id.cardNumber)
        submit = findViewById(R.id.submit)
        db = LocalDataBaseHelper(applicationContext)

        submit.setOnClickListener{
            val cardNumber = getNewCardNumberFromDatabase()
            this.cardNumber.text = "${this.cardNumber.text} $cardNumber"
            this.cardNumber.visibility = TextView.VISIBLE
        }
    }

    private fun getNewCardNumberFromDatabase(): String {
        return db.insertNewUser(name.text.toString(), mobilenumber.text.toString().toInt(), age.text.toString().toInt())
    }
}