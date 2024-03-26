package com.mvpsales.pokemonviewer

import com.mvpsales.pokemonviewer.api.PokemonDetailsResponse
import com.mvpsales.pokemonviewer.api.PokemonMoveResponse
import com.mvpsales.pokemonviewer.api.PokemonMovesResponse
import com.mvpsales.pokemonviewer.api.PokemonService
import com.mvpsales.pokemonviewer.api.PokemonTypeResponse
import com.mvpsales.pokemonviewer.api.PokemonTypesResponse
import com.mvpsales.pokemonviewer.api.toModel
import com.mvpsales.pokemonviewer.usecase.GetPokemonDetailsUseCase
import com.mvpsales.pokemonviewer.usecase.GetPokemonDetailsUseCaseImpl
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetPokemonDetailsUseCaseTest {

    private lateinit var useCase: GetPokemonDetailsUseCase
    @Mock
    private lateinit var mockService: PokemonService

    @Before
    fun setup() {
        useCase = GetPokemonDetailsUseCaseImpl(mockService)
    }

    @Test
    fun `should return pokemon details correctly`() = runTest {
        val expected = PokemonDetailsResponse(
            1,
            "bulbasaur",
            2,
            2,
            12,
            arrayListOf(
                PokemonTypesResponse(
                    PokemonTypeResponse("grass")
                ),
                PokemonTypesResponse(
                    PokemonTypeResponse("poison")
                ),
            ),
            arrayListOf(
                PokemonMovesResponse(
                    PokemonMoveResponse("razor leaf")
                ),
                PokemonMovesResponse(
                    PokemonMoveResponse("vine whip")
                ),
                PokemonMovesResponse(
                    PokemonMoveResponse("solar beam")
                ),
            )
        )

        Mockito.`when`(mockService.getPokemonDetails(Mockito.anyString())).thenReturn(expected)

        val flow = useCase.getPokemonDetails("bulbasaur")

        flow.collect {
            assert(it == expected.toModel())
        }

        Mockito.verify(mockService).getPokemonDetails("bulbasaur")
    }
}