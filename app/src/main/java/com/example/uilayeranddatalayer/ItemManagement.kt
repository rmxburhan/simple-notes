package com.example.uilayeranddatalayer

import android.content.Context
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.uilayeranddatalayer.models.Item
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

private  const val KEY = "list-item"
private  const val ITEM_KEY = "items"

class ItemManagement(context : Context) {
    private lateinit var sharedPreferences: SharedPreferences
    init {
        sharedPreferences = context.getSharedPreferences(KEY, MODE_PRIVATE)
    }

    fun getData() : List<Item>{
        val gson = Gson()
        val items = sharedPreferences.getString(ITEM_KEY, "")
        if (items.isNullOrEmpty())
            return listOf()
        val myType = object : TypeToken<List<Item>>() {}.type
        return gson.fromJson<List<Item>>(items.toString(), myType)
    }

    private fun getMutableData() : MutableList<Item> {
        val gson = Gson()
        val items = sharedPreferences.getString(ITEM_KEY, "")
        if (items.isNullOrEmpty())
            return mutableListOf()
        val myType = object : TypeToken<MutableList<Item>>() {}.type
        return gson.fromJson<MutableList<Item>>(items.toString(), myType)
    }

    fun saveData(item : Item) {
        try {
            val gson = Gson()
            var items = getMutableData()
            if (items.size > 0) {
                val sorted = items.sortedByDescending { it.id }.get(0)
                item.id = sorted.id + 1
            } else {
                item.id = 1
            }
            items.add(item)
            sharedPreferences.edit()
                .putString(ITEM_KEY, gson.toJson(items.sortedByDescending { it.id }).toString())
                .commit()

        } catch (ex : Exception) {
            Log.d("excepiton", ex.toString())
        }
    }

    private fun saveAll(items : List<Item>) {
        val gson = Gson()
        sharedPreferences.edit()
            .putString(ITEM_KEY, gson.toJson(items).toString())
            .apply()
    }

    fun deleteData(id : Int,onSuccess : () -> Unit, onError : (ex : Exception) -> Unit) {
        try {
            val items = getMutableData()
            val item = items.find { it.id == id}
            items.remove(item)
            saveAll(items)
            onSuccess()
        } catch (ex : Exception) {
            onError(ex)
        }
    }
}