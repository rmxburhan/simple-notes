package com.example.uilayeranddatalayer.adapter

import android.app.Activity
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.uilayeranddatalayer.ItemManagement
import com.example.uilayeranddatalayer.MainActivity
import com.example.uilayeranddatalayer.MainViewModel
import com.example.uilayeranddatalayer.R
import com.example.uilayeranddatalayer.models.Item

class ItemAdapter(var list : List<Item>, val context : Activity, private val onDeleteItem : (message : String) -> Unit, val itemManagement: ItemManagement) :
    RecyclerView.Adapter<ItemAdapter.ItemHolder>() {
    class ItemHolder(itemView : View) : ViewHolder(itemView) {
        var txtContent : TextView = itemView.findViewById(R.id.myContent)
        var btnDelete : TextView = itemView.findViewById(R.id.btnDelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemHolder(view)
    }

    override fun getItemCount(): Int {
        return list.size
    }

override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val data = list.get(position)

        with(holder) {
            txtContent.setText(data.content)
            btnDelete.setOnClickListener {
                    itemManagement.deleteData(data.id, onSuccess = {
                        onDeleteItem("Delete success")
                    }, onError =
                    {
                        with(context) {
                            Log.d("err-deleteItem", it.toString())
                            Toast.makeText(this, "Terjadi kesalahan", Toast.LENGTH_SHORT).show()
                        }
                    })
            }
        }
    }
}