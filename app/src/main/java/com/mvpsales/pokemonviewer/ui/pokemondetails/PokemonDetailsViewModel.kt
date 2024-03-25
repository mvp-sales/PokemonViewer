package com.mvpsales.pokemonviewer.ui.pokemondetails

import androidx.lifecycle.ViewModel
import com.mvpsales.pokemonviewer.usecase.GetPokemonDetailsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class PokemonDetailsViewModel @Inject constructor(
    private val getPokemonDetailsUseCase: GetPokemonDetailsUseCase
): ViewModel() {
    fun getPokemonDetails(name: String) =
        getPokemonDetailsUseCase.getPokemonDetails(name).flowOn(Dispatchers.IO)
}