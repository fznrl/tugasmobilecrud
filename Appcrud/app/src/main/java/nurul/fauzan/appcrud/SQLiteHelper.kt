package nurul.fauzan.appcrud

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf
import androidx.core.graphics.rotationMatrix
import java.lang.Exception

class SQLiteHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{

        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "name.db"
        private const val TBL_NAME = "tbl_name"
        private const val ID = "id"
        private const val NAME = "name"
        private const val EMAIL = "email"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTblName = ("CREATE TABLE " + TBL_NAME + "("
                + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + EMAIL + " TEXT" + ")")
        db?.execSQL(createTblName)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TBL_NAME")
        onCreate(db)
    }

    fun insertName(nme: NameModel): Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, nme.id)
        contentValues.put(NAME, nme.name)
        contentValues.put(EMAIL, nme.email)

        val success = db.insert(TBL_NAME, null, contentValues)
        db.close()
        return success
    }

    fun getAllName(): ArrayList<NameModel>{
        val nmeList: ArrayList<NameModel> = ArrayList()
        val selectQuery = "SELECT * FROM $TBL_NAME"
        val db = this.readableDatabase

        val cursor: Cursor?

        try{
            cursor = db.rawQuery(selectQuery, null)
        }catch (e: Exception){
            e.printStackTrace()
            db.execSQL(selectQuery)
            return ArrayList()
        }

        var id: Int
        var name: String
        var email: String

        if (cursor.moveToFirst()) {
            do {
                id = cursor.getInt(cursor.getColumnIndex("id"))
                name = cursor.getString(cursor.getColumnIndex("name"))
                email = cursor.getString(cursor.getColumnIndex("email"))

                val nme = NameModel(id = id, name = name, email = email)
                nmeList.add(nme)
            }while (cursor.moveToNext())
        }
        return nmeList
    }

    fun updateName(nme: NameModel): Int{
        val db = this.writableDatabase
        val contentValues = ContentValues()
        contentValues.put(ID, nme.id)
        contentValues.put(NAME, nme.name)
        contentValues.put(EMAIL, nme.email)

        val success = db.update(TBL_NAME,contentValues,"id=" + nme.id, null)
        db.close()
        return success
    }

    fun deleteNameById(id: Int): Int{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(ID, id)

        val success = db.delete(TBL_NAME, "id=$id", null)
        db.close()
        return success
    }
}