package com.example.assignment.adapter

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.io.*
import kotlin.collections.ArrayList
import android.graphics.Bitmap

import android.os.Environment
import android.webkit.URLUtil
import android.widget.Toast
import com.downloader.*
import com.example.assignment.R
import com.example.assignment.viewholder.ViewHolder
import com.karumi.dexter.PermissionToken

import com.karumi.dexter.MultiplePermissionsReport

import com.karumi.dexter.listener.multi.MultiplePermissionsListener

import com.karumi.dexter.Dexter
import com.karumi.dexter.listener.PermissionRequest


class Adapter(mContext: Context, mList:ArrayList<String>): RecyclerView.Adapter<ViewHolder>() {
    var mContext = mContext
    var mList = mList
    var file:File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    lateinit var bitmap:Bitmap
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(mContext).inflate(R.layout.list_image,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        PRDownloader.initialize(mContext)
        val image = mList[position]
        Glide.with(mContext).load(image).into(holder.image)
        holder.button.setOnClickListener {
            Dexter.withContext(mContext)
                .withPermissions(
                    Manifest.permission.WRITE_EXTERNAL_STORAGE,
                    Manifest.permission.READ_EXTERNAL_STORAGE
                ).withListener(object : MultiplePermissionsListener {
                    override fun onPermissionsChecked(report: MultiplePermissionsReport) {
                    if(report.areAllPermissionsGranted()){
                        downloadImage(image)
                    }
                    else{
                        Toast.makeText(mContext,"Please Allow Permission",Toast.LENGTH_LONG).show()
                    }
                    }
                    override fun onPermissionRationaleShouldBeShown(
                        permissions: List<PermissionRequest?>?,
                        token: PermissionToken?
                    ) { /* ... */
                    }
                }).check()
        }

    }

    private fun downloadImage(s: String) {
        val p = ProgressDialog(mContext)
        p.setMessage("Downloading...")
        p.setCancelable(false)
        p.show()
        PRDownloader.download(s, file.path, URLUtil.guessFileName(s,null,null))
            .build()
            .setOnStartOrResumeListener {

            }
            .setOnPauseListener {

            }
            .setOnCancelListener {

            }
            .setOnProgressListener {
                val per = it.currentBytes*100/it.totalBytes
                p.setMessage("Downloading : $per%")
            }
            .start(object : OnDownloadListener {
                override fun onDownloadComplete() {
                    p.hide()
                    Toast.makeText(mContext,"Successfully saved in Gallery",Toast.LENGTH_LONG).show()
                }
                override fun onError(error: com.downloader.Error?) {
                }
            })
    }
}