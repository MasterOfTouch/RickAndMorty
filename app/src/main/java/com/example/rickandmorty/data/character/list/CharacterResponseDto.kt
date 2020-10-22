package com.example.rickandmorty.data.character.list

import com.example.rickandmorty.data.character.details.CharacterDetailsDto
import kotlinx.serialization.Serializable

@Serializable
class CharacterResponseDto(
  val info: InfoDto,
  val results: List<CharacterDetailsDto>
)

@Serializable
class InfoDto(
  val count: Int,
  val pages: Int,
  val next: String?,
  val prev: String?
)