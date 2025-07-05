package com.zeekstudios.zeeknotes

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ZeekNotesApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}