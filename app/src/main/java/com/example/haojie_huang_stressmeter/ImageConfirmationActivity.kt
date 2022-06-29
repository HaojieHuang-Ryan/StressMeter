package com.example.haojie_huang_stressmeter

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.haojie_huang_stressmeter.ui.stressmeter.StressMeterFragment
import java.io.File
import java.sql.Timestamp

class ImageConfirmationActivity: AppCompatActivity()
{
    private lateinit var imageView: ImageView
    private lateinit var imageAdapter: StressMeterFragment.ImageAdapter

    override fun onCreate(savedInstanceState: Bundle?)
    {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_confirmation)

        val page_num = intent.getIntExtra("PAGE_NUM", -1)
        val index = intent.getIntExtra("INDEX", -1)
        val outer_function = StressMeterFragment()

        imageView = findViewById(R.id.image_confirmation)
        imageAdapter = outer_function.ImageAdapter()
        imageAdapter.changePages(page_num)
        imageView.setImageResource(imageAdapter.images[index])

    }

    fun onSubmitClicked(view: View)
    {
        val timestamp = Timestamp(System.currentTimeMillis()).time
        val file = File(getExternalFilesDir(null), "stress_timestamp.csv")
        val index = intent.getIntExtra("INDEX", -1)
        file.createNewFile()
        var stress_value: Int? = null
        when (index)
        {
            0 -> stress_value = 6
            1 -> stress_value = 8
            2 -> stress_value = 14
            3 -> stress_value = 16
            4 -> stress_value = 5
            5 -> stress_value = 7
            6 -> stress_value = 13
            7 -> stress_value = 15
            8 -> stress_value = 2
            9 -> stress_value = 4
            10 -> stress_value = 10
            11 -> stress_value = 12
            12 -> stress_value = 1
            13 -> stress_value = 3
            14 -> stress_value = 9
            15 -> stress_value = 11
        }
        val text = "$timestamp,$stress_value\n"
        file.appendText(text)
        finishAffinity()
    }

    fun onCancelClicked(view : View)
    {
        finish()
    }
}