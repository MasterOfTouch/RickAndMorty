package com.example.rickandmorty.data.character

import kotlinx.serialization.Serializable

@Serializable
class CharacterLocationDto (
  val name : String,
  val url : String
)