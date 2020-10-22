package com.example.rickandmorty.data.character.details

import com.example.rickandmorty.data.character.CharacterLocationDto
import kotlinx.serialization.Serializable

@Serializable
class CharacterDetailsDto(
  val id : Int,
  val name : String,
  val status : String,
  val species : String,
  val type : String,
  val gender : String,
  val origin : CharacterLocationDto,
  val location : CharacterLocationDto,
  val image : String,
  val episode : List<String>,
  val url : String,
  val created : String
)