package com.example.expenses.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.icu.text.SimpleDateFormat
import android.os.Build
import android.widget.Toast
import androidx.annotation.RequiresApi
import com.example.mcproject.models.User
import java.util.*


/**
 * This class defines all SQLite CRUD functions
 * */
class DBHandler(var context: Context): SQLiteOpenHelper(context,DATABASE_NAME ,null,
    DATABASE_VERSION){
    override fun onCreate(db: SQLiteDatabase?) {
        val createUser = "CREATE TABLE user(" +
                "USER_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "USERNAME TEXT NOT NULL, " +
                "PROFILE TEXT NOT NULL)"
        val createTranx = "CREATE TABLE user_record(" +
                "RECORD_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ITEM_NAME TEXT NOT NULL," +
                "AMOUNT REAL NOT NULL," +
                "TRANS_TYPE TEXT NOT NULL," +
                "TRANS_DATE DATE NOT NULL," +
                "USER_ID INTEGER NOT NULL," +
                "FOREIGN KEY (USER_ID) REFERENCES user (USER_ID))"
        val createSales = "CREATE TABLE sales( " +
                "SALES_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "ITEM VARCHAR(20) NOT NULL," +
                "QTY INTEGER NOT NULL," +
                "AMOUNT REAL NOT NULL," +
                "TRANS_DATE DATE NOT NULL," +
                "USER_ID INTEGER NOT NULL," +
                "FOREIGN KEY (USER_ID) REFERENCES user (USER_ID))"
        val createReceipt = "CREATE TABLE receipt(" +
                "USER_ID INTEGER NOT NULL," +
                "ITEM_NAME REAL NOT NULL," +
                "ITEM_IMAGE BLOB," +
                "RECORD_ID INTEGER NOT NULL," +
                "FOREIGN KEY (USER_ID) REFERENCES user (USER_ID)," +
                "FOREIGN KEY (RECORD_ID) REFERENCES user_record (RECORD_ID))"

        db?.execSQL(createUser)
        db?.execSQL(createTranx)
        db?.execSQL(createSales)
        db?.execSQL(createReceipt)
    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
//        db?.execSQL("DROP TABLE IF EXISTS user")
//        onCreate(db)

    }

    /** This function inserts income and expenses into the transaction table
    **/
    fun insertRow(userObj: User){
        val cv = ContentValues()
        cv.put("USERNAME", userObj.username)
        cv.put("PROFILE", userObj.profile)

        val db = this.writableDatabase
        var result = db.insert("user",null, cv)
        if (result == (-1).toLong()){
            Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
        }
        else{
            Toast.makeText(context, "Profile Created", Toast.LENGTH_SHORT).show()
        }
        db.close()
    }

    /**This function timestamps a record with the current system time*/
    @RequiresApi(Build.VERSION_CODES.N)
    private fun getDateTime(): String? {
        val dateFormat = SimpleDateFormat(
            "yyyy-MM-dd HH:mm:ss", Locale.getDefault()
        )
        val date = Date()
        return dateFormat.format(date)
    }

    /**this function checks if a user has a registered profile*/
    @RequiresApi(Build.VERSION_CODES.N)
    fun readTrnx(item: String, trnx_type: String, amount: Double, user:String){
        val db = this.writableDatabase
        var user_id=""
        val query =
            "SELECT user_id FROM user where username =\"$user\""
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)
        }
        if (cursor != null) {
            if (cursor.count != 0) {
                while (cursor.moveToNext()) {
                    user_id = cursor.getString(0)
                }
            }
        }

        if(user_id != ""){
            val cv = ContentValues()
            cv.put("ITEM_NAME", item)
            cv.put("AMOUNT", amount)
            cv.put("TRANS_TYPE", trnx_type)
            cv.put("TRANS_DATE", getDateTime() )
            cv.put("USER_ID", user_id.toInt())

            var result = db.insert("user_record",null, cv)
            if (result == (-1).toLong()){
                Toast.makeText(context, "Failed", Toast.LENGTH_SHORT).show()
            }
            else{
                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show()
            }
        }
        else{
            Toast.makeText(context, "User does not exist!", Toast.LENGTH_SHORT).show()}

        db.close()
    }

    /**this function retrieves all transaction recore
     * from sqlite database
     * */
    fun readTransaction(tran_type:String): Cursor? {
        val query =
            "SELECT $TRAN_DATE,$ITEM_NAME,$AMOUNT FROM user_record where trans_type =\"$tran_type\""
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query, null)

        }
        return cursor
    }

    /**
     * this function returns and arraylist of the
     * individual sum of income and expenses
     * */
    fun getTotal(): ArrayList<String> {
        val total = ArrayList<String>()
        val query1 =
            "SELECT sum(amount) FROM user_record where trans_type =\"Income\""
        val query2 =
            "SELECT sum(amount) FROM user_record where trans_type =\"Expenses\""
        val db = this.readableDatabase
        var cursor: Cursor? = null
        if (db != null) {
            cursor = db.rawQuery(query1, null)
        }
        if (cursor != null) {
            if (cursor.count != 0) {
                while (cursor.moveToNext()) {
                    total.add(cursor.getString(0))
                }
            }
        }
        if (db != null) {
            cursor = db.rawQuery(query2, null)
        }
        if (cursor != null) {
            if (cursor.count != 0) {
                while (cursor.moveToNext()) {
                    total.add(cursor.getString(0))
                }
            }
        }
        return total
    }

    companion object{
        // here we have defined variables for our database
        private val DATABASE_NAME = "DB"
        private val DATABASE_VERSION = 1
        val TABLE_NAME = "gfg_table"
        val TRAN_DATE = "trans_date"
        val ITEM_NAME = "item_name"
        val AMOUNT = "amount"

    }

}