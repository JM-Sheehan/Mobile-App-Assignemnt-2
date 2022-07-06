package ie.wit.concertapplication.models

interface ConcertStore {

    fun findAll(): List<ConcertModel>
    fun create(concert: ConcertModel)
    fun update(concert: ConcertModel)
}