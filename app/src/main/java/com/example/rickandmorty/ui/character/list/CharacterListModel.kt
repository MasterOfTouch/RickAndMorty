package com.example.rickandmorty.ui.character.list

import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.rickandmorty.data.character.CharacterRepo
import com.example.rickandmorty.ui.character.details.CharacterDetailsViewData
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class CharacterListModel @ViewModelInject constructor(
  private val repo: CharacterRepo
) : ViewModel() {

  private val characterListState: MutableStateFlow<CharacterListState> = MutableStateFlow(
    CharacterListState.Loading
  )
  private val coroutineExceptionHandler = CoroutineExceptionHandler { _, exception ->
    characterListState.tryEmit(CharacterListState.Error(exception.message.toString()))
  }

  val state = characterListState.asLiveData()

  init {
    loadCharacterList()
  }

  fun onRetry() {
    characterListState.tryEmit(CharacterListState.Loading)
    loadCharacterList()
  }

  private fun loadCharacterList() = viewModelScope.launch(coroutineExceptionHandler) {

    val content = repo.getCharactersList().toContent()
    characterListState.tryEmit(CharacterListState.Content(content))
  }
}

sealed class CharacterListState {
  object Loading : CharacterListState()
  data class Content(val data: List<CharacterDetailsViewData>) : CharacterListState()
  data class Error(val message: String) : CharacterListState()
}