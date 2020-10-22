package com.example.rickandmorty

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.core.view.doOnLayout
import androidx.core.view.updatePadding
import com.example.rickandmorty.ui.character.list.CharacterListFragment

class ContentActivity : AppCompatActivity() {

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    setContentView(R.layout.content_activity)
    if (savedInstanceState == null) {
      supportFragmentManager.beginTransaction()
        .replace(R.id.container, CharacterListFragment())
        .commitNow()
    }
  }
}