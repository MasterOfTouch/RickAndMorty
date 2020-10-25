package com.example.rickandmorty.data.character

import com.example.rickandmorty.data.character.list.CharacterResponseDto
import com.example.rickandmorty.data.character.list.InfoDto
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runBlockingTest
import org.junit.Test

import org.junit.Assert.*
import java.lang.Exception

class DefaultCharacterRepoTest {

  private val api: CharacterApi = mockk()

  private val repo = DefaultCharacterRepo(api)

  private val characterResponse = CharacterResponseDto(
    InfoDto(0,0,"",""),
    emptyList()
  )

  @Test
  fun getCharactersList() = runBlockingTest {
    coEvery {
      api.getCharactersList()
    } returns characterResponse

    assertEquals(characterResponse, repo.getCharactersList())
  }

  @Test(expected = Exception::class)
  fun getCharactersListFail() = runBlockingTest {
    coEvery {
      api.getCharactersList()
    } throws Exception()

    repo.getCharactersList()
  }
}