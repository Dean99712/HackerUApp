package com.example.hackeruapp

package com.example.hackeruapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class MyAdapter(private val personList: ArrayList<Person>, var onItemClick : (Person) -> Unit)
    : RecyclerView.Adapter<MyAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textView: TextView = itemView.findViewById(R.id.item_name)
        val imageView : ImageView = itemView.findViewById(R.id.item_image)
        val removeBtn : ImageButton = itemView.findViewById(R.id.remove_button)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.items_layout,parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = personList[position]

        holder.textView.text = item.name
        holder.imageView.setImageResource(item.image)

        holder.itemView.setOnClickListener{
            onItemClick(personList[position])
        }

        holder.removeBtn.setOnClickListener{
            personList.removeAt(holder.layoutPosition)
            notifyItemRemoved(position)
        }
    }

    override fun getItemCount(): Int {
        return personList.size
    }

}
