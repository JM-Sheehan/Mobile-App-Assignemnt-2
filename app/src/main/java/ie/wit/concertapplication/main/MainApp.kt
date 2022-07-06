package ie.wit.concertapplication.main

import android.app.Application
import ie.wit.concertapplication.models.ConcertModel
import timber.log.Timber
import timber.log.Timber.i

class MainApp : Application() {

    val concerts = ArrayList<ConcertModel>()

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        i("Concert App started")
//        concerts.add(ConcertModel(
//            "Band 1", "band1.com", "band 1 address",
//            1, 2, 1993))
//        concerts.add(ConcertModel(
//            "Band 2", "band2.com", "band 2 address",
//            12, 4, 2000))
//        concerts.add(ConcertModel(
//            "Band 3", "band2.com", "band 3 address",
//            20, 11, 2021))
    }
}