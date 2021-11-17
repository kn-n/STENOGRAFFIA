package com.example.stenograffia.ui.surfaceExchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.stenograffia.R
import com.example.stenograffia.ui.routes.RoutesViewModel

class SurfaceExchangeFragment : Fragment() {

    private lateinit var surfaceExchangeViewModel: SurfaceExchangeViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        surfaceExchangeViewModel =
            ViewModelProvider(this).get(SurfaceExchangeViewModel::class.java)
        val root = inflater.inflate(R.layout.fragment_surface_exchange, container, false)
        val textView: TextView = root.findViewById(R.id.text_surface_exchange)
        surfaceExchangeViewModel.text.observe(viewLifecycleOwner, Observer {
            textView.text = it
        })
        return root
    }
}