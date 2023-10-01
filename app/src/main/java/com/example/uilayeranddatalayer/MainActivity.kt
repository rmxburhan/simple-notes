package com.example.uilayeranddatalayer

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.example.uilayeranddatalayer.adapter.ItemAdapter
import com.example.uilayeranddatalayer.databinding.ActivityMainBinding
import com.example.uilayeranddatalayer.util.showToast
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding
    private lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val factory = MainViewModelFactory(itemManagement = ItemManagement(this))
        viewModel = ViewModelProvider(this, factory).get(MainViewModel::class.java)

        viewModel.adapterItem = ItemAdapter(listOf(), this, onDeleteItem = {
            onItemDeleted(it)}, ItemManagement(this))

        binding.viewModel = viewModel

        viewModel.getItems().observe(this) {
            viewModel.adapterItem.list = it
            viewModel.adapterItem.notifyDataSetChanged()
        }

        binding.btnSave.setOnClickListener {
            viewModel.addData(onSucces = {
                showToast("Data berhasil ditambahkan")
                getData()
            }) {
                showToast(it)
            }
//            startActivity(Intent(this, HomeActivity::class.java).apply {
//                addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK)
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            })
        }
    }

    fun onItemDeleted(message: String) {
        viewModel.getData()
        Toast.makeText(this@MainActivity, "Berhasil di hapus", Toast.LENGTH_SHORT).show()
    }

    fun getData() {
        lifecycleScope.launch {
            viewModel.getData()
        }
    }

    override fun onStart() {
        super.onStart()
        getData()
    }

    override fun onResume() {
        super.onResume()
        getData()
        Log.d("activity-lifecycle", "Resume")
    }

    override fun onPause() {
        super.onPause()
        Log.d("activity-lifecycle", "Pause")

    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d("activity-lifecycle", "Destroy")
    }

}