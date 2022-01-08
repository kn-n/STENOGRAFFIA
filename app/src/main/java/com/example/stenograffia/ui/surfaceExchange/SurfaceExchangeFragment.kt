package com.example.stenograffia.ui.surfaceExchange

import android.app.Activity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.stenograffia.R
import com.example.stenograffia.ui.data.Models.Surface
import com.example.stenograffia.ui.data.firebase.*
import com.example.stenograffia.ui.routes.RoutesViewModel
import com.github.dhaval2404.imagepicker.ImagePicker
import de.hdodenhof.circleimageview.CircleImageView
import java.util.*

class SurfaceExchangeFragment : Fragment() {

    private lateinit var surfaceExchangeViewModel: SurfaceExchangeViewModel
    var mExchangeUri: String = ""

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
        val btnSend = root.findViewById<Button>(R.id.btn_send)

        surfaceExchangeViewModel =
            ViewModelProvider(this).get(SurfaceExchangeViewModel::class.java)

        surfaceExchangeViewModel.text.observe(viewLifecycleOwner, Observer {
            btnSend.setOnClickListener {
                if (surfaceAddress.text.isNotEmpty() && surfaceDescription.text.isNotEmpty() && mExchangeUri != "") {
                    initFirebase()
                    val uid = UUID.randomUUID()
                    REF_STORAGE_ROOT.child(FOLDER_SURFACE_EXCHANGE).child(AUTH.currentUser!!.uid)
                        .child(uid.toString()).putFile(mExchangeUri.toUri())
                    REF_STORAGE_ROOT.child(NODE_SURFACE_EXCHANGE).child(AUTH.currentUser!!.uid)
                        .child(uid.toString()).downloadUrl.addOnCompleteListener {
                            if (it.isSuccessful) {
                                val surface = Surface(
                                    AUTH.currentUser!!.uid,
                                    it.result.toString(),
                                    surfaceAddress.text.toString(),
                                    surfaceDescription.text.toString()
                                )
                                REF_DATABASE_ROOT.child(NODE_SURFACE_EXCHANGE).child(uid.toString()).setValue(surface)
                            }
                        }
                } else {
                    Toast.makeText(context, "Заполните все поля!", Toast.LENGTH_LONG).show()
                }
            }
        })

        btnAddPhotosSurface.setOnClickListener {
            ImagePicker
                .with(this)
                .cropSquare()
                .galleryOnly()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

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

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                //Image Uri will not be null for RESULT_OK
                val fileUri = data?.data!!
                val img = requireView().findViewById<ImageButton>(R.id.btn_add_photos_surface)
                mExchangeUri = fileUri.toString()
                img.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(context, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
        }
}