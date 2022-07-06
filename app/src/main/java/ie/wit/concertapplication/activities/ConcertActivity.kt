package ie.wit.concertapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import com.google.android.material.snackbar.Snackbar
import ie.wit.concertapplication.R
import ie.wit.concertapplication.databinding.ActivityConcertBinding
import ie.wit.concertapplication.main.MainApp
import ie.wit.concertapplication.models.ConcertModel
import timber.log.Timber
import timber.log.Timber.i

class ConcertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConcertBinding
    lateinit var app: MainApp
    var concert = ConcertModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert)

        i("Concert Activity started..")

        binding = ActivityConcertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.toolbarAdd.title = title
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if(intent.hasExtra("concert_edit")){
            concert = intent.extras?.getParcelable("concert_edit")!!
            binding.headlineAct.setText(concert.headlineAct)
            binding.url.setText(concert.url)
            binding.address.setText(concert.address)
            // Must implement date picking
        }

        binding.btnAdd.setOnClickListener(){
            concert.headlineAct = binding.headlineAct.text.toString()
            concert.url = binding.url.text.toString()
            concert.address = binding.address.text.toString()
            concert.day = binding.datePicker.dayOfMonth
            concert.month = binding.datePicker.month
            concert.year = binding.datePicker.year

            if( concert.headlineAct.isNotEmpty() &&
                concert.url.isNotEmpty() &&
                concert.address.isNotEmpty())
            {
                app.concerts.create(concert.copy())
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,"${binding}", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        binding.btnQuit.setOnClickListener(){
            i("Quit Button Pressed")
        }


    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_concert, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_cancel -> {
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}