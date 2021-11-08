package com.example.rickandmorty.ui.character.details.di

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.rickandmorty.ui.character.details.CharacterDetailsModel

class CharacterDetailsHiltModule {

  @dagger.assisted.AssistedFactory
  interface AssistedFactory {
    fun create(characterId: Int): CharacterDetailsModel
  }

  companion object {
    fun provideFactory(
      assistedFactory: AssistedFactory,
      characterId: Int
    ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
      @Suppress("UNCHECKED_CAST")
      override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return assistedFactory.create(characterId) as T
      }
    }
  }
}
