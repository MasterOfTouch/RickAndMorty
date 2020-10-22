package com.example.rickandmorty.data.character

import com.example.rickandmorty.data.character.details.CharacterDetailsDto
import com.example.rickandmorty.data.character.list.CharacterResponseDto
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import kotlinx.coroutines.delay
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit

object CharacterRepo {

  private val contentType = "application/json".toMediaType()
  private val converter = Json {
    ignoreUnknownKeys = true
  }.asConverterFactory(contentType)

  private val api: CharacterApi = Retrofit.Builder()
    .baseUrl(API_URL)
    .addConverterFactory(converter)
    .build()
    .create(CharacterApi::class.java)

  suspend fun getCharactersList(): CharacterResponseDto {
    return api.getAllCharacters()
  }

  suspend fun getCharacterDetails(characterId: Int): CharacterDetailsDto {
    return api.getCharacterDetails(characterId = characterId)
  }
}