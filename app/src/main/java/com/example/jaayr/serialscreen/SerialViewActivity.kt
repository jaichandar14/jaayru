package com.example.jaayr.serialscreen

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.jaayr.R
import com.example.jaayr.baseactivity.BaseActivity
import com.example.jaayr.serialscreen.adapter.CustomSerialListAdapter
import com.example.jaayr.WebViewSerialActivity
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SerialViewActivity : BaseActivity<SerailViewViewModel>(),
    CustomSerialListAdapter.CallBackInterface {
    private lateinit var database: DatabaseReference
    private val serialMutable = ArrayList<String>()
    var channelName=""
    private lateinit var adapterSerial: CustomSerialListAdapter

    val hashMap = mutableMapOf<String, String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_seriall_view)
        viewModel = ViewModelProvider(this)[SerailViewViewModel::class.java]
        adapterSerial = CustomSerialListAdapter()
        adapterSerial.setCallBackInterface(this)
         channelName = intent.getStringExtra("ChannelName").toString()
        if (channelName == "Vijay TV") {
            dataBaseVijay()
        }else if (channelName == "Sun Tv"){
            dataBaseSunTv()
        }else if (channelName == "Zee Tamil"){
            dataBaseZeeTamil()
        }
    }

    private fun dataBaseVijay() {
        GlobalScope.launch(Dispatchers.Main) {
            viewModel.fetchDataFromDatabase()
        }

        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapterSerial

        database = FirebaseDatabase.getInstance().getReference("serial")

        database.child("list").get().addOnSuccessListener {
            Log.d("TAG", "onCreate: ${it.value}")

            serialMutable.addAll(it.value as Collection<String>)
            Log.d("TAG", "onCreate2: $serialMutable")
            val vijaySerialList = ArrayList<String>()
            serialMutable.forEach { j ->
                if (j != null) {
                    vijaySerialList.add(j)
                }
            }
            adapterSerial.refreshItems(vijaySerialList as ArrayList<String>,channelName)

        }
    }
    private fun dataBaseSunTv() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapterSerial

        database = FirebaseDatabase.getInstance().getReference("suntv")

        database.child("list").get().addOnSuccessListener {
            Log.d("TAG", "onCreate: ${it.value}")

            serialMutable.addAll(it.value as Collection<String>)
            Log.d("TAG", "onCreate2: $serialMutable")
            val vijaySerialList = ArrayList<String>()
            serialMutable.forEach { j ->
                if (j != null) {
                    vijaySerialList.add(j)
                }
            }
            adapterSerial.refreshItems(vijaySerialList as ArrayList<String>,channelName)

        }
    }
    private fun dataBaseZeeTamil() {
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapterSerial

        database = FirebaseDatabase.getInstance().getReference("Zeetamil")

        database.child("list").get().addOnSuccessListener {
            Log.d("TAG", "onCreate: ${it.value}")

            serialMutable.addAll(it.value as Collection<String>)
            Log.d("TAG", "onCreate2: $serialMutable")
            val vijaySerialList = ArrayList<String>()
            serialMutable.forEach { j ->
                if (j != null) {
                    vijaySerialList.add(j)
                }
            }
            adapterSerial.refreshItems(vijaySerialList as ArrayList<String>,channelName)

        }
    }

    override fun onClickSerialName(name: String) {
        Toast.makeText(this, "$name", Toast.LENGTH_SHORT).show()
        database.child("$name").get().addOnSuccessListener {
            Log.d("TAG", "onCreate: ${it.child("link").value}")
            hashMap.put("pandian", it.child("link").value.toString())
            val intent = Intent(this, WebViewSerialActivity::class.java)
            intent.putExtra("link", it.child("link").value.toString())
            startActivity(intent)
        }
    }
}