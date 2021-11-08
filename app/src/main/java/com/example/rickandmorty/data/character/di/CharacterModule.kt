package com.example.rickandmorty.data.character.di

import com.example.rickandmorty.data.character.API_URL
import com.example.rickandmorty.data.character.CharacterApi
import com.example.rickandmorty.data.character.CharacterRepo
import com.example.rickandmorty.data.character.DefaultCharacterRepo
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityRetainedComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Converter
import retrofit2.Retrofit
import javax.inject.Singleton


@Module
@InstallIn(ActivityRetainedComponent::class)
object CharacterModule {

  @Provides
  fun provideMediaType(): MediaType = "application/json".toMediaType()

  @Suppress("JSON_FORMAT_REDUNDANT")
  @Provides
  @Singleton
  fun provideConverterFactory(mediaType: MediaType): Converter.Factory = Json {
    ignoreUnknownKeys = true
  }.asConverterFactory(mediaType)

  @Provides
  fun provideCharacterApi(converter: Converter.Factory): CharacterApi = Retrofit.Builder()
    .baseUrl(API_URL)
    .addConverterFactory(converter)
    .build()
    .create(CharacterApi::class.java)

  @Provides
  fun provideCharacterRepo(
    api: CharacterApi
  ): CharacterRepo = DefaultCharacterRepo(api)
}