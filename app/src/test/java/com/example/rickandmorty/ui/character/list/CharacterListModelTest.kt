package com.example.rickandmorty.ui.character.list

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.rickandmorty.data.character.CharacterRepo
import com.example.rickandmorty.data.character.list.CharacterResponseDto
import com.example.rickandmorty.data.character.list.InfoDto
import com.example.rickandmorty.ui.CoroutineTestRule
import com.example.rickandmorty.ui.LifeCycleTestOwner
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.delay
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.withContext
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TestRule

private const val TIME_FOR_LOAD_DATA = 1000L

class CharacterListModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  @get:Rule
  var coroutinesTestRule = CoroutineTestRule()

  private val repo: CharacterRepo = mockk()

  lateinit var viewModel: CharacterListModel
  lateinit var lifeCycleTestOwner: LifeCycleTestOwner

  private val testObserver: Observer<CharacterListState> = mockk {
    every { onChanged(any()) } answers { }
  }

  private val characterResponse = CharacterResponseDto(
    InfoDto(0, 0, "", ""),
    emptyList()
  )

  @Test
  fun loadingStateBeforeDataLoaded() = runBlockingTest {
    coEvery {
      repo.getCharactersList()
    } coAnswers {
      withContext(coroutineContext) {
        delay(TIME_FOR_LOAD_DATA)
        characterResponse
      }
    }
    lifeCycleTestOwner = LifeCycleTestOwner()
    viewModel = CharacterListModel(repo)
    viewModel.state.observe(lifeCycleTestOwner, testObserver)

    lifeCycleTestOwner.onStart()
    coVerify {
      testObserver.onChanged(CharacterListState.Loading)
    }
  }

  @Test
  fun contentStateAfterDataLoaded() = runBlockingTest {
    coEvery {
      repo.getCharactersList()
    } coAnswers {
      withContext(coroutineContext) {
        delay(TIME_FOR_LOAD_DATA)
        characterResponse
      }
    }
    lifeCycleTestOwner = LifeCycleTestOwner()
    viewModel = CharacterListModel(repo)
    viewModel.state.observe(lifeCycleTestOwner, testObserver)

    delay(TIME_FOR_LOAD_DATA)

    lifeCycleTestOwner.onStart()
    coVerify {
      val content = characterResponse.toContent()
      testObserver.onChanged(CharacterListState.Content(content))
    }
    advanceTimeBy(TIME_FOR_LOAD_DATA)
  }

  @Test
  fun errorStateDataLoadFailed() = runBlockingTest {
    val errorMessage = "load data failed"
    coEvery {
      repo.getCharactersList()
    } coAnswers {
      throw Exception(errorMessage)
    }
    lifeCycleTestOwner = LifeCycleTestOwner()
    viewModel = CharacterListModel(repo)
    viewModel.state.observe(lifeCycleTestOwner, testObserver)

    lifeCycleTestOwner.onStart()
    coVerify {
      testObserver.onChanged(CharacterListState.Error(errorMessage))
    }
  }
}