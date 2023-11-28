package com.example.bankapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class LoginPage : AppCompatActivity() {
    private lateinit var login: Button
    private lateinit var cardNumberEt: EditText
    private lateinit var db: LocalDataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login_page)

        db = LocalDataBaseHelper(applicationContext)
        login = findViewById(R.id.login2)
        cardNumberEt = findViewById(R.id.cardNumber2)

        login.setOnClickListener {
            val cardNumber = cardNumberEt.text.toString().toInt()
            if(db.loginUser(cardNumber)) {
                Toast.makeText(applicationContext,"Card number exists", Toast.LENGTH_LONG).show()
                val intent = Intent(this, UserAccountPage::class.java)
                intent.putExtra("cardNumber", cardNumber)
                startActivity(intent)
            } else {
                Toast.makeText(applicationContext,"Card number does not exist", Toast.LENGTH_LONG).show()
            }
        }
    }
}