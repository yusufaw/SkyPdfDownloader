package com.skyshi.skypdf

import android.app.DownloadManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Toast
import java.io.File

const val FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic"

class SkyPdfDownloader : AppCompatActivity() {

    private lateinit var dm: DownloadManager
    private lateinit var fileName: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_render)

        dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
        registerReceiver(onComplete,
                IntentFilter(DownloadManager.ACTION_DOWNLOAD_COMPLETE))
        val extras = intent.extras


        val stringUrl = extras.getString("stringUrl")
        fileName = extras.getString("fileName")
        startDownload(stringUrl)
    }

    companion object {
        fun download(context: Context, stringUrl: String, fileName: String) {
            val intent = Intent(context, SkyPdfDownloader::class.java)
            intent.putExtra("stringUrl", stringUrl)
            intent.putExtra("fileName", fileName)
            context.startActivity(intent)
        }
    }


    private var onComplete: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(ctxt: Context, intent: Intent) {
            Toast.makeText(ctxt, "Download Complete", Toast.LENGTH_LONG).show()
            openPdfReader()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        unregisterReceiver(onComplete)
    }


    private fun openPdfReader() {
        if (fileName.isNotEmpty()) {
            val transaction = supportFragmentManager.beginTransaction()
            transaction.addToBackStack(null)
            transaction.replace(R.id.container, PdfRendererBasicFragment.newInstance(fileName),
                    FRAGMENT_PDF_RENDERER_BASIC)
            transaction.commitAllowingStateLoss()
        }
    }

    private fun startDownload(stringUrl: String) {
        val uri = Uri.parse(stringUrl)

        val fi = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName)
        if (fi.exists()) {
            openPdfReader()
        } else {
            dm.enqueue(DownloadManager.Request(uri)
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

    override fun onBackPressed() {
        finish()
    }
}