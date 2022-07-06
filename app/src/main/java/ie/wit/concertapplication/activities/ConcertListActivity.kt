package ie.wit.concertapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ie.wit.concertapplication.R
import ie.wit.concertapplication.databinding.ActivityConcertBinding
import ie.wit.concertapplication.databinding.ActivityConcertListBinding
import ie.wit.concertapplication.databinding.CardConcertBinding
import ie.wit.concertapplication.main.MainApp
import ie.wit.concertapplication.models.ConcertModel

class ConcertListActivity : AppCompatActivity() {
    lateinit var app : MainApp
    private lateinit var binding: ActivityConcertListBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding =ActivityConcertListBinding.inflate(layoutInflater)
        setContentView(R.layout.activity_concert_list)

        app =  application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ConcertAdapter(app.concerts)

        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.item_add -> {
                val launcherIntent = Intent(this, ConcertActivity::class.java)
                startActivityForResult(launcherIntent, 0)
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

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

    class MainHolder(private val binding: CardConcertBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(concert: ConcertModel){
            binding.concertHeadlineAct.text = concert.headlineAct
            binding.concertUrl.text = concert.url
            binding.concertAddress.text = concert.address
            binding.concertDate.text = "${concert.day} / ${concert.month} / ${concert.year}"
        }
    }

}