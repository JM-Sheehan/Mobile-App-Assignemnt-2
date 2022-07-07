package ie.wit.concertapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import ie.wit.concertapplication.R
import ie.wit.concertapplication.adapters.ConcertAdapter
import ie.wit.concertapplication.adapters.ConcertListener
import ie.wit.concertapplication.databinding.ActivityConcertListBinding
import ie.wit.concertapplication.main.MainApp
import ie.wit.concertapplication.models.ConcertModel

class ConcertListActivity : AppCompatActivity(), ConcertListener {
    lateinit var app : MainApp
    private lateinit var binding: ActivityConcertListBinding
    private lateinit var refreshIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcertListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = ConcertAdapter(app.concerts.findAll(),this)

        registerRefreshCallback()
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId){
            R.id.item_add -> {
                val launcherIntent = Intent(this, ConcertActivity::class.java)
                refreshIntentLauncher.launch(launcherIntent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onConcertClick(concert: ConcertModel) {
        val launcherIntent = Intent(this, ConcertActivity::class.java)
        launcherIntent.putExtra("concert_edit", concert)
        refreshIntentLauncher.launch(launcherIntent)
    }


    private fun registerRefreshCallback(){
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            {binding.recyclerView.adapter?.notifyDataSetChanged()}
    }
}