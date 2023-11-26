package com.example.bankapp

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import android.widget.Toast
import kotlin.random.Random

class LocalDataBaseHelper(context: Context) {
    private var db: SQLiteDatabase
    private var mContext: Context
    private final val TAG = javaClass.simpleName
    private var CREATE_TABLE = "CREATE TABLE IF NOT EXISTS USER_TABLE" +
            "( ID INTEGER PRIMARY KEY AUTOINCREMENT," +
            " NAME VARCHAR," +
            " MOBILE_NUMBER INTEGER," +
            " AGE INTEGER, " +
            "BALANCE INTEGER DEFAULT 0, " +
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
}