package com.example.hackeruapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText

class NameActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_name)
    }

    fun onStartClick(view:View) {
        val intent = Intent(this, MainActivity::class.java)
        val name = findViewById<EditText>(R.id.name_et).text.toString()
        intent.putExtra("name", name)
        startActivity(intent)
    }
}