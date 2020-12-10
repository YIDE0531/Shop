package com.nuu.shop

import android.os.Bundle
import android.view.KeyEvent
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity() {
    private var exitTime: Long = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        btn_sign_up.setOnClickListener{
            val email = edt_email.text.toString()
            val password = edt_password.text.toString()
            FirebaseAuth.getInstance()
                    .createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener {
                        if(it.isSuccessful){
                            AlertDialog.Builder(this)
                                    .setTitle("Sign Up")
                                    .setMessage("Account created")
                                    .setPositiveButton("ok"){ _, _ ->
                                        setResult(RESULT_OK)
                                        finish()
                                    }.show()
                        }else{
                            AlertDialog.Builder(this)
                                    .setTitle("Sign Up")
                                    .setMessage(it.exception?.message)
                                    .setPositiveButton("ok", null)
                                    .show()
                        }
                    }
        }
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if((System.currentTimeMillis() - exitTime) > 2000){
                Toast.makeText(applicationContext, "再按一次退出程序", Toast.LENGTH_SHORT).show()
                exitTime = System.currentTimeMillis()
            } else {
                setResult(RESULT_CANCELED)
                finish()
            }
            return true //不執行父類
        }
        return super.onKeyDown(keyCode, event)
    }
}