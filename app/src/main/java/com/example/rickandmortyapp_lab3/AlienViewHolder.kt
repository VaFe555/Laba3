package com.example.rickandmortyapp_lab3

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class AlienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.alienImageView)
    private val nameTextView: TextView = itemView.findViewById(R.id.alienNameTextView)
    private val speciesTextView: TextView = itemView.findViewById(R.id.alienSpeciesTextView)

    fun bind(character: Character) {
        nameTextView.text = character.name
        speciesTextView.text = "Species: ${character.species}"
        Glide.with(itemView.context)
            .load(character.image)
            .into(imageView)
    }
}