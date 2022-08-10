package ie.wit.concertapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import ie.wit.concertapplication.databinding.CardConcertBinding
import ie.wit.concertapplication.models.ConcertModel

interface ConcertListener {
    fun onConcertClick(concert: ConcertModel)
}

class ConcertAdapter constructor(private var concerts: List<ConcertModel>,
private val listener: ConcertListener):
    RecyclerView.Adapter<ConcertAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardConcertBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding, listener)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val concert = concerts[holder.adapterPosition]
        holder.bind(concert, listener)
    }

    override fun getItemCount(): Int = concerts.size

    class MainHolder(private val binding: CardConcertBinding, listener: ConcertListener) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(concert: ConcertModel, listener: ConcertListener) {
            binding.concertHeadlineAct.text = concert.headlineAct
            binding.concertUrl.text = concert.url
            binding.concertAddress.text = concert.address
            binding.concertDate.text = "${concert.day} / ${concert.month} / ${concert.year}"
            binding.concertCoordinates.text = "lat: ${concert.lat} long:${concert.lng}"
            Picasso.get().load(concert.image).resize(200,200).into(binding.imageIcon)

            binding.root.setOnClickListener {listener.onConcertClick(concert)}
        }
    }
}