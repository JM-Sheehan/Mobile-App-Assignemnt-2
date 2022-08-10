package ie.wit.concertapplication.main

import android.app.Application
import ie.wit.concertapplication.models.*
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    lateinit var concerts:ConcertStore

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Concert App started")
        concerts = ConcertJSONStore(applicationContext)
    }
}