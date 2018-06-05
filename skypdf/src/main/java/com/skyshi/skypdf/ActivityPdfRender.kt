package com.skyshi.skypdf

import android.os.Bundle
import android.support.v7.app.AppCompatActivity

const val FRAGMENT_PDF_RENDERER_BASIC = "pdf_renderer_basic"

class ActivityPdfRender : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_render)

//        if (savedInstanceState == null) {
        val extras = intent.extras
        val exampleString = extras.getString("stringUri")
        supportFragmentManager.beginTransaction()
                .add(R.id.container, PdfRendererBasicFragment.newInstance(exampleString),
                        FRAGMENT_PDF_RENDERER_BASIC)
                .commit()
//        }
    }

//    fun onCreateOptionsMenu(menu: Menu): Boolean {
//        getMenuInflater().inflate(R.menu.main, menu)
//        return true
//    }

//    fun onOptionsItemSelected(item: MenuItem): Boolean {
//        when (item.getItemId()) {
//            R.id.action_info -> {
//                AlertDialog.Builder(this)
//                        .setMessage(R.string.intro_message)
//                        .setPositiveButton(android.R.string.ok, null)
//                        .show()
//                return true
//            }
//        }
//        return super.onOptionsItemSelected(item)
//    }

}