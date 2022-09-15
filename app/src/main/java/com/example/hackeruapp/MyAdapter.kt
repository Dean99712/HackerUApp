package com.example.hackeruapp

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(
    private val dataList: ArrayList<Note>,
    val onNoteTitleClick: (Note) -> Unit,
    val onNoteImageClick: (Note) -> Unit
) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView
        val imageView: ImageView

        init {
            textView = view.findViewById(R.id.text_view)
            imageView = view.findViewById(R.id.note_image_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.row_item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = dataList[position]
        holder.textView.text = note.title
        if (note.imageUri != null) {
            holder.imageView.setImageURI(Uri.parse(note.imageUri))
        }
        holder.textView.setOnClickListener {
            onNoteTitleClick(note)
        }

        holder.imageView.setOnClickListener {
            onNoteImageClick(note)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun heyAdapterPleaseUpdateTheView(notesList: List<Note>) {

        dataList.clear()
        dataList.addAll(notesList)
        notifyDataSetChanged()
    }

}