package com.example.assignment

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.androidnetworking.AndroidNetworking
import com.androidnetworking.common.Priority
import com.androidnetworking.error.ANError
import com.androidnetworking.interfaces.JSONObjectRequestListener
import com.example.assignment.adapter.Adapter
import com.example.assignment.adapter.AdapterRandom
import org.json.JSONObject

class RandomActivity : AppCompatActivity() {
    private lateinit var name:String
    lateinit var imageList:ArrayList<String>
    lateinit var recyclerView: RecyclerView
    lateinit var progressDialog:ProgressDialog
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_random)
        supportActionBar!!.title = "Random Images"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true);
        imageList = ArrayList()
        recyclerView = findViewById(R.id.recycler_view_random)
        AndroidNetworking.initialize(applicationContext)
            progressDialog = ProgressDialog(this@RandomActivity)
            progressDialog.setMessage("Loading...")
            progressDialog.show()
            AndroidNetworking.get("https://dog.ceo/api/breeds/image/random/10")
                .setTag("test")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsJSONObject(object : JSONObjectRequestListener {
                    override fun onResponse(response: JSONObject) {
                        progressDialog.hide()
                        val list = response.getJSONArray("message")
                        for(i in 0 until list.length()){
                            imageList.add(list[i].toString())
                        }
                        val adapterSuccess =
                            AdapterRandom(this@RandomActivity, imageList)
                        recyclerView.adapter = adapterSuccess
                    }

                    override fun onError(error: ANError) {
                        progressDialog.hide()
                        val toast: Toast = Toast.makeText(
                            applicationContext,
                            error.errorDetail, Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.TOP or Gravity.CENTER_HORIZONTAL, 0, 200)
                        toast.show()
                    }
                })
    }
}