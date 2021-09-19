package com.example.assignment.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.RecyclerView
import com.example.assignment.ImageActivity
import com.example.assignment.R
import com.example.assignment.viewholder.ViewHolderMain

class AdapterMain(private val mContext: Context,private val mList:ArrayList<String>): RecyclerView.Adapter<ViewHolderMain>(),Filterable {
    var allList:ArrayList<String> = ArrayList(mList)
    private val filter = object : Filter(){
        override fun performFiltering(p0: CharSequence?): FilterResults {
            val filteredList = ArrayList<String>()
            if(p0.toString().isEmpty()){
                filteredList.addAll(allList)
            }
            else{
                for(i in allList){
                    if(i.lowercase().contains(p0.toString().lowercase())){
                        filteredList.add(i)
                    }
                }
            }
            val filterResult = FilterResults()
            filterResult.values = filteredList
            return filterResult
        }

        @SuppressLint("NotifyDataSetChanged")
        override fun publishResults(p0: CharSequence?, p1: FilterResults?) {
            mList.clear()
            mList.addAll(p1!!.values as Collection<String>)
            notifyDataSetChanged()
        }

    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderMain {
        val view = LayoutInflater.from(mContext).inflate(R.layout.list_view,parent,false)
        return ViewHolderMain(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolderMain, position: Int) {
        holder.text_name.text = mList[position].capitalize()
        holder.text_name.setOnClickListener {
            val intent = Intent(mContext, ImageActivity::class.java)
            intent.putExtra("name",mList[position])
            holder.context.startActivity(intent)
        }
    }

    override fun getFilter(): Filter {
       return filter
    }
}