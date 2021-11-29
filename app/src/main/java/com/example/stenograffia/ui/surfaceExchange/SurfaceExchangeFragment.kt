package com.example.stenograffia.ui.surfaceExchange

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
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
        val root = inflater.inflate(R.layout.fragment_surface_exchange, container, false)

        //Find view by id
        val btnWhatBlock = root.findViewById<Button>(R.id.btn_what_block)
        val whatTextBlock = root.findViewById<FrameLayout>(R.id.what_text_block)
        val btnAddPhotosSurface = root.findViewById<ImageButton>(R.id.btn_add_photos_surface)
        val surfaceAddress = root.findViewById<EditText>(R.id.address)
        val surfaceDescription = root.findViewById<EditText>(R.id.description)
        val btnSend = root.findViewById<Button>(R.id.btn_edit)

        surfaceExchangeViewModel =
            ViewModelProvider(this).get(SurfaceExchangeViewModel::class.java)

        surfaceExchangeViewModel.text.observe(viewLifecycleOwner, Observer {
        })

        openAndCloseInfBox(btnWhatBlock, whatTextBlock)

        return root
    }

    private fun openAndCloseInfBox(btnWhatBlock: View, whatTextBlock: View) {
        btnWhatBlock.setOnClickListener {
            if (!btnWhatBlock.isActivated) {
                whatTextBlock.visibility = View.GONE
                btnWhatBlock.isActivated = true
            } else {
                whatTextBlock.visibility = View.VISIBLE
                btnWhatBlock.isActivated = false
            }
        }
    }
}