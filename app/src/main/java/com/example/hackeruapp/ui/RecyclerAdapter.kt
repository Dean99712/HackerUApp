package com.example.hackeruapp.ui

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.hackeruapp.model.Person
import com.example.hackeruapp.R
import com.example.hackeruapp.model.IMAGE_TYPE


class RecyclerAdapter(
    val dataList: ArrayList<Person>,
    private val onPersonTitleClick: (Person) -> Unit,
    private val onPersonImageClick: (Person) -> Unit,
    private val onPersonCardClick: (Person) -> Unit,
    val context: Context
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val personCard: LinearLayout = itemView.findViewById(R.id.person_card_recycler)
        val textView: TextView = itemView.findViewById(R.id.item_name)
        val imageView: ImageView = itemView.findViewById(R.id.item_image)
        val editButton: ImageButton = itemView.findViewById(R.id.edit_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false)
        return ViewHolder(view)
    }

    @SuppressLint("NotifyDataSetChanged")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = dataList[position]
        holder.textView.text = person.name

        if (person.imagePath != null) {
            if (person.imageType == IMAGE_TYPE.URI) holder.imageView.setImageURI(Uri.parse(person.imagePath))
            else Glide.with(context).load(person.imagePath).into(holder.imageView)
        }


        holder.imageView.setOnClickListener {
            onPersonImageClick(person)
        }

        holder.personCard.setOnClickListener {
            onPersonCardClick(person)
        }

        holder.editButton.setOnClickListener {
            onPersonTitleClick(person)
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    fun viewUpdater(personList: List<Person>) {
        dataList.clear()
        dataList.addAll(personList)
        notifyDataSetChanged()
    }

}
