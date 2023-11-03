package com.example.jaayr.dashboard.adapter

import android.annotation.SuppressLint
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.jaayr.R

class CustomSerialMovieAdapter() :
    RecyclerView.Adapter<CustomSerialMovieAdapter.ViewHolder>() {

    var dataSerial = ArrayList<String>()

    /**
     * Provide a reference to the type of views that you are using
     * (custom ViewHolder)
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView: ImageView
        val layoutTitle: ConstraintLayout
        init {
            // Define click listener for the ViewHolder's View
            textView = view.findViewById(R.id.title)
            imageView = view.findViewById(R.id.imageView2)
            layoutTitle= view.findViewById(androidx.constraintlayout.widget.R.id.layout)
        }
    }

    // Create new views (invoked by the layout manager)
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.serial_movie_listrecylerview, viewGroup, false)

        return ViewHolder(view)
    }

    // Replace the contents of a view (invoked by the layout manager)
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {

        // Get element from your dataset at this position and replace the
        // contents of the view with that element
        viewHolder.textView.text = dataSerial[position]
        if (viewHolder.textView.text == "Vijay TV") {
            viewHolder.imageView.setImageResource(R.drawable.vjay)
        } else if (viewHolder.textView.text == "Sun Tv") {
            viewHolder.imageView.setImageResource(R.drawable.suntv)
        } else if (viewHolder.textView.text == "Zee Tamil") {
            viewHolder.imageView.setImageResource(R.drawable.zeetv)
        }else{
            viewHolder.imageView.setImageResource(R.mipmap.ic_launcher)
        }
        viewHolder.textView.apply {
            if (text == "Vijay TV") {
                viewHolder.layoutTitle.setOnClickListener {
                    callBackInterface?.onClickAppName(text.toString())
                }
            } else if (text == "SunTV") {
                viewHolder.layoutTitle.setOnClickListener {
                    callBackInterface?.onClickAppName(text.toString())
                }
            } else {
                viewHolder.layoutTitle.setOnClickListener {
                    callBackInterface?.onClickAppName(text.toString())
                }
            }
        }

    }

    @SuppressLint("NotifyDataSetChanged")
    fun refreshItems(
        dataSet: ArrayList<String>
    ) {
        dataSerial = dataSet
        notifyDataSetChanged()
    }

    // Return the size of your dataset (invoked by the layout manager)
    override fun getItemCount() = dataSerial.size
    private var callBackInterface: CallBackInterface? = null

    // Initializing CallBack Interface Method
    fun setCallBackInterface(callback: CallBackInterface) {
        callBackInterface = callback
    }

    // CallBackInterface
    interface CallBackInterface {
        fun onClickAppName(name: String)
    }
}
