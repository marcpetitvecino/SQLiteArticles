package com.example.articlessqlite

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import androidx.core.content.contentValuesOf


val DATABASE_NAME = "Bdd"
val TABLE_NAME = "ARTICLES"
val COL_ID = "_id"
val COL_CODE = "Codi"
val COL_DESCRIPCIO = "Descripcio"
val COL_PVP = "PVP"
val COL_STOCK = "STOCK"

class ArticlesRepository(var context: Context): SQLiteOpenHelper(context, DATABASE_NAME, null, 1) {

    override fun onCreate(p0: SQLiteDatabase?) {

        val createTable = "CREATE TABLE IF NOT EXISTS " + TABLE_NAME + " ("+
                COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT," +
                COL_CODE + " VARCHAR(80)," +
                COL_DESCRIPCIO + " VARCHAR(80)," +
                COL_STOCK + " INT DEFAULT 0," +
                COL_PVP + " DOUBLE)";

        p0?.execSQL(createTable)

    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    fun insertData(article: Article) {

        var db = this.writableDatabase
        var cv = ContentValues()

        cv.put(COL_ID, article.getId())
        cv.put(COL_CODE, article.getCode())
        cv.put(COL_DESCRIPCIO, article.getDescripcio())
        cv.put(COL_STOCK, article.getStock())
        cv.put(COL_PVP, article.getPVP())

        db.insert(TABLE_NAME, null, cv)

    }

    fun cursorToArticle(cursor: Cursor): Article? {

        val newArticle = Article()

        if (cursor.count > 0) {

            newArticle.setCode(cursor.getString(cursor.getColumnIndex(COL_CODE)))
            newArticle.setDescripcio(cursor.getString(cursor.getColumnIndex(COL_DESCRIPCIO)))
            newArticle.setStock(cursor.getInt(cursor.getColumnIndex(COL_STOCK)))
            newArticle.setPVP(cursor.getDouble(cursor.getColumnIndex(COL_PVP)))

            return newArticle

        }

        return null

    }

    fun getDataCursor(): Cursor {

        var db = this.readableDatabase
        var query = "SELECT $COL_ID, $COL_CODE, $COL_DESCRIPCIO, $COL_PVP, $COL_STOCK FROM $TABLE_NAME"

        var result = db.rawQuery(query, null)

        return result

    }

    fun insertarArticle(codi: String, descripcio: String, pvp: Double, stock: Int) {

        var db = this.writableDatabase

        val values = ContentValues()
        values.put(COL_CODE, codi)
        values.put(COL_DESCRIPCIO, descripcio)
        values.put(COL_STOCK, stock)
        values.put(COL_PVP, pvp)

        db.insert(TABLE_NAME, null, values)
        db.close()

    }

    fun checkIfCodeExists(codi:String): Boolean {

        var db = this.readableDatabase
        var query = "SELECT $COL_CODE FROM $TABLE_NAME WHERE $COL_CODE = '$codi'"

        var cursor = db.rawQuery(query, null)

        return !cursor.moveToFirst()

    }

    fun findArticleById(id: Int): Article {

        var db = this.readableDatabase
        var query = "SELECT * FROM $TABLE_NAME WHERE $COL_ID = '$id'"

        var cursor = db.rawQuery(query, null)

        cursor.moveToFirst()

        val retorn = cursorToArticle(cursor)!!

        cursor.close()

        return retorn

    }

    fun updateArticle(id: Int, descripcio: String, stock: Int, pvp: Double) {

        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COL_DESCRIPCIO, descripcio)
        cv.put(COL_PVP, pvp)
        cv.put(COL_STOCK, stock)

        db.update(TABLE_NAME, cv,"_id=$id", null)
        db.close()

    }

    fun esborrarArticle(id: Int) {

        val db = this.writableDatabase
        val query = "DELETE FROM $TABLE_NAME WHERE _id=$id"

        db.execSQL(query)
        db.close()

    }

    fun searchByDescription(description: String): Cursor {

        val db = this.readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COL_DESCRIPCIO LIKE '%$description%'"

        val result = db.rawQuery(query, null)

        return result

    }

}