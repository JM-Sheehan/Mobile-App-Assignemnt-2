package ie.wit.concertapplication.models

data class ConcertModel(var headlineAct: String = "", 
                        var url: String = "",
                        var address: String = "",
                        var day: Int = 0,
                        var month: Int = 0,
                        var year: Int = 0)
