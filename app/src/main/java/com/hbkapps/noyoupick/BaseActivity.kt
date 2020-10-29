package com.hbkapps.noyoupick

import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected val application: NoYouPickApplication
        get() = getApplication() as NoYouPickApplication

    private lateinit var progressDialog : Dialog

    fun showProgressBar() {
        progressDialog = Dialog(this)
        progressDialog.setContentView(R.layout.progress_bar_custom_dialog)
        progressDialog.setCancelable(false)
        progressDialog.show()
    }

    fun hideProgressBar() {
        progressDialog.hide()
    }
}
