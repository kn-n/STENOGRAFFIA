package com.example.stenograffia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import com.example.stenograffia.R
import de.hdodenhof.circleimageview.CircleImageView

class RegistrationActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        //Find view by id
        val img = findViewById<CircleImageView>(R.id.img)
        val username = findViewById<EditText>(R.id.username)
        val login = findViewById<EditText>(R.id.login)
        val email = findViewById<EditText>(R.id.email)
        val password = findViewById<EditText>(R.id.password)
        val btnRegistration = findViewById<Button>(R.id.registration)
        val signIn = findViewById<LinearLayout>(R.id.sign_in)

        btnRegistration.setOnClickListener {
            val intent = Intent(this, MenuActivity::class.java)
            startActivity(intent)
        }

        signIn.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}