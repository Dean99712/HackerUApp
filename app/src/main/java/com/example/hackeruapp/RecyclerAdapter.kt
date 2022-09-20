package com.example.hackeruapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView

class RecyclerAdapter(
    private val dataList: ArrayList<Person>,
    private val onPersonTitleClick: (Person) -> Unit,
    private val onRemoveButtonClick: (Person) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val personCard: LinearLayout = itemView.findViewById(R.id.person_card_recycler)
        val textView: TextView = itemView.findViewById(R.id.item_name)
        val imageView: ImageView = itemView.findViewById(R.id.item_image)
        val removeBtn: ImageButton = itemView.findViewById(R.id.remove_button)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_layout, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = dataList[position]

        holder.textView.text = person.name
        holder.imageView.setImageResource(person.image)

        holder.imageView.setOnClickListener {
        }

        holder.textView.setOnClickListener {
            onPersonTitleClick(person)
        }

        holder.personCard.setOnClickListener {
            onPersonTitleClick(person)
        }


        holder.removeBtn.setOnClickListener {
            onRemoveButtonClick(person)
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
