package com.example.stenograffia

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.*
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.canhub.cropper.CropImageContract
import com.canhub.cropper.CropImageView
import com.canhub.cropper.options
import com.example.stenograffia.ui.data.Models.User
import com.example.stenograffia.ui.data.firebase.*
import com.github.dhaval2404.imagepicker.ImagePicker
import de.hdodenhof.circleimageview.CircleImageView

class RegistrationActivity : AppCompatActivity() {

    var mProfileUri: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        initFirebase()
        //Find view by id
        val img = findViewById<CircleImageView>(R.id.img)
        val username = findViewById<EditText>(R.id.username)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val btnRegistration = findViewById<Button>(R.id.registration)
        val signIn = findViewById<LinearLayout>(R.id.sign_in)
        val loading = findViewById<ImageView>(R.id.loading)

        btnRegistration.isEnabled = true
        btnRegistration.isClickable = true

        img.setOnClickListener {
            ImagePicker
                .with(this)
                .cropSquare()
                .galleryOnly()
                .createIntent { intent ->
                    startForProfileImageResult.launch(intent)
                }
        }

        btnRegistration.setOnClickListener {
            if (username.text.isNotEmpty()
                && email.text.isNotEmpty()
                && password.text.isNotEmpty()
            ) {
                loading(this, loading)
                blockChanges(username, email, password, btnRegistration, false)
                AUTH.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            val user = User(
                                AUTH.currentUser!!.uid,
                                username.text.toString(),
                                "",
                                ArrayList()
                            )
                            addNewUser(user)
                            val path = REF_STORAGE_ROOT.child(FOLDER_PROFILE_IMAGE)
                                .child(AUTH.currentUser!!.uid)
                            path.putFile(Uri.parse(mProfileUri)).addOnCompleteListener {
                                if (it.isSuccessful) {
                                    path.downloadUrl.addOnCompleteListener { uri ->
                                        if (uri.isSuccessful) {
                                            val urlFromStorage = uri.result.toString()
                                            REF_DATABASE_ROOT.child(NODE_USERS)
                                                .child(AUTH.currentUser!!.uid).child("imgUri")
                                                .setValue(urlFromStorage)
                                            val intent = Intent(this, MenuActivity::class.java)
                                            startActivity(intent)
                                        }
                                    }
                                } else {
                                    val intent = Intent(this, MenuActivity::class.java)
                                    startActivity(intent)
                                }
                            }

                        } else {
                            Log.d("UP/IN/OUT", "createUserWithEmail:failure", task.exception)
                            blockChanges(username, email, password, btnRegistration, true)
                            loading.setImageDrawable(null)
                            Toast.makeText(
                                baseContext,
                                "Ой! Что-то пошло не так(",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            } else {
                Toast.makeText(baseContext, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        signIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }

    private val startForProfileImageResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            val resultCode = result.resultCode
            val data = result.data
            if (resultCode == Activity.RESULT_OK) {
                val fileUri = data?.data!!
                val img = findViewById<CircleImageView>(R.id.img)
                mProfileUri = fileUri.toString()
                img.setImageURI(fileUri)
            } else if (resultCode == ImagePicker.RESULT_ERROR) {
                Toast.makeText(this, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            }
        }

    private fun blockChanges(
        name: EditText,
        mail: EditText,
        password: EditText,
        btnRegistration: Button,
        boolean: Boolean
    ) {
        name.isLongClickable = boolean
        name.isCursorVisible = boolean
        mail.isLongClickable = boolean
        mail.isCursorVisible = boolean
        password.isLongClickable = boolean
        password.isCursorVisible = boolean
        btnRegistration.isEnabled = boolean
        btnRegistration.isClickable = boolean
    }
}