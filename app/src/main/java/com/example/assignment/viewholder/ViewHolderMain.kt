package com.example.assignment.viewholder

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.R

class ViewHolderMain(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val context = itemView.context;
    val text_name = itemView.findViewById<TextView>(R.id.label)
}