package com.example.stenograffia.ui.route

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.example.stenograffia.R
import com.example.stenograffia.downloadAndSetImage
import com.smarteist.autoimageslider.SliderViewAdapter

class SliderImageAdapter(private val sliderItems: ArrayList<String?>) :
    SliderViewAdapter<SliderImageAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val inflater: View = LayoutInflater.from(parent.context).inflate(R.layout.element_image_slider, null)
        return ViewHolder(inflater)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.image.downloadAndSetImage(sliderItems[position]!!)
    }

    override fun getCount() = sliderItems.size

    class ViewHolder(itemView: View):SliderViewAdapter.ViewHolder(itemView){
        var image: ImageView = itemView.findViewById(R.id.imageSlider)
    }

}