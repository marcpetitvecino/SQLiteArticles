package com.example.articlessqlite

import android.content.Context
import android.content.res.Resources
import android.database.Cursor
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CursorAdapter
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat

class ArticleAdapter(val context: Context, cursor:Cursor): CursorAdapter(context, cursor, true) {
    override fun newView(p0: Context?, p1: Cursor?, p2: ViewGroup?): View {

        return LayoutInflater.from(context).inflate(R.layout.article_cell, p2, false)
    }

    override fun bindView(p0: View?, p1: Context?, p2: Cursor?) {

        var codiView = p0?.findViewById<TextView>(R.id.codiArticle)
        var descripcioView = p0?.findViewById<TextView>(R.id.DescripcioArticle)
        var stockView = p0?.findViewById<TextView>(R.id.stockArticle)
        var cellView = p0?.findViewById<LinearLayout>(R.id.cellArticle)

        var codi = cursor!!.getString(cursor.getColumnIndex(COL_CODE))
        var descripcio = cursor!!.getString(cursor.getColumnIndex(COL_DESCRIPCIO))
        var stock = cursor!!.getString(cursor.getColumnIndex(COL_STOCK))

        codiView!!.text = "Codi: $codi"
        descripcioView!!.text = "Descripcio: $descripcio"
        stockView!!.text = "Stock: $stock"

        if (stock.toString().toInt() <= 0) {

            cellView!!.setBackgroundColor(ContextCompat.getColor(context, android.R.color.holo_red_light))

        } else {

            cellView!!.setBackgroundColor(ContextCompat.getColor(context, android.R.color.white))

        }


    }


}