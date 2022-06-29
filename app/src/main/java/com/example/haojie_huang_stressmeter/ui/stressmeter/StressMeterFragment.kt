package com.example.haojie_huang_stressmeter.ui.stressmeter

import android.content.Context
import android.content.Intent
import android.media.MediaPlayer
import android.os.Bundle
import android.os.Vibrator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.haojie_huang_stressmeter.ImageConfirmationActivity
import com.example.haojie_huang_stressmeter.R
import com.example.haojie_huang_stressmeter.databinding.FragmentStressMeterBinding

class StressMeterFragment : Fragment()
{
    private var _binding: FragmentStressMeterBinding? = null
    private lateinit var grid_view: GridView

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!
    private var isStopThread = false


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        val mediaPlayer = MediaPlayer.create(context, R.raw.sound)
        val vibrator = activity?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator

        object : Thread()
        {
            override fun run()
            {
                super.run()
                sleep(500)
                while (true)
                {
                    if (isStopThread)
                    {
                        mediaPlayer?.stop()
                        break
                    }
                    vibrator.vibrate(500)
                    mediaPlayer?.start()
                    sleep(800)
                }
            }
        }.start()

        _binding = FragmentStressMeterBinding.inflate(inflater, container, false)

        val root: View = binding.root
        grid_view = binding.imageGridView
        val more_image_button: Button = binding.moreImagesButton
        val gridAdapter = ImageAdapter()

        val random_num_page = (0..2).shuffled().first()
        gridAdapter.changePages(random_num_page)

        grid_view.adapter = gridAdapter
        var page_num = random_num_page

        more_image_button.setOnClickListener {
            isStopThread = true
            page_num = (page_num + 1).mod(3)
            gridAdapter.changePages(page_num)
            grid_view.adapter = gridAdapter
        }

        grid_view.setOnItemClickListener { _, _, position, _ ->
            isStopThread = true
            val intent = Intent(context, ImageConfirmationActivity::class.java)
            intent.putExtra("PAGE_NUM", page_num)
            intent.putExtra("INDEX", position)
            startActivity(intent)
        }

        return root
    }

    inner class ImageAdapter: BaseAdapter()
    {
        private val grid1 = arrayOf(
            R.drawable.psm_mountains11,
            R.drawable.psm_wine3,
            R.drawable.psm_barbed_wire2,
            R.drawable.psm_clutter,
            R.drawable.psm_blue_drop,
            R.drawable.psm_to_do_list,
            R.drawable.psm_stressed_person7,
            R.drawable.psm_stressed_person6,
            R.drawable.psm_yoga4,
            R.drawable.psm_bird3,
            R.drawable.psm_stressed_person8,
            R.drawable.psm_exam4,
            R.drawable.psm_kettle,
            R.drawable.psm_lawn_chairs3,
            R.drawable.psm_to_do_list3,
            R.drawable.psm_work4)

        private val grid2 = arrayOf(
            R.drawable.psm_talking_on_phone2,
            R.drawable.psm_stressed_person,
            R.drawable.psm_stressed_person12,
            R.drawable.psm_lonely,
            R.drawable.psm_gambling4,
            R.drawable.psm_clutter3,
            R.drawable.psm_reading_in_bed2,
            R.drawable.psm_stressed_person4,
            R.drawable.psm_lake3,
            R.drawable.psm_cat,
            R.drawable.psm_puppy3,
            R.drawable.psm_neutral_person2,
            R.drawable.psm_beach3,
            R.drawable.psm_peaceful_person,
            R.drawable.psm_alarm_clock2,
            R.drawable.psm_sticky_notes2)

        private val grid3 = arrayOf(
            R.drawable.psm_anxious,
            R.drawable.psm_hiking3,
            R.drawable.psm_stressed_person3,
            R.drawable.psm_lonely2,
            R.drawable.psm_dog_sleeping,
            R.drawable.psm_running4,
            R.drawable.psm_alarm_clock,
            R.drawable.psm_headache,
            R.drawable.psm_baby_sleeping,
            R.drawable.psm_puppy,
            R.drawable.psm_stressed_cat,
            R.drawable.psm_angry_face,
            R.drawable.psm_bar,
            R.drawable.psm_running3,
            R.drawable.psm_neutral_child,
            R.drawable.psm_headache2)

        var images: Array<Int> = grid1

        fun changePages(grid_pages: Int)
        {
            when(grid_pages)
            {
                0 -> images = grid1
                1 -> images = grid2
                2 -> images = grid3
            }
        }

        override fun getCount(): Int
        {
            return images.size
        }

        override fun getItem(position: Int): Any
        {
            return images[position]
        }

        override fun getItemId(position: Int): Long
        {
            return images[position].toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View
        {
            val imageView = ImageView(context)
            imageView.setImageResource(images[position])
            imageView.scaleType = ImageView.ScaleType.CENTER_CROP
            imageView.layoutParams = AbsListView.LayoutParams(grid_view.width/4, grid_view.width/4)
            return imageView
        }
    }

    override fun onDestroyView()
    {
        super.onDestroyView()
        _binding = null
    }

    override fun onPause()
    {
        isStopThread = true
        super.onPause()
    }


//    override fun onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment: Boolean)
//    {
//        super.onPrimaryNavigationFragmentChanged(isPrimaryNavigationFragment)
//        println("debug")
//        if (temp_count != 0)
//        {
//            temp_count--
//        }
//        else
//        {
//            isStopThread = true
//            stressmeterviewmodel.isStopThread.value = true
//        }
//    }
}