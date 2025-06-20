package com.example.rickandmortyapp_lab3

import android.os.Bundle
import android.view.View
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: CharacterAdapter
    private lateinit var viewModel: CharacterViewModel
    private lateinit var errorMessageTextView: TextView
    private lateinit var progressBar: ProgressBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        errorMessageTextView = findViewById(R.id.errorMessageTextView)
        progressBar = findViewById(R.id.progressBar)

        viewModel = ViewModelProvider(this)[CharacterViewModel::class.java]

        adapter = CharacterAdapter(emptyList())
        recyclerView.adapter = adapter

        observeViewModel()
    }

    private fun observeViewModel() {
        viewModel.characters.observe(this, Observer { characters ->
            adapter = CharacterAdapter(characters)
            recyclerView.adapter = adapter
        })

        viewModel.errorMessage.observe(this, Observer { errorMessage ->
            errorMessageTextView.text = errorMessage
            errorMessageTextView.visibility =
                if (errorMessage.isNullOrEmpty()) View.GONE else View.VISIBLE
        })

        viewModel.isLoading.observe(this, Observer { isLoading ->
            progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
        })
    }
}