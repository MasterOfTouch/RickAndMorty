package com.example.rickandmorty.ui.character.details

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.example.rickandmorty.data.character.CharacterLocationDto
import com.example.rickandmorty.data.character.CharacterRepo
import com.example.rickandmorty.data.character.details.CharacterDetailsDto
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

class CharacterDetailsModelTest {

  @get:Rule
  var rule: TestRule = InstantTaskExecutorRule()

  @get:Rule
  var coroutinesTestRule = CoroutineTestRule()

  private val repo: CharacterRepo = mockk()

  lateinit var viewModel: CharacterDetailsModel
  lateinit var lifeCycleTestOwner: LifeCycleTestOwner

  private val testObserver: Observer<CharacterDetailsState> = mockk {
    every { onChanged(any()) } answers { }
  }

  private val characterId = 0

  private val characterDetails = CharacterDetailsDto(
    characterId,
    "",
    "Alive",
    "",
    "",
    "",
    CharacterLocationDto("", ""),
    CharacterLocationDto("", ""),
    "",
    emptyList(),
    "", ""
  )

  @Test
  fun loadingStateBeforeDataLoaded() = runBlockingTest {
    coEvery {
      repo.getCharacterDetails(characterId)
    } coAnswers {
      withContext(coroutineContext) {
        delay(TIME_FOR_LOAD_DATA)
        characterDetails
      }
    }
    lifeCycleTestOwner = LifeCycleTestOwner()
    viewModel = CharacterDetailsModel(repo, characterId)
    viewModel.state.observe(lifeCycleTestOwner, testObserver)

    lifeCycleTestOwner.onStart()
    coVerify {
      testObserver.onChanged(CharacterDetailsState.Loading)
    }
  }

  @Test
  fun contentStateAfterDataLoaded() = runBlockingTest {
    coEvery {
      repo.getCharacterDetails(characterId)
    } coAnswers {
      withContext(coroutineContext) {
        delay(TIME_FOR_LOAD_DATA)
        characterDetails
      }
    }
    lifeCycleTestOwner = LifeCycleTestOwner()
    viewModel = CharacterDetailsModel(repo, characterId)
    viewModel.state.observe(lifeCycleTestOwner, testObserver)

    delay(TIME_FOR_LOAD_DATA)

    lifeCycleTestOwner.onStart()
    coVerify {
      testObserver.onChanged(CharacterDetailsState.Content(characterDetails.toContent()))
    }
    advanceTimeBy(TIME_FOR_LOAD_DATA)
  }

  @Test
  fun errorStateDataLoadFailed() = runBlockingTest {
    val errorMessage = "load data failed"
    coEvery {
      repo.getCharacterDetails(characterId)
    } coAnswers {
      throw Exception(errorMessage)
    }
    lifeCycleTestOwner = LifeCycleTestOwner()
    viewModel = CharacterDetailsModel(repo, characterId)
    viewModel.state.observe(lifeCycleTestOwner, testObserver)

    lifeCycleTestOwner.onStart()
    coVerify {
      testObserver.onChanged(CharacterDetailsState.Error(errorMessage))
    }
  }
}