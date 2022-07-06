package ie.wit.concertapplication.main

import android.app.Application
import ie.wit.concertapplication.models.ConcertMemStore
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val concerts = ConcertMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Concert App started")
    }
}