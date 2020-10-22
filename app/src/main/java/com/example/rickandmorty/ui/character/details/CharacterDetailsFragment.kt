package com.example.rickandmorty.ui.character.details

import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.bumptech.glide.Glide
import com.example.rickandmorty.R
import com.example.rickandmorty.ui.bindStatusAndSpecies
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

private const val CHARACTER_ID_EXTRA = "character_id_extra"

@AndroidEntryPoint
class CharacterDetailsFragment : Fragment(R.layout.characters_details_fragment) {

  companion object {
    fun newInstance(
      details: CharacterDetailsViewData
    ) = CharacterDetailsFragment().apply {
      arguments = bundleOf(CHARACTER_ID_EXTRA to details.id)
    }
  }

  @Inject
  lateinit var detailViewModelFactory: CharacterDetailsModel.AssistedFactory

  private val characterId: Int by lazy { requireArguments().getInt(CHARACTER_ID_EXTRA) }

  private val viewModel: CharacterDetailsModel by viewModels {
    CharacterDetailsModel.provideFactory(detailViewModelFactory, characterId)
  }

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    viewModel.state.observe(viewLifecycleOwner) { state ->
      when (state) {
        CharacterDetailsState.Loading -> showProgress()
        is CharacterDetailsState.Content -> showContent(state.details)
        is CharacterDetailsState.Error -> showError()
      }
    }
    requireView().findViewById<View>(R.id.backButton).setOnClickListener {
      requireActivity().onBackPressed()
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