package ie.wit.concertapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.concertapplication.R
import ie.wit.concertapplication.adapters.ConcertAdapter
import ie.wit.concertapplication.databinding.ActivityConcertListBinding
import ie.wit.concertapplication.main.MainApp

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