package com.example.assignment.adapter

import android.Manifest
import android.app.ProgressDialog
import android.content.Context
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.webkit.URLUtil
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.downloader.OnDownloadListener
import com.downloader.PRDownloader
import com.example.assignment.R
import com.example.assignment.viewholder.ViewHolderRandom
import com.karumi.dexter.Dexter
import com.karumi.dexter.MultiplePermissionsReport
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.multi.MultiplePermissionsListener
import java.io.File

class AdapterRandom(private val mContext: Context, private val mList:ArrayList<String>): RecyclerView.Adapter<ViewHolderRandom>() {
    var file: File = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM);
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderRandom {
        val view = LayoutInflater.from(mContext).inflate(R.layout.list_random,parent,false)
        return ViewHolderRandom(view)
    }

    override fun onBindViewHolder(holder: ViewHolderRandom, position: Int) {
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
                            Toast.makeText(mContext,"Please Allow Permission", Toast.LENGTH_LONG).show()
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

    override fun getItemCount(): Int {
        return mList.size
    }
}