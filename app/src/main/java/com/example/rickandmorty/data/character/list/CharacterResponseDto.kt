package com.example.rickandmorty.data.character.list

import com.example.rickandmorty.data.character.details.CharacterDetailsDto
import kotlinx.serialization.Serializable

@Serializable
data class CharacterResponseDto(
  val info: InfoDto,
  val results: List<CharacterDetailsDto>
)

@Serializable
data class InfoDto(
  val count: Int,
  val pages: Int,
  val next: String?,
  val prev: String?
)