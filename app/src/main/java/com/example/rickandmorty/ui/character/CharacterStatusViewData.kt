package com.example.rickandmorty.ui.character

sealed class CharacterStatusViewData(open val order: Int) {

  object Alive : CharacterStatusViewData(0)
  object Dead : CharacterStatusViewData(1)
  object Unknown : CharacterStatusViewData(2)
}