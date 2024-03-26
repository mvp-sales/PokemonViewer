package com.mvpsales.pokemonviewer

import com.mvpsales.pokemonviewer.api.PokemonListItemResponse
import com.mvpsales.pokemonviewer.api.PokemonListResponse
import com.mvpsales.pokemonviewer.api.PokemonService
import com.mvpsales.pokemonviewer.api.toModel
import com.mvpsales.pokemonviewer.usecase.GetPokemonListUseCase
import com.mvpsales.pokemonviewer.usecase.GetPokemonListUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPokemonListUseCaseTest {

    private lateinit var useCase: GetPokemonListUseCase
    @Mock
    private lateinit var mockService: PokemonService

    @Before
    fun setup() {
        useCase = GetPokemonListUseCaseImpl(mockService)
    }

    @Test
    fun `should return flow`() = runTest {
        val expected = PokemonListResponse(
            results = arrayListOf(
                PokemonListItemResponse(
                    name = "Bulbasaur",
                    url = "https://pokeapi.co/api/v2/pokemon/1/"
                )
            )
        )

        Mockito.`when`(mockService.getPokemons(Mockito.anyInt())).thenReturn(expected)

        val flow = useCase.getPokemonList(0)

        flow.collect {
            assert(it.size == expected.results.size)
            assert(it[0] == expected.results[0].toModel())
        }

        Mockito.verify(mockService).getPokemons(0)
    }
}