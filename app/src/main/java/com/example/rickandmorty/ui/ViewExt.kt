package com.example.rickandmorty.ui

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.annotation.ColorRes
import androidx.core.content.ContextCompat
import androidx.core.widget.TextViewCompat
import com.example.rickandmorty.R
import com.example.rickandmorty.ui.character.CharacterStatusViewData

@SuppressLint("SetTextI18n")
fun TextView.bindStatusAndSpecies(status: CharacterStatusViewData, species: String) {
  when (status) {
    CharacterStatusViewData.Alive -> {
      text = "${context.getString(R.string.alive)} - $species"
      setDrawableTint(R.color.status_alive)
    }
    CharacterStatusViewData.Dead -> {
      text = "${context.getString(R.string.dead)} - $species"
      setDrawableTint(R.color.status_dead)
    }
    CharacterStatusViewData.Unknown -> {
      text = "${context.getString(R.string.unknown)} - $species"
      setDrawableTint(R.color.status_unknown)
    }
  }
}

private fun TextView.setDrawableTint(@ColorRes color: Int) {
  TextViewCompat.setCompoundDrawableTintList(
    this,
    ContextCompat.getColorStateList(context, color)
  )
}