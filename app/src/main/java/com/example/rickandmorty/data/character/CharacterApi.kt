package com.example.rickandmorty.data.character

import com.example.rickandmorty.data.character.details.CharacterDetailsDto
import com.example.rickandmorty.data.character.list.CharacterResponseDto
import retrofit2.http.GET
import retrofit2.http.Path

const val API_URL = "https://rickandmortyapi.com/api/"
private const val CHARACTER_CONTROLLER = "character/"
private const val CHARACTER_ID = "characterId"

interface CharacterApi {

  @GET(CHARACTER_CONTROLLER)
  suspend fun getCharactersList(): CharacterResponseDto

  @GET("$CHARACTER_CONTROLLER{$CHARACTER_ID}")
  suspend fun getCharacterDetails(
    @Path(CHARACTER_ID) characterId: Int
  ): CharacterDetailsDto
}