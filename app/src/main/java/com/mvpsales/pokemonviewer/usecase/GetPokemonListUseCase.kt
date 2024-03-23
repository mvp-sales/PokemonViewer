package com.mvpsales.pokemonviewer.usecase

import com.mvpsales.pokemonviewer.api.PokemonService
import com.mvpsales.pokemonviewer.api.toModel
import com.mvpsales.pokemonviewer.model.PokemonListItem
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class GetPokemonListUseCase @Inject constructor(
    private val service: PokemonService
) {
    fun getPokemonList(offset: Int): Flow<List<PokemonListItem>> = flow {
        emit(service.getPokemons(offset).results.map { it.toModel() })
    }
}