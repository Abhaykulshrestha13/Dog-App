package com.example.assignment.viewholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R

class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image  = itemView.findViewById<ImageView>(R.id.image_view)
    val button = itemView.findViewById<ImageView>(R.id.download)
}