package com.mvpsales.pokemonviewer.ui.pokelist

import androidx.lifecycle.ViewModel
import com.mvpsales.pokemonviewer.usecase.GetPokemonListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import javax.inject.Inject

@HiltViewModel
class PokeListViewModel @Inject constructor(
    private val getPokemonListUseCase: GetPokemonListUseCase
): ViewModel() {

    val isLoading: Boolean
        get() = _isLoading
    private var _isLoading = false
    val currentOffset: Int
        get() = _currentOffset
    private var _currentOffset = 0

    fun getPokemonList() =
        getPokemonListUseCase.getPokemonList(_currentOffset)
            .flowOn(Dispatchers.IO)
            .onStart {
                _isLoading = true
            }
            .onEach {
                _currentOffset += it.size
            }
            .onCompletion {
                _isLoading = false
            }
}