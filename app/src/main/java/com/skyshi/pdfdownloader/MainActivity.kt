package com.skyshi.pdfdownloader

import android.Manifest
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions
import java.io.File

class MainActivity : AppCompatActivity() {

    private val fileName: String = "your-file-name.pdf"
    private val stringUrl: String = "https://www.adobe.com/support/products/enterprise/knowledgecenter/media/c4611_sample_explain.pdf"
    private var lastDownload = -1L
    private lateinit var dm: DownloadManager
    private val requestCodeWriteDisk = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager

        button.setOnClickListener({ downloadTask() })
    }

    private fun startDownload() {
        val uri = Uri.parse(stringUrl)

        val fi = File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                fileName)
        if (fi.exists()) {

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


    private fun hasWriteDiskPermission(): Boolean {
        return EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)
    }

    private fun downloadTask() {
        if (hasWriteDiskPermission()) {
            startDownload()
        } else {
            EasyPermissions.requestPermissions(
                    this,
                    getString(R.string.rationale_disk),
                    requestCodeWriteDisk,
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this)
        if (hasWriteDiskPermission()) startDownload()
    }
}
