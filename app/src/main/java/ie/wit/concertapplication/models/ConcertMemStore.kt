package ie.wit.concertapplication.models

import timber.log.Timber.i

var lastId = 0L

internal fun getId(): Long {
    return lastId++
}

class ConcertMemStore: ConcertStore
{
    val concerts = ArrayList<ConcertModel>()

    override fun findAll(): List<ConcertModel> {
        return concerts
    }

    override fun create(concert: ConcertModel) {
        concert.id = getId()
        concerts.add(concert)
        logAll()
    }

    override fun update(concert: ConcertModel) {
        var foundConcert: ConcertModel? =
            concerts.find{ c -> c.id == concert.id}
        if(foundConcert != null){
            foundConcert.headlineAct = concert.headlineAct
            foundConcert.url = concert.url
            foundConcert.address = concert.address
            foundConcert.day = concert.day
            foundConcert.month = concert.month
            foundConcert.year = concert.year
            foundConcert.image  = concert.image
            logAll()
        }
    }

    fun logAll(){
        concerts.forEach{ i("${it}")}
    }
}