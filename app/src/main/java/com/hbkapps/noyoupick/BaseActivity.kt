package com.hbkapps.noyoupick

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity : AppCompatActivity() {

    protected val application: NoYouPickApplication
        get() = getApplication() as NoYouPickApplication

}