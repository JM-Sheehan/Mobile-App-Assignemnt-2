package ie.wit.concertapplication.activities

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.squareup.picasso.Picasso
import ie.wit.concertapplication.R
import ie.wit.concertapplication.databinding.ActivityConcertBinding
import ie.wit.concertapplication.helpers.showImagePicker
import ie.wit.concertapplication.main.MainApp
import ie.wit.concertapplication.models.ConcertModel
import ie.wit.concertapplication.models.Location
import timber.log.Timber.i

class ConcertActivity : AppCompatActivity() {
    private lateinit var binding: ActivityConcertBinding
    lateinit var app: MainApp
    var concert = ConcertModel()
    private lateinit var imageIntentLauncher: ActivityResultLauncher<Intent>
    val IMAGE_REQUEST = 1
    private lateinit var mapIntentLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        var edit = false

        binding = ActivityConcertBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbarAdd.title = FirebaseAuth.getInstance().currentUser?.email.toString()
        setSupportActionBar(binding.toolbarAdd)

        app = application as MainApp

        if(intent.hasExtra("concert_edit")){
            edit = true

            concert = intent.extras?.getParcelable("concert_edit")!!

            binding.headlineAct.setText(concert.headlineAct)
            binding.url.setText(concert.url)
            binding.address.setText(concert.address)
            binding.btnAdd.setText(R.string.save_concert)

            Picasso.get()
                .load(concert.image)
                .into(binding.concertImage)
            if(concert.image!= Uri.EMPTY){
                binding.chooseImage.setText(R.string.change_concert_image)
            }


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
                if(edit){
                    app.concerts.update(concert.copy())
                }
                else{
                    app.concerts.create(concert.copy())
                }
                i("add Button Pressed: $concert")
                setResult(RESULT_OK)
                finish()
            }
            else {
                Snackbar
                    .make(it,R.string.add_prompt, Snackbar.LENGTH_LONG)
                    .show()
            }
        }
//        binding.btnQuit.setOnClickListener(){
//            i("Quit Button Pressed")
//        }

        binding.chooseImage.setOnClickListener(){
            showImagePicker(imageIntentLauncher)
        }

        binding.btnLocation.setOnClickListener{
            val location = Location(52.245696, -7.139102, 15f)
            if (concert.zoom != 0f) {
                location.lat =  concert.lat
                location.lng = concert.lng
                location.zoom = concert.zoom
            }
            val launcherIntent = Intent(this, MapActivity::class.java)
                .putExtra("location", location)
            mapIntentLauncher.launch(launcherIntent)
        }

        binding.btnDelete.setOnClickListener{
            app.concerts.delete(concert.copy())
            finish()
        }

        registerImagePickerCallback()
        registerMapCallback()
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

    private fun registerImagePickerCallback() {
        imageIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when(result.resultCode){
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Result ${result.data!!.data}")
                            concert.image = result.data!!.data!!
                            Picasso.get()
                                .load(concert.image)
                                .into(binding.concertImage)
                            binding.chooseImage.setText(R.string.change_concert_image)
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }

    private fun registerMapCallback() {
        mapIntentLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult())
            { result ->
                when (result.resultCode) {
                    RESULT_OK -> {
                        if (result.data != null) {
                            i("Got Location ${result.data.toString()}")
                            val location = result.data!!.extras?.getParcelable<Location>("location")!!
                            i("Location == $location")
                            concert.lat = location.lat
                            concert.lng = location.lng
                            concert.zoom = location.zoom
                        } // end of if
                    }
                    RESULT_CANCELED -> { } else -> { }
                }
            }
    }
}