package ie.wit.concertapplication.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.firebase.auth.FirebaseAuth
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
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityConcertListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = FirebaseAuth.getInstance().currentUser?.email.toString()
        setSupportActionBar(binding.toolbar)

        app = application as MainApp

        firebaseAuth = FirebaseAuth.getInstance()

        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        loadConcerts()

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
            R.id.sign_out -> {
                firebaseAuth.signOut()
                val intent = Intent(this, SignInActivity::class.java)
                startActivity(intent)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onConcertClick(concert: ConcertModel) {
        val launcherIntent = Intent(this, ConcertActivity::class.java)
        launcherIntent.putExtra("concert_edit", concert)
        refreshIntentLauncher.launch(launcherIntent)
    }


    private fun registerRefreshCallback() {
        refreshIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { loadConcerts() }
    }

    private fun loadConcerts() {
        showConcerts(app.concerts.findAll())
    }

    fun showConcerts(concerts: List<ConcertModel>){
        binding.recyclerView.adapter = ConcertAdapter(concerts, this)
        binding.recyclerView.adapter?.notifyDataSetChanged()
    }
}