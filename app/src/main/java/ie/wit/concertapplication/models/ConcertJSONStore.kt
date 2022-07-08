package ie.wit.concertapplication.models

import android.content.Context
import android.net.Uri
import com.google.gson.*
import com.google.gson.reflect.TypeToken
import ie.wit.concertapplication.helpers.*
import timber.log.Timber
import java.lang.reflect.Type
import java.util.*
import kotlin.collections.ArrayList

const val JSON_FILE = "concerts.json"
val gsonBuilder: Gson = GsonBuilder().setPrettyPrinting()
    .registerTypeAdapter(Uri::class.java, UriParser())
    .create()
val listType: Type = object : TypeToken<ArrayList<ConcertModel>>() {}.type

fun generateRandomId(): Long {
    return Random().nextLong()
}

class ConcertJSONStore(private val context: Context) : ConcertStore {

    var concerts = mutableListOf<ConcertModel>()

    init {
        if (exists(context, JSON_FILE)) {
            deserialize()
        }
    }

    override fun findAll(): MutableList<ConcertModel> {
        logAll()
        return concerts
    }

    override fun create(concert: ConcertModel) {
        concert.id = generateRandomId()
        concerts.add(concert)
        serialize()
    }


    override fun update(concert: ConcertModel) {
        val concertList = findAll() as ArrayList<ConcertModel>
        var foundConcert: ConcertModel? = concertList.find { c -> c.id == concert.id }
        if(foundConcert != null){
            foundConcert.headlineAct = concert.headlineAct
            foundConcert.url = concert.url
            foundConcert.address = concert.address
            foundConcert.day = concert.day
            foundConcert.month = concert.month
            foundConcert.year = concert.year
            foundConcert.image  = concert.image
            foundConcert.lat = concert.lat
            foundConcert.lng = concert.lng
            foundConcert.zoom = concert.zoom
        }
        serialize()
    }

    override fun delete(concert: ConcertModel) {
        concerts.remove(concert)
        serialize()
    }

    private fun serialize() {
        val jsonString = gsonBuilder.toJson(concerts, listType)
        write(context, JSON_FILE, jsonString)
    }

    private fun deserialize() {
        val jsonString = read(context, JSON_FILE)
        concerts = gsonBuilder.fromJson(jsonString, listType)
    }

    private fun logAll() {
        concerts.forEach { Timber.i("$it") }
    }
}

class UriParser : JsonDeserializer<Uri>,JsonSerializer<Uri> {
    override fun deserialize(
        json: JsonElement?,
        typeOfT: Type?,
        context: JsonDeserializationContext?
    ): Uri {
        return Uri.parse(json?.asString)
    }

    override fun serialize(
        src: Uri?,
        typeOfSrc: Type?,
        context: JsonSerializationContext?
    ): JsonElement {
        return JsonPrimitive(src.toString())
    }
}