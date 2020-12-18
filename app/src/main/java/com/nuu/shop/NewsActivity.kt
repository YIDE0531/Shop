package com.nuu.shop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class NewsActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_news)

        val fragmentTransition = supportFragmentManager.beginTransaction()
        fragmentTransition.add(R.id.container, NewsFragment.instance)
        fragmentTransition.commit()
    }
}