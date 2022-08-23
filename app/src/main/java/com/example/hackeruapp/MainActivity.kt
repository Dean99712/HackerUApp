package com.example.hackeruapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var button: Button = findViewById(R.id.hello_button)
        var editText: EditText = findViewById(R.id.name)
        var welcome: TextView = findViewById(R.id.hello_text)

        button.setOnClickListener {
            val name : String = editText.text.toString()

            if (!name.matches(".*[a-zA-Z].*".toRegex()) )
                Toast.makeText(this,"Please Enter you name!",Toast.LENGTH_SHORT).show()
            else
                welcome.text = "Hello ${name} ";

        }

    }

}
