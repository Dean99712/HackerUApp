package com.example.hackeruapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView
import retrofit2.Retrofit

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun getName(): String {
        var name:String = "Yakov"
        name = " Nevo"
        return name
    }

    fun onButtonClick(view:View) {
        val textView:TextView = findViewById<TextView>(R.id.hello_text)
       textView.text = "Hello ${getName()} How are you"
    }
}
