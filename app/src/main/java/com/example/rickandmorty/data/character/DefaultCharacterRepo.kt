package com.example.rickandmorty.data.character

import com.example.rickandmorty.data.character.details.CharacterDetailsDto
import com.example.rickandmorty.data.character.list.CharacterResponseDto

class DefaultCharacterRepo(
  private val api: CharacterApi
) : CharacterRepo {

  override suspend fun getCharactersList(): CharacterResponseDto {
    return api.getAllCharacters()
  }

  override suspend fun getCharacterDetails(characterId: Int): CharacterDetailsDto {
    return api.getCharacterDetails(characterId = characterId)
  }
}