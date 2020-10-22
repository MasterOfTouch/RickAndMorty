package com.example.rickandmorty.data.character

import com.example.rickandmorty.data.character.details.CharacterDetailsDto
import com.example.rickandmorty.data.character.list.CharacterResponseDto

interface CharacterRepo {

  suspend fun getCharactersList(): CharacterResponseDto

  suspend fun getCharacterDetails(characterId: Int): CharacterDetailsDto
}