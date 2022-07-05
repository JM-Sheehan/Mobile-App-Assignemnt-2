package ie.wit.concertapplication.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
import ie.wit.concertapplication.R
import ie.wit.concertapplication.databinding.ActivityConcertBinding
import timber.log.Timber
import timber.log.Timber.i

class ConcertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConcertBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_concert)

        Timber.plant(Timber.DebugTree())

        i("Concert Activity started..")

        binding = ActivityConcertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.btnAdd.setOnClickListener(){
            val concertHeadliner = binding.headlineAct.text.toString()
            val concertUrl = binding.url.text.toString()
            val concertAddress = binding.address.text.toString()
            val day = binding.datePicker.dayOfMonth
            val month = binding.datePicker.month
            val year = binding.datePicker.year

            if(concertHeadliner.isNotEmpty() && concertUrl.isNotEmpty() && concertAddress.isNotEmpty()){
                i("""
                    Concert Added For: $concertHeadliner  
                    Location: $concertAddress
                    Contact At: $concertUrl
                    Date: $day/ $month/ $year
                    """)
            }
            else {
                Snackbar
                    .make(it,"Info Missing, please fix", Snackbar.LENGTH_LONG)
                    .show()
            }
        }
        binding.btnQuit.setOnClickListener(){
            i("Quit Button Pressed")
        }
    }
}