package com.mvpsales.pokemonviewer.di

import com.mvpsales.pokemonviewer.api.PokemonService
import com.mvpsales.pokemonviewer.usecase.GetPokemonDetailsUseCase
import com.mvpsales.pokemonviewer.usecase.GetPokemonDetailsUseCaseImpl
import com.mvpsales.pokemonviewer.usecase.GetPokemonListUseCase
import com.mvpsales.pokemonviewer.usecase.GetPokemonListUseCaseImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit =
        Retrofit.Builder()
            .baseUrl("https://pokeapi.co")
            .addConverterFactory(MoshiConverterFactory.create())
            .client(OkHttpClient())
            .build()

    @Provides
    @Singleton
    fun providePokemonService(retrofit: Retrofit): PokemonService {
        return retrofit.create(PokemonService::class.java)
    }

    @Provides
    fun provideGetPokemonListUseCase(pokemonService: PokemonService): GetPokemonListUseCase =
        GetPokemonListUseCaseImpl(pokemonService)

    @Provides
    fun provideGetPokemonDetailsUseCase(pokemonService: PokemonService): GetPokemonDetailsUseCase =
        GetPokemonDetailsUseCaseImpl(pokemonService)
}