package com.example.hackeruapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView


class MainActivity : AppCompatActivity() {

    var itemList = mutableListOf<Item>()
    lateinit var itemImage: Array<Int>
    var adapter = RecyclerAdapter(itemList)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButton3ClickListener()

        itemImage = arrayOf(
            R.drawable.avatar1_foreground,
            R.drawable.avatar2_foreground,
            R.drawable.avatar3_foreground
        )
    }


    private fun setButton3ClickListener() {
        val button = findViewById<Button>(R.id.add_button)
        val input = findViewById<EditText>(R.id.item_name_input)

        button.setOnClickListener {
            adapter.notifyDataSetChanged()
            if (input.text.isNullOrEmpty())
                Toast.makeText(this, "learn how to write!", Toast.LENGTH_SHORT).show()
            else
                itemList.add(Item(input.text.toString(), itemImage.get(1)))
        }
        createRecyclerView()
    }

    private fun createRecyclerView() {
        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view)
        recyclerView.adapter = adapter
    }

}




