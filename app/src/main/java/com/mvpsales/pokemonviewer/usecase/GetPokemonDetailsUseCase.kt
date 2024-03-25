package com.mvpsales.pokemonviewer.usecase

import com.mvpsales.pokemonviewer.api.PokemonService
import com.mvpsales.pokemonviewer.api.toModel
import com.mvpsales.pokemonviewer.model.PokemonDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

interface GetPokemonDetailsUseCase {
    fun getPokemonDetails(name: String): Flow<PokemonDetails>
}

class GetPokemonDetailsUseCaseImpl @Inject constructor(
    private val service: PokemonService
): GetPokemonDetailsUseCase {
    override fun getPokemonDetails(name: String): Flow<PokemonDetails> = flow {
        emit(service.getPokemonDetails(name).toModel())
    }
}