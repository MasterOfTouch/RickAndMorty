package com.example.rickandmorty.ui.character.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.character.CharacterRepo
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CharacterDetailsModel @AssistedInject constructor(
  private val repo: CharacterRepo,
  @Assisted private val characterId: Int,
) : ViewModel() {

  private val characterDetailsState: MutableStateFlow<CharacterDetailsState> = MutableStateFlow(
    CharacterDetailsState.Loading
  )

  private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
    characterDetailsState.tryEmit(CharacterDetailsState.Error(exception.message.toString()))
  }

  val state = characterDetailsState.asLiveData()

  init {
    loadCharacterDetails()
  }

  fun onRetry() {
    loadCharacterDetails()
  }

  private fun loadCharacterDetails() {
    viewModelScope.launch(coroutineExceptionHandler) {
      characterDetailsState.tryEmit(CharacterDetailsState.Loading)
      val details = repo.getCharacterDetails(characterId).toContent()
      characterDetailsState.tryEmit(CharacterDetailsState.Content(details))
    }
  }
}

sealed class CharacterDetailsState {
  object Loading : CharacterDetailsState()
  data class Content(val details: CharacterDetailsViewData) : CharacterDetailsState()
  data class Error(val message: String) : CharacterDetailsState()
}