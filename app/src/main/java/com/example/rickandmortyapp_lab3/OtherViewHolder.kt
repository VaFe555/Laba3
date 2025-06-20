package com.example.rickandmortyapp_lab3

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class OtherViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val imageView: ImageView = itemView.findViewById(R.id.otherImageView)
    private val nameTextView: TextView = itemView.findViewById(R.id.otherNameTextView)
    private val typeTextView: TextView = itemView.findViewById(R.id.otherTypeTextView)

    fun bind(character: Character) {
        nameTextView.text = character.name
        typeTextView.text = "Type: ${character.type}"
        Glide.with(itemView.context)
            .load(character.image)
            .into(imageView)
    }
}