package com.example.uilayeranddatalayer

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.uilayeranddatalayer.adapter.ItemAdapter
import com.example.uilayeranddatalayer.models.Item
import java.time.LocalDate

class MainViewModel(private val cart : ItemManagement) : ViewModel() {
    var inputText : String = ""
    private var items : MutableLiveData<List<Item>> = MutableLiveData()
    lateinit var adapterItem : ItemAdapter

    fun getItems() : LiveData<List<Item>> {
        return items
    }

    fun getData() {
        items.value = cart.getData()
    }

    fun addData(onSucces : () -> Unit, onError : (message : String) -> Unit) {
        cart.saveData(Item(content = inputText, createdAt = LocalDate.now(), updatedAt = null))
        onSucces()
    }
}