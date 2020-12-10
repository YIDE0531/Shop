package com.nuu.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_nick_name.*

class NickNameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nick_name)

        btn_finish.setOnClickListener{
            //TODO: nickName
            setNickName(edt_nick_name.text.toString())

            FirebaseDatabase.getInstance()
                .getReference("users")
                .child(FirebaseAuth.getInstance().currentUser!!.uid)
                .child("nickname")
                .setValue(edt_nick_name.text.toString())
            setResult(RESULT_OK)
            finish()
        }
    }
}