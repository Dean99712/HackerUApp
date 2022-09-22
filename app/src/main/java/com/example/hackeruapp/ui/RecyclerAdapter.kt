package com.example.hackeruapp.ui

import android.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.example.hackeruapp.model.Person
import com.example.hackeruapp.R

class RecyclerAdapter(
    val dataList: ArrayList<Person>,
    private val onPersonTitleClick: (Person) -> Unit,
    private val onRemoveButtonClick: (Person) -> Unit
) : RecyclerView.Adapter<RecyclerAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val personCard: LinearLayout = itemView.findViewById(R.id.person_card_recycler)
        val textView: TextView = itemView.findViewById(R.id.item_name)
        val imageView: ImageView = itemView.findViewById(R.id.item_image)
        val removeBtn: ImageButton = itemView.findViewById(R.id.edit_button)
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

        holder.personCard.setOnClickListener {
            val dialog: AlertDialog.Builder = AlertDialog.Builder(it.rootView.context)
            val dialogView: View =
                LayoutInflater.from(it.rootView.context).inflate(R.layout.person_fragment, null)

            val dialogProfileImage: ImageView = dialogView.findViewById(R.id.fragment_person_image)
            val dialogProfileTitle: TextView = dialogView.findViewById(R.id.fragment_person_details)
            dialogProfileTitle.text = person.name
            dialogProfileImage.setImageResource(person.image)
            dialog.setView(dialogView)
            dialog.setCancelable(true)
            dialog.show()
        }


        holder.removeBtn.setOnClickListener {
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
