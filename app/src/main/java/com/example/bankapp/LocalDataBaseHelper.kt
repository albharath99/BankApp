package com.example.bankapp

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import kotlin.random.Random

class LocalDataBaseHelper(context: Context) {
    private var db: SQLiteDatabase
    private var mContext: Context
    private val TAG = javaClass.simpleName
    private var CREATE_TABLE = "CREATE TABLE IF NOT EXISTS USER_TABLE" +
            "( ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " NAME VARCHAR," +
            " MOBILE_NUMBER INTEGER," +
            " AGE INTEGER, " +
            "BALANCE INTEGER DEFAULT 15, " +
            "CARD_NUMBER VARCHAR )"

    init {
        mContext = context
        db = context.openOrCreateDatabase("myDb",Context.MODE_PRIVATE,null)
        db.execSQL(CREATE_TABLE)
        db.close()
    }

    private fun generateCardNumber(): String {
        val cardNum = Random.nextInt(999999)
        return String.format("%06d",cardNum)
    }

    fun insertNewUser(name: String, mobileNumber: Int, age: Int): String {
        Log.d(TAG,"Insert user: $name, $mobileNumber, $age")
        db = mContext.openOrCreateDatabase("myDb", Context.MODE_PRIVATE, null)


        val cardNumber = generateCardNumber()
        val values = ContentValues()
        values.put("NAME",name)
        values.put("MOBILE_NUMBER", mobileNumber)
        values.put("AGE", age)
        values.put("CARD_NUMBER",cardNumber)

        val ret = db.insert("USER_TABLE","ID",values)
        db.close()
        if(ret.toInt() == -1) {
            Toast.makeText(mContext,"USER CREATION UNSUCCESSFUL",Toast.LENGTH_LONG).show()
            return ""
        } else {
            Toast.makeText(mContext,"USER CARD NUMBER: $cardNumber",Toast.LENGTH_LONG).show()
            return cardNumber
        }

    }
    fun loginUser(cardNumber: Int): Boolean {
        db = mContext.openOrCreateDatabase("myDb", Context.MODE_PRIVATE, null)
        val cursor = db.rawQuery("Select * from User_table where card_number = $cardNumber", null)
        Log.d(TAG,"Login User >>")
        if(cursor.count != 0)
        {
            db.close()
            return true
        } else {
            db.close()
            return false
        }

    }

    @SuppressLint("Range")
    fun getBalance(cardNumber: Int): Int{
        var alreadyOpen = false
        if(!db.isOpen) {
            db = mContext.openOrCreateDatabase("myDb", Context.MODE_PRIVATE, null)
        } else {
            alreadyOpen = true
        }
        val GET_MONEY = "Select * from USER_Table where card_number = $cardNumber"
        val cursor = db.rawQuery(GET_MONEY, null)
        cursor.moveToFirst()
        val balance = cursor.getInt(cursor.getColumnIndex("BALANCE"))
        if(!alreadyOpen) {
            db.close()
        }
        return balance
    }

    fun topUpMoney(cardNumber: Int, amount: Int): Boolean {
        var done = false
        db = mContext.openOrCreateDatabase("myDb", Context.MODE_PRIVATE, null)
        if(db.isOpen) {
            var balance = amount + getBalance(cardNumber)
            val values = ContentValues()
            values.put("balance", balance)
            //db.update("User_table",values,"card_number = ?", arrayOf(cardNumber.toString()))
            //val cursor = db.rawQuery("update User_table set balance = $balance where card_number = $cardNumber", null)
            db.execSQL("update User_table set balance = $balance where card_number = $cardNumber")
            //cursor.close()

            db.close()
        }

        return done
    }
}