package com.example.jaayr.serialscreen

import android.util.Log
import com.example.jaayr.baseactivity.BaseViewModel
import com.google.firebase.database.DatabaseReference
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class SerailViewViewModel:BaseViewModel() {
    private lateinit var database: DatabaseReference
    val serialMutable = ArrayList<String>()
    suspend fun fetchDataFromDatabase() {
        try {
            val data = withContext(Dispatchers.IO) {
                // Perform the asynchronous database operation here
                val dataSnapshot = database.child("list").get().await()
                dataSnapshot.value
            }

            if (data is List<*>) {
                // Data is a list, add it to the global variable serialMutable
                serialMutable.addAll(data as ArrayList<String>)
            }
        } catch (e: Exception) {
            // Handle any exceptions that may occur during the database operation
            Log.e("TAG", "Error fetching data from the database: ${e.message}")
        }
    }

}