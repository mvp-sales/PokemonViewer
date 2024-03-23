package com.mvpsales.pokemonviewer.ui.pokelist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.mvpsales.pokemonviewer.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
): ViewModel() {

    fun getPokemonList(offset: Int = 0) =
        getPokemonListUseCase.getPokemonList(offset).flowOn(Dispatchers.IO)
}