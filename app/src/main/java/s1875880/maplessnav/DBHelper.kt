package s1875880.maplessnav

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by Andreas Neokleous.
 */

class DBHelper(context: Context, factory: SQLiteDatabase.CursorFactory?):
    SQLiteOpenHelper(context,DATABASE_NAME, factory, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        val CREATE_FAVOURITE_TABLE = ("CREATE TABLE " +
                TABLE_NAME + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY," +
                COLUMN_NAME + " TEXT," +
                COLUMN_POINT + " TEXT" +")")
        db.execSQL(CREATE_FAVOURITE_TABLE)
    }
    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME)
        onCreate(db)
    }

    fun addFavourite(fav: Favourite) {
        val values = ContentValues()
        values.put(COLUMN_NAME, fav.placeName)
        values.put(COLUMN_POINT, fav.point)
        val db = this.writableDatabase
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllFavourite(): Cursor? {
        val db = this.readableDatabase
        return db.rawQuery("SELECT * FROM $TABLE_NAME", null)
    }

    fun deleteFavourite(placeName: String){
        val db = this.writableDatabase
        db.delete(TABLE_NAME, COLUMN_NAME + " = ?", arrayOf(placeName))
        db.close()
    }

    companion object {
        private val DATABASE_VERSION = 1
        private val DATABASE_NAME = "maplessnavdb"
        val TABLE_NAME = "favourite"
        val COLUMN_ID = "_id"
        val COLUMN_NAME = "palceName"
        val COLUMN_POINT = "point"
    }
}