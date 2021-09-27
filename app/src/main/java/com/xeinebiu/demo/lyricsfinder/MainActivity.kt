package com.xeinebiu.demo.lyricsfinder

import android.os.Bundle
import android.os.StrictMode
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatEditText
import androidx.appcompat.widget.AppCompatTextView
import androidx.lifecycle.lifecycleScope
import com.xeinebiu.lyrics_finder.LyricsFinder
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // do not use this on production {this is used only for demo purpose}
        StrictMode.setThreadPolicy(StrictMode.ThreadPolicy.Builder().permitAll().build())
    }

    fun search(view: View) {
        val queryInputView = findViewById<AppCompatEditText>(R.id.queryInput)
        val query = queryInputView.text.toString()

        lifecycleScope.launch {
            val lyrics = LyricsFinder.find(query)
            findViewById<AppCompatTextView>(R.id.lyricsView).text = lyrics
        }
    }
}