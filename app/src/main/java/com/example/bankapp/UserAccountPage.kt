package com.example.bankapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class UserAccountPage : AppCompatActivity() {
    private lateinit var balanceTv: TextView
    private lateinit var topUpBt: Button
    private lateinit var transferBt: Button
    private lateinit var enterCardNumEt: EditText
    private lateinit var amountEt: EditText
    private lateinit var clickTv: TextView


    private lateinit var db: LocalDataBaseHelper
    private final val TAG = javaClass.simpleName
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_user_account_page)

        db = LocalDataBaseHelper(applicationContext)
        balanceTv = findViewById(R.id.balanceTv)
        topUpBt = findViewById(R.id.topUpBt)
        transferBt = findViewById(R.id.transferBt)
        enterCardNumEt = findViewById(R.id.enterCardNo)
        amountEt = findViewById(R.id.amount)
        clickTv = findViewById(R.id.click)

        val cardNumber = intent.getIntExtra("cardNumber", 0)
        Log.d(TAG,"Card number: $cardNumber")

        val balance = db.getBalance(cardNumber)
        balanceTv.text = balance.toString()
        balanceTv.visibility = TextView.VISIBLE
        Log.d(TAG,"Balance: $balance")

        topUpBt.setOnClickListener {
            if(clickTv.visibility == TextView.INVISIBLE) {
                amountEt.text.clear()
                amountEt.visibility = EditText.VISIBLE
                clickTv.text = "Press Topup again after entering amount"
                clickTv.visibility = TextView.VISIBLE
            } else {
                val amount = amountEt.text.toString().toInt()
                val done = db.topUpMoney(cardNumber,amount)
                Toast.makeText(applicationContext,"Top up succesful", Toast.LENGTH_SHORT).show()
                amountEt.visibility = EditText.INVISIBLE
                clickTv.visibility = TextView.INVISIBLE
                balanceTv.text = db.getBalance(cardNumber).toString()
            }
        }

        transferBt.setOnClickListener {
            if(clickTv.visibility == TextView.INVISIBLE) {
                Log.d(TAG,"CLick invisible")
                amountEt.text.clear()
                amountEt.visibility = EditText.VISIBLE
                enterCardNumEt.text.clear()
                enterCardNumEt.visibility = EditText.VISIBLE
                clickTv.text = "Press transfer again after entering amount and recepient card number"
                clickTv.visibility = TextView.VISIBLE
            } else {
                Log.d(TAG,"CLick visible")
                val amount = amountEt.text.toString().toInt()
                val targetCardNumber = enterCardNumEt.text.toString().toInt()
                if(db.transferMoney(cardNumber, targetCardNumber, amount)){
                    Toast.makeText(applicationContext,"Transfer succesful", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(applicationContext,"Transfer unsuccesful", Toast.LENGTH_SHORT).show()
                }
                amountEt.visibility = EditText.INVISIBLE
                clickTv.visibility = TextView.INVISIBLE
                enterCardNumEt.visibility = EditText.INVISIBLE
                balanceTv.text = db.getBalance(cardNumber).toString()
            }
        }
    }
}