package ie.wit.concertapplication.main

import android.app.Application
import ie.wit.concertapplication.models.ConcertMemStore
import ie.wit.concertapplication.models.ConcertModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val concerts = ConcertMemStore()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Concert App started")

        concerts.create(
            ConcertModel(
            1, "Band 1", "band1.com", "band 1 address",
            1, 2, 1993)
        )
        concerts.create(ConcertModel(
            2, "Band 2", "band2.com", "band 2 address",
            12, 4, 2000))
        concerts.create(ConcertModel(
            3, "Band 3", "band2.com", "band 3 address",
            20, 11, 2021))
    }
}