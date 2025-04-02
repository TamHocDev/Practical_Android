package com.example.contactapp.Adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import com.example.contactapp.Model.ContactModel
import com.example.contactapp.R

class ContactAdapter(context: Context, private val contacts: ArrayList<ContactModel>) :
    ArrayAdapter<ContactModel>(context, R.layout.item_contact, contacts) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var itemView = convertView
        if (itemView == null) {
            itemView = LayoutInflater.from(context).inflate(R.layout.item_contact, parent, false)
        }

        val contact = contacts[position]

        val tvId = itemView!!.findViewById<TextView>(R.id.tvId)
        val tvName = itemView.findViewById<TextView>(R.id.tvName)
        val tvPhone = itemView.findViewById<TextView>(R.id.tvPhone)

        tvId.text = contact.id.toString()
        tvName.text = contact.name
        tvPhone.text = contact.phone

        return itemView
    }
}