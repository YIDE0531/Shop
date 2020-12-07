package com.nuu.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_sign_up.setOnClickListener{
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            FirebaseAuth.getInstance().createUserWithEmailAndPassword(email, password)
        }
    }
}