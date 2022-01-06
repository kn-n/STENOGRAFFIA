package com.example.stenograffia

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import com.example.stenograffia.R
import com.example.stenograffia.ui.data.Models.User
import com.example.stenograffia.ui.data.firebase.AUTH
import com.example.stenograffia.ui.data.firebase.addNewUser
import com.example.stenograffia.ui.data.firebase.initFirebase
import com.theartofdev.edmodo.cropper.CropImage
import de.hdodenhof.circleimageview.CircleImageView

class RegistrationActivity : AppCompatActivity() {
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

        img.setOnClickListener {
            CropImage.activity()
                .start(this)
        }

        btnRegistration.setOnClickListener {
            if (username.text.isNotEmpty()
                && email.text.isNotEmpty()
                && password.text.isNotEmpty()){
                AUTH.createUserWithEmailAndPassword(email.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful){
                            Log.d("UP/IN/OUT", "createUserWithEmail:success")
                            val user = User(AUTH.currentUser!!.uid, username.text.toString(), "", "")
                            addNewUser(user)
                            val intent = Intent(this, MenuActivity::class.java)
                            startActivity(intent)
                        } else {
                            Log.d("UP/IN/OUT", "createUserWithEmail:failure", task.exception)
                            Toast.makeText(baseContext, "Ой! Что-то пошло не так(", Toast.LENGTH_SHORT).show()
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

}