package com.example.rickandmortyapp_lab3

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class CharacterAdapter(private val characters: List<Character>) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private val VIEW_TYPE_HUMAN = 0
    private val VIEW_TYPE_ALIEN = 1
    private val VIEW_TYPE_OTHER = 2

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TYPE_HUMAN -> {
                val view = inflater.inflate(R.layout.human_item_layout, parent, false)
                HumanViewHolder(view)
            }
            VIEW_TYPE_ALIEN -> {
                val view = inflater.inflate(R.layout.alien_item_layout, parent, false)
                AlienViewHolder(view)
            }
            else -> {
                val view = inflater.inflate(R.layout.other_item_layout, parent, false)
                OtherViewHolder(view)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (characters[position].type) {
            "Human" -> VIEW_TYPE_HUMAN
            "Alien" -> VIEW_TYPE_ALIEN
            else -> VIEW_TYPE_OTHER
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val character = characters[position]
        when (holder) {
            is HumanViewHolder -> holder.bind(character)
            is AlienViewHolder -> holder.bind(character)
            is OtherViewHolder -> holder.bind(character)
        }
    }

    override fun getItemCount(): Int {
        return characters.size
    }
}