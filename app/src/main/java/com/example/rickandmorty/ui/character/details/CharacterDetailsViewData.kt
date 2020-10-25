package com.example.rickandmorty.ui.character.details

import com.example.rickandmorty.data.character.details.CharacterDetailsDto
import com.example.rickandmorty.ui.character.CharacterStatusViewData

data class CharacterDetailsViewData(
  val id: Int,
  val name: String,
  val avatar: String,
  val status: CharacterStatusViewData,
  val species: String,
  val type: String,
  val lastLocation: String,
  val firstSeen: String
)

fun CharacterDetailsDto.toContent() = CharacterDetailsViewData(
  id = id,
  name = name,
  avatar = image,
  status = status.toViewData(),
  species = species,
  type = type,
  lastLocation = location.name,
  firstSeen = origin.name,
)

private fun String.toViewData() = when (this) {
  "Alive" -> CharacterStatusViewData.Alive
  "Dead" -> CharacterStatusViewData.Dead
  "unknown" -> CharacterStatusViewData.Unknown
  else -> throw IllegalArgumentException("unexpected status")
}