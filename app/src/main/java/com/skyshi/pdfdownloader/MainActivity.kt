package com.skyshi.pdfdownloader

import android.Manifest
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.skyshi.skypdf.SkyPdfDownloader
import kotlinx.android.synthetic.main.activity_main.*
import pub.devrel.easypermissions.EasyPermissions

class MainActivity : AppCompatActivity() {

    private val fileName: String = "your-file-name.pdf"
    private val stringUrl: String = "https://www.adobe.com/support/products/enterprise/knowledgecenter/media/c4611_sample_explain.pdf"
    private val requestCodeWriteDisk = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button.setOnClickListener({ downloadTask() })
    }

    private fun startDownload() {
        SkyPdfDownloader.of(stringUrl, fileName)
                .withDescription("This is a awesome description")
                .start(this)
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
