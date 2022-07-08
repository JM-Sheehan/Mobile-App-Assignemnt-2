package ie.wit.concertapplication.models

import android.net.Uri
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class ConcertModel(var id: Long = 0,
                        var headlineAct: String = "",
                        var url: String = "",
                        var address: String = "",
                        var day: Int = 0,
                        var month: Int = 0,
                        var year: Int = 0,
                        var image: Uri = Uri.EMPTY,
                        var lat : Double = 0.0,
                        var lng: Double = 0.0,
                        var zoom: Float = 0f) : Parcelable

@Parcelize
data class Location(var lat: Double = 0.0,
                    var lng: Double = 0.0,
                    var zoom: Float = 0f) : Parcelable