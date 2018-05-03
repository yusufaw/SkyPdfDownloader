package com.skyshi.pdfdownloader

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    var fileName: String? = "your-file-name.pdf"
    private var lastDownload = -1L
    lateinit var dm: DownloadManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        button.setOnClickListener({ startDownload("https://www.adobe.com/support/products/enterprise/knowledgecenter/media/c4611_sample_explain.pdf") })
    }

    private fun startDownload(stringUrl: String) {
        val uri = Uri.parse(stringUrl)

        val fi = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName)
        if (fi.exists()) {
//            openPdfReader()
        } else {
            lastDownload = dm.enqueue(DownloadManager.Request(uri)
                    .setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
                    .setAllowedOverRoaming(false)
                    .setTitle(fileName)
                    .setVisibleInDownloadsUi(true)
                    .setNotificationVisibility(View.VISIBLE)
                    .setDescription("This is a description")
                    .setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS,
                            fileName)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE)
                    .setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED))
        }
    }
}
