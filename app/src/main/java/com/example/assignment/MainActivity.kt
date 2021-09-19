package com.example.assignment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.adapter.AdapterMain
import com.example.assignment.repository.dogRepository
import com.google.android.material.navigation.NavigationView
import org.json.JSONObject


class MainActivity : AppCompatActivity() {
    lateinit var imageList:ArrayList<String>
    lateinit var adapter: AdapterMain
    lateinit var toggle:ActionBarDrawerToggle
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        title = "Home";
        imageList = ArrayList<String>()
        val drawerr:DrawerLayout = findViewById(R.id.drawer_layout)
        val navView:NavigationView = findViewById(R.id.nav_view)
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        navView.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.random_nav -> {
                    startActivity(Intent(this, RandomActivity::class.java))
                }
                R.id.home_nav ->{
                    startActivity(Intent(this, MainActivity::class.java))
                    finish()
                }
            }
            true
        }
        toggle = ActionBarDrawerToggle(this,drawerr,R.string.open,R.string.close)
        drawerr.addDrawerListener(toggle)
        toggle.syncState()
        val response = dogRepository().dogList()
        response.observe(this, Observer {
            val i =JSONObject(it)
            val list = i.getJSONObject("message").keys()
            Log.d("list",list.toString())
            while (list.hasNext()) {
                val key: String = list.next()
                Log.d("key", key)
                    imageList.add(key)
                Log.d("listTest", imageList.toString())
            }
            adapter = AdapterMain(this,imageList)
            val recyclerView: RecyclerView = findViewById<RecyclerView>(R.id.list_item)
            recyclerView.adapter = adapter
        })
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        val item = menu!!.findItem(R.id.search)
        val searchView:SearchView = item.actionView as SearchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                adapter.filter.filter(newText)
                return false
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(toggle.onOptionsItemSelected(item)){
            return true
        }
        return super.onOptionsItemSelected(item)
    }
}