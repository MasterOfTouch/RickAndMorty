package com.example.rickandmorty.ui.character.list

import android.graphics.Rect
import android.os.Bundle
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.example.rickandmorty.R
import com.example.rickandmorty.ui.character.details.CharacterDetailsFragment
import com.example.rickandmorty.ui.character.details.CharacterDetailsViewData
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CharacterListFragment : Fragment(R.layout.characters_list_fragment) {

  private val viewModel: CharacterListModel by viewModels()

  override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
    viewModel.state.observe(viewLifecycleOwner) { state ->
      when (state) {
        CharacterListState.Loading -> showProgress()
        is CharacterListState.Content -> showContent(state.data)
        is CharacterListState.Error -> showError()
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
    findViewById<RecyclerView>(R.id.characterRecycler).isVisible = false
  }

  private fun showContent(data: List<CharacterDetailsViewData>) = requireView().apply {
    findViewById<View>(R.id.progressBar).isVisible = false
    findViewById<View>(R.id.errorLayout).isVisible = false

    val recycler = findViewById<RecyclerView>(R.id.characterRecycler)
    recycler.isVisible = true
    recycler.addItemDecoration(
      object : RecyclerView.ItemDecoration() {
        override fun getItemOffsets(
          outRect: Rect,
          view: View,
          parent: RecyclerView,
          state: RecyclerView.State
        ) {
          outRect.bottom = resources.getDimensionPixelSize(R.dimen.padding_small)
        }
      }
    )

    recycler.adapter = CharacterListAdapter(data) { details ->
      requireActivity().supportFragmentManager.beginTransaction()
        .add(R.id.container, CharacterDetailsFragment.newInstance(details))
        .addToBackStack(null)
        .commit()
    }
  }

  private fun showProgress() = requireView().apply {
    findViewById<View>(R.id.errorLayout).isVisible = false
    findViewById<RecyclerView>(R.id.characterRecycler).isVisible = false

    findViewById<View>(R.id.progressBar).isVisible = true
  }
}