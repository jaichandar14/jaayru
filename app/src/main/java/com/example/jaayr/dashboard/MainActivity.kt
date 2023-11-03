package com.example.jaayr.dashboard

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.example.jaayr.R
import com.example.jaayr.baseactivity.BaseActivity
import com.example.jaayr.dashboard.adapter.CustomSerialMovieAdapter
import com.example.jaayr.dialog.WineDialog
import com.example.jaayr.login.LoginActivity
import com.example.jaayr.registeration.RegActivity
import com.example.jaayr.serialscreen.SerialViewActivity
import com.example.jaayr.splash.SplashActivity
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class MainActivity : BaseActivity<MainViewModel>(), CustomSerialMovieAdapter.CallBackInterface {

    private lateinit var adapterSerial: CustomSerialMovieAdapter

    private lateinit var database: DatabaseReference
    private lateinit var sharedPreferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //actionbar
        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "DashBoard"

        viewModel = ViewModelProvider(this)[MainViewModel::class.java]
        adapterSerial = CustomSerialMovieAdapter()
        adapterSerial.setCallBackInterface(this)
        sharedPreferences = getSharedPreferences("myAppPreferences", MODE_PRIVATE)
        mVijayTvSerialList()
        getNewsFeed()

        validatingSubscription()


        findViewById<BottomNavigationView>(R.id.bottomNavigationView).setOnItemSelectedListener {
            when (it.itemId) {
                R.id.serials -> {
                    mVijayTvSerialList()
                    true
                }

                R.id.logout -> {
                    sharedPreferences.edit().putString("uid", "").apply()
                    FirebaseAuth.getInstance().signOut()
                    startActivity(Intent(this, SplashActivity::class.java))
                    true;
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

    fun validatingSubscription() {
        val sharedPreferences = getSharedPreferences("myAppPreferences", MODE_PRIVATE)
        var auth = Firebase.auth
        auth.uid
        Log.d("TAG", "validatingSubscription:${auth.uid} ")
        sharedPreferences.edit().putString("uid", auth.uid).apply()

        database = FirebaseDatabase.getInstance().getReference("users")
        database.child(auth.uid!!).get().addOnSuccessListener {
            var userDate = it.child("date").value
            var userValue = it.child("value").value
            Log.d("TAG", "onCreate: ${it.child("date").value}")
            Log.d("TAG", "onCreate1: ${it.value}")
            val dateFormat = SimpleDateFormat("dd-MM-yyyy")
            // Parse the input string into a Date object
            val date: Date = dateFormat.parse(formatDate(userDate.toString()))
            var currentDate: Date = dateFormat.parse(now())
            val comparisonResult = date.compareTo(currentDate)
            Log.d("TAG", "validatingSubscription: $date $currentDate")
            if (comparisonResult < 0) {
                WineDialog(this).show(
                    "Subscription Ended",
                    "Your Subscription ended by  contact admin"
                ) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
                }
            } else if (comparisonResult > 0) {
                subValidation(userValue.toString().toInt())
            } else {
                WineDialog(this).show(
                    "Subscription Reminder",
                    "Your Subscription is going to end by today contact admin"
                ) {

                }
            }


        }
    }

    fun subValidation(userValue: Int?) {
        when (userValue) {
            1 -> {
                WineDialog(this).show(
                    "Free trail for 5 days",
                    "Admin details is added in the bellow settings menu"
                ) {
                    Toast.makeText(
                        this,
                        it.toString(),
                        Toast.LENGTH_LONG
                    ).show()
                }
            }

            2 -> {
                WineDialog(this).show(
                    "Your subscription is unavailable contact admin",
                    "Admin details is added in the bellow settings menu"
                ) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                    finish()
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

    private fun mVijayTvSerialList() {
        val dataset = mutableListOf<String>("Vijay TV", "Sun Tv", "Zee Tamil")
        adapterSerial.refreshItems(dataset as ArrayList<String>)
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.adapter = adapterSerial
    }

    private fun getNewsFeed() {

        var news = ""
        database = FirebaseDatabase.getInstance().getReference("newsfeed")

        database.get().addOnSuccessListener {
            Log.d("TAG", "onCreate: ${it.value}")


            GlobalScope.launch(Dispatchers.Main) {}
            findViewById<TextView>(R.id.newsfeed).apply {
                text = it.value.toString()
                isSelected = true;
            }
        }
    }

    fun formatDate(userDate: String): String {
        val dateString = userDate

        // Define the date format for the input string
        val dateFormat = SimpleDateFormat("dd-MM-yyyy")

        try {
            // Parse the input string into a Date object
            val date = dateFormat.parse(dateString)

            // Create a Calendar instance and set it to the parsed date
            val calendar = Calendar.getInstance()
            calendar.time = date

            // Add 30 days to the date
            calendar.add(Calendar.DAY_OF_MONTH, 30)

            // Get the resulting date
            val newDate = calendar.time

            // Format the new date as a string if needed
            val newDateString = dateFormat.format(newDate)

            println("Original Date: $dateString")
            println("New Date: $newDateString")
            return newDateString
        } catch (e: Exception) {
            println("Error parsing date: $e")
        }
        return ""
    }

    fun now(): String {
        return SimpleDateFormat("dd-MM-yyyy", Locale.getDefault()).format(Date())
    }
}

