package com.example.stenograffia

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.navigation.findNavController
import com.example.stenograffia.ui.data.firebase.AUTH
import com.example.stenograffia.ui.data.firebase.initFirebase

class SignInActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)
        initFirebase()

        //Find view by id
        val mail = findViewById<EditText>(R.id.login)
        val password = findViewById<EditText>(R.id.password)
        val btnLogIn = findViewById<Button>(R.id.sign_in)
        val registration = findViewById<LinearLayout>(R.id.registration)

        btnLogIn.setOnClickListener {
            if (mail.text.isNotEmpty()
                && password.text.isNotEmpty()) {
                AUTH.signInWithEmailAndPassword(mail.text.toString(), password.text.toString())
                    .addOnCompleteListener(this) { task ->
                        if (task.isSuccessful) {
                            Log.d("UP/IN/OUT", "signInWithEmail:success")
                            val user = AUTH.currentUser
                            val intent = Intent(this, MenuActivity::class.java)
                            startActivity(intent)
                        } else {
                            Log.d("UP/IN/OUT", "signInWithEmail:failure", task.exception)
                            Toast.makeText(
                                baseContext, "Неверный e-mail или пароль",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }
            }else{
                Toast.makeText(baseContext, "Заполните все поля", Toast.LENGTH_SHORT).show()
            }
        }

        registration.setOnClickListener {
            val intent = Intent(this, RegistrationActivity::class.java)
            startActivity(intent)
        }
    }
}