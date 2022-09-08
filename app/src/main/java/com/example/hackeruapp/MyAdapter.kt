//package com.example.hackeruapp
//
//import android.view.LayoutInflater
//import android.view.View
//import android.view.ViewGroup
//import android.widget.ImageView
//import android.widget.TextView
//import androidx.recyclerview.widget.RecyclerView
//
//class MyAdapter(
//    private val dataList: ArrayList<Note>,
//    val onNoteTitleClick: (Note) -> Unit,

//) : RecyclerView.Adapter<MyAdapter.ViewHolder>() {
//
//    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
//        val textView: TextView
//    }
//
//    init {
//        textView = view.findViewById(R.id.text_view)
//        imageView = view.findViewById)R.id.note_image_view
//    }
//}
//
//override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
//    val view =
//        LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
//    return ViewHolder(view)
//}
//
//override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//    val note = dataList[position]
//    holder.textView.text = dataList[position].title
//    holder.textView.setOnClickListener {
//        onPersonClick(note)
//    }
//
//    holder.imageView.setOnClickListiner {
//
//    }
//}
//
//override fun getItemCount(): Int {
//    return dataList.size
//}
//
//fun updateAdapterView(notesList: List<Note>) {
//    dataList.clear()
//    dataList.addAll(notesList)
//    notifyDataSetChanged()
//}
package com.example.hackeruapp

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
        val textView: TextView = TODO()
        val imageView: ImageView = TODO()

        init {
            textView = view.findViewById(R.id.text_view)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = dataList[position]
        holder.textView.text = dataList[position].title
        holder.textView.setOnClickListener {
            onPersonClick(note)
        }

        holder.imageView. {

        }

        override fun getItemCount(): Int {
            return dataList.size
        }

        fun updateAdapterView(notesList: List<Note>) {
            dataList.clear()
            dataList.addAll(notesList)
            notifyDataSetChanged()

        }
}
