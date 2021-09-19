package com.example.assignment.viewholder

import android.view.View
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R

class ViewHolderRandom(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val image  = itemView.findViewById<ImageView>(R.id.image_view_random)
    val button = itemView.findViewById<ImageView>(R.id.download_random)
}