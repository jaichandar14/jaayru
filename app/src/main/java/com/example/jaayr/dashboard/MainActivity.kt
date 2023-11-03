package com.example.jaayr.dashboard

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.jaayr.R
import com.example.jaayr.baseactivity.BaseActivity
import com.example.jaayr.dashboard.adapter.CustomSerialMovieAdapter
import com.example.jaayr.dashboard.model.SerialData
import com.example.jaayr.dashboard.model.ValuesData
import com.example.jaayr.serialscreen.SerialViewActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationBarMenu
import com.google.firebase.Firebase
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.database
import com.google.firebase.database.values
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainActivity : BaseActivity<MainViewModel>(),CustomSerialMovieAdapter.CallBackInterface {

    private lateinit var adapterSerial: CustomSerialMovieAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapterSerial = CustomSerialMovieAdapter()
        adapterSerial.setCallBackInterface(this)
        mVijayTvSerialList()

        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.serials -> {
                    mVijayTvSerialList()
                    true
                }

                R.id.movie -> {
                    val dataset = mutableListOf<String>("Leo", "jailer", "RDX")
                    adapterSerial.refreshItems(dataset as ArrayList<String>)
                    val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
                    recyclerView.adapter = adapterSerial
                    true
                }

                else -> {
                    true
                }
            }

        }

    }

    override fun onClickAppName(name: String) {
        Toast.makeText(this, "$name", Toast.LENGTH_SHORT).show()
        val intent = Intent(this, SerialViewActivity::class.java)
        intent.putExtra("ChannelName", name)
        startActivity(intent)
    }
    private fun mVijayTvSerialList(){
        val dataset = mutableListOf<String>("Vijay TV","Sun Tv","Zee Tamil")
        adapterSerial.refreshItems(dataset as ArrayList<String>)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapterSerial
    }
}
