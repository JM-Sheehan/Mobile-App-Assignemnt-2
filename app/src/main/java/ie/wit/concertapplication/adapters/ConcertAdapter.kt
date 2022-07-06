package ie.wit.concertapplication.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ie.wit.concertapplication.databinding.CardConcertBinding
import ie.wit.concertapplication.models.ConcertModel

class ConcertAdapter constructor(private var concerts: List<ConcertModel>): RecyclerView.Adapter<ConcertAdapter.MainHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainHolder {
        val binding = CardConcertBinding
            .inflate(LayoutInflater.from(parent.context), parent, false)

        return MainHolder(binding)
    }

    override fun onBindViewHolder(holder: MainHolder, position: Int) {
        val concert = concerts[holder.adapterPosition]
        holder.bind(concert)
    }

    override fun getItemCount(): Int = concerts.size

    class MainHolder(private val binding: CardConcertBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(concert: ConcertModel) {
            binding.concertHeadlineAct.text = concert.headlineAct
            binding.concertUrl.text = concert.url
            binding.concertAddress.text = concert.address
            binding.concertDate.text = "${concert.day} / ${concert.month} / ${concert.year}"
        }
    }
}