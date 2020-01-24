package com.example.articlessqlite

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.CursorAdapter
import android.widget.ListView
import androidx.appcompat.widget.SearchView

class MainActivity : AppCompatActivity() {

    private val context = this
    private var db = ArticlesRepository(context)

    private lateinit var listView: ListView
    private lateinit var adapter: CursorAdapter
    private lateinit var searchView: SearchView

    override fun onStart() {
        super.onStart()
        adapter.notifyDataSetChanged()

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        listView = findViewById(R.id.listViewArticles)

        adapter = ArticleAdapter(context, ArticlesRepository(context).getDataCursor())

        listView.adapter = adapter


    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {

        val inflater = menuInflater
        inflater.inflate(R.menu.menu_main, menu)

        searchView = menu!!.findItem(R.id.searchView).actionView as SearchView

        initListeners()

        return super.onCreateOptionsMenu(menu)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when (item.itemId) {

            R.id.afegirArticleBtn -> {

                val intent = Intent(this, InsertDataAct::class.java).apply {}
                startActivity(intent)

            }

        }

        return super.onOptionsItemSelected(item)
    }

    private fun initListeners() {

        listView.setOnItemClickListener { parent, view, position, id ->

            val intent = Intent(this, DetailAct::class.java)
            val article = adapter.cursor
            article.moveToPosition(position)
            val idArticle = article.getString(article.getColumnIndex("_id"))

            intent.putExtra("id", idArticle.toInt())
            startActivity(intent)
        }

        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if (newText != null) {

                    val queryCursor = ArticlesRepository(context).searchByDescription(newText)
                    listView.adapter = ArticleAdapter(context, queryCursor)
                    adapter.notifyDataSetChanged()

                }

                return true

            }


        })

    }

}
