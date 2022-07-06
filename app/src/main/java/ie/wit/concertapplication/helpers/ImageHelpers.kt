package ie.wit.concertapplication.helpers

import android.content.Intent
import androidx.activity.result.ActivityResultLauncher
import ie.wit.concertapplication.R

fun showImagePicker(intentLauncher: ActivityResultLauncher<Intent>){
    var chooseFile = Intent(Intent.ACTION_OPEN_DOCUMENT)
    chooseFile.type = "image/"
    chooseFile = Intent.createChooser(chooseFile, R.string.select_concert_image.toString())
    intentLauncher.launch(chooseFile)
}