package com.example.masterand

import android.app.Application
import com.example.masterand.Data.AppContainer
import com.example.masterand.Data.AppDataContainer

class MasterAndApplication : Application() {

    /**
     * AppContainer instance used by the rest of classes to obtain dependencies
     */
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}