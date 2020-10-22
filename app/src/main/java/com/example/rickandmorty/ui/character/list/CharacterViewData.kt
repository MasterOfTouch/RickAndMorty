package com.example.rickandmorty.ui.character.list

import com.example.rickandmorty.data.character.list.CharacterResponseDto
import com.example.rickandmorty.ui.character.details.toView

fun CharacterResponseDto.toView() = results.map {
  it.toView()
}.sortedWith(
  compareBy(
    { it.status.order },
    { it.name }
  )
)