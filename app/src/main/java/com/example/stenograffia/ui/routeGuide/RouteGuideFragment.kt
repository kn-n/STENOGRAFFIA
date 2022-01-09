package com.example.stenograffia.ui.routeGuide

import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.SeekBar.OnSeekBarChangeListener
import android.widget.TextView
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.stenograffia.R
import com.example.stenograffia.downloadAndSetImage
import java.util.*


class RouteGuideFragment : Fragment() {

    private lateinit var routeGuideViewModel: RouteGuideViewModel
    private lateinit var mediaPlayer: MediaPlayer

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root = inflater.inflate(R.layout.fragment_route_guide, container, false)

        //Find view by id
        val image:ImageView = root.findViewById(R.id.image)
        val name: TextView = root.findViewById(R.id.name)
        val playPause: ImageView = root.findViewById(R.id.play_pause)
        val seekBar: SeekBar = root.findViewById(R.id.seek_bar)
        val description: TextView = root.findViewById(R.id.description)

        val routeId = requireArguments().getString("routeId")
        val placeId = requireArguments().getString("placeId")

        routeGuideViewModel = ViewModelProvider(this, RouteGuideModelFactory(routeId!!, placeId!!)).get(
            RouteGuideViewModel::class.java
        )

        routeGuideViewModel.place.observe(viewLifecycleOwner, Observer {
            image.downloadAndSetImage(it.Img_url)
            name.text = it.Name
            description.text = it.Description
            mediaPlayer = MediaPlayer.create(context, Uri.parse(it.Audio))
            seekBar.max = mediaPlayer.duration
            seekBar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
                override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                    if (fromUser) {
                        mediaPlayer.seekTo(progress)
                    }
                }

                override fun onStartTrackingTouch(seekBar: SeekBar) {}
                override fun onStopTrackingTouch(seekBar: SeekBar) {}
            })

            Timer().scheduleAtFixedRate(object : TimerTask() {
                override fun run() {
                    seekBar.progress = mediaPlayer.currentPosition
                }
            }, 0, 1000)

            playPause.setOnClickListener {
                if (mediaPlayer.isPlaying){
                    mediaPlayer.pause()
                    playPause.setImageResource(R.drawable.ic_play)
                } else {
                    mediaPlayer.start()
                    playPause.setImageResource(R.drawable.ic_pause)
                }
            }
        })


        return root
    }
}