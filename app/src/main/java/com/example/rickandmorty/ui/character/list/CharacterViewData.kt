package com.example.rickandmorty.ui.character.list

import com.example.rickandmorty.data.character.list.CharacterResponseDto
import com.example.rickandmorty.ui.character.details.toContent

fun CharacterResponseDto.toContent() = results.map {
  it.toContent()
}.sortedWith(
  compareBy(
    { it.status.order },
    { it.name }
  )
)