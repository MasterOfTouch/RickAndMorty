package com.example.rickandmorty.ui.character.details

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.character.CharacterRepo
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class CharacterDetailsModel(
  private val characterId: Int
) : ViewModel() {

  private val repo = CharacterRepo

  val characterDetailsState: MutableStateFlow<CharacterDetailsState> = MutableStateFlow(
    CharacterDetailsState.Loading
  )
  private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
    characterDetailsState.tryEmit(CharacterDetailsState.Error(exception.message.toString()))
  }

  init {
    loadCharacterDetails()
  }

  fun onRetry() {
    loadCharacterDetails()
  }

  private fun loadCharacterDetails() {
    viewModelScope.launch(coroutineExceptionHandler) {
      characterDetailsState.tryEmit(CharacterDetailsState.Loading)
      val details = repo.getCharacterDetails(characterId).toView()
      characterDetailsState.tryEmit(CharacterDetailsState.Content(details))
    }
  }
}

sealed class CharacterDetailsState {
  object Loading : CharacterDetailsState()
  data class Content(val details: CharacterDetailsViewData) : CharacterDetailsState()
  data class Error(val message: String) : CharacterDetailsState()
}

@Suppress("UNCHECKED_CAST")
class CharacterDetailsModelFactory(private val characterId: Int) :
  ViewModelProvider.Factory {

  override fun <T : ViewModel?> create(modelClass: Class<T>): T {
    return CharacterDetailsModel(characterId) as T
  }
}