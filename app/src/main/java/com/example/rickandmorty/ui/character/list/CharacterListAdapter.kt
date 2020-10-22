package com.example.rickandmorty.ui.character.list

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.ui.bindStatusAndSpecies
import com.example.rickandmorty.ui.character.details.CharacterDetailsViewData

class CharacterListAdapter(
  private val items: List<CharacterDetailsViewData>,
  private val onClick: (details: CharacterDetailsViewData) -> Unit
) : RecyclerView.Adapter<CharacterListViewHolder>() {

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterListViewHolder {
    return CharacterListViewHolder(
      LayoutInflater.from(parent.context).inflate(R.layout.characters_list_item, parent, false)
    ) {
      onClick.invoke(items[it])
    }
  }

  override fun onBindViewHolder(holder: CharacterListViewHolder, position: Int) {
    val data = items[position]
    holder.apply {
      Glide.with(avatar).load(data.avatar).into(avatar)
      characterName.text = data.name
      characterStatusAndSpecies.bindStatusAndSpecies(data.status, data.species)
    }
  }

  override fun getItemCount(): Int = items.count()
}

class CharacterListViewHolder(
  view: View,
  onClick: (pos: Int) -> Unit
) : RecyclerView.ViewHolder(view) {

  val avatar: ImageView = view.findViewById(R.id.avatar)
  val characterName: TextView = view.findViewById(R.id.characterName)
  val characterStatusAndSpecies: TextView = view.findViewById(R.id.characterStatusAndSpecies)

  init {
    itemView.setOnClickListener {
      onClick.invoke(adapterPosition)
    }
  }
}