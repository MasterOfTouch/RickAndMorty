package com.example.rickandmorty.ui.character.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.ui.bindStatusAndSpecies
import kotlinx.coroutines.flow.collect

private const val CHARACTER_ID_EXTRA = "character_id_extra"

class CharacterDetailsFragment : Fragment(R.layout.characters_details_fragment) {

  companion object {
    fun newInstance(
      details: CharacterDetailsViewData
    ) = CharacterDetailsFragment().apply {
      arguments = bundleOf(CHARACTER_ID_EXTRA to details.id)
    }
  }

  private lateinit var viewModel: CharacterDetailsModel

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    viewModel = ViewModelProvider(
      this,
      CharacterDetailsModelFactory(
        requireArguments().getInt(CHARACTER_ID_EXTRA)
      )
    ).get(CharacterDetailsModel::class.java)

    lifecycleScope.launchWhenStarted {

      requireView().findViewById<View>(R.id.backButton).setOnClickListener {
        requireActivity().onBackPressed()
      }

      viewModel.characterDetailsState.collect { state ->
        when (state) {
          CharacterDetailsState.Loading -> showProgress()
          is CharacterDetailsState.Content -> showContent(state.details)
          is CharacterDetailsState.Error -> showError()
        }
      }
    }
  }

  private fun showError() = requireView().apply {
    findViewById<View>(R.id.characterDetailsContent).isVisible = false
    findViewById<View>(R.id.progressBar).isVisible = false

    findViewById<View>(R.id.errorLayout).isVisible = true
    findViewById<View>(R.id.errorButton).apply {
      isVisible = true
      setOnClickListener {
        viewModel.onRetry()
      }
    }
  }

  private fun showContent(details: CharacterDetailsViewData) = requireView().apply {
    findViewById<View>(R.id.progressBar).isVisible = false
    findViewById<View>(R.id.errorLayout).isVisible = false

    findViewById<View>(R.id.characterDetailsContent).isVisible = true
    findViewById<ImageView>(R.id.avatar).apply {
      Glide.with(this).load(details.avatar).into(this)
    }
    findViewById<TextView>(R.id.characterName).text = details.name
    findViewById<TextView>(R.id.characterStatusAndSpecies)
      .bindStatusAndSpecies(details.status, details.species)
    findViewById<TextView>(R.id.characterType).text = details.type.ifEmpty {
      requireContext().getString(R.string.unknown)
    }
    findViewById<TextView>(R.id.characterLastLocation).text = details.lastLocation
    findViewById<TextView>(R.id.characterFirstSeen).text = details.firstSeen
  }

  private fun showProgress() = requireView().apply {
    findViewById<View>(R.id.errorLayout).isVisible = false
    findViewById<View>(R.id.characterDetailsContent).isVisible = false

    findViewById<View>(R.id.progressBar).isVisible = true
  }
}