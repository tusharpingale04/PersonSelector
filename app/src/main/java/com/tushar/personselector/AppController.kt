package com.tushar.personselector

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Base class which is called when app is launched
 */
@HiltAndroidApp
class AppController : Application(){

    override fun onCreate() {
        super.onCreate()
        mInstance = this
    }

    companion object{

        private lateinit var mInstance: AppController

        @JvmStatic
        @Synchronized
        fun getInstance() : AppController{
            return mInstance
        }
    }
}