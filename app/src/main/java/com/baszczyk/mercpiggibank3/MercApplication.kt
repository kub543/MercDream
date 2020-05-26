package com.baszczyk.mercpiggibank3

import android.app.Application
import timber.log.Timber

class MercApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}