package com.mvpsales.pokemonviewer

import com.mvpsales.pokemonviewer.model.PokemonDetails
import com.mvpsales.pokemonviewer.ui.pokemondetails.PokemonDetailsViewModel
import com.mvpsales.pokemonviewer.usecase.GetPokemonDetailsUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokemonDetailsViewModelTest {

    private lateinit var sut: PokemonDetailsViewModel
    @Mock
    private lateinit var mockUseCase: GetPokemonDetailsUseCase

    @Before
    fun setup() {
        sut = PokemonDetailsViewModel(mockUseCase)
    }

    @Test
    fun `should return flow with pokemon details`() = runTest {
        val expectedValue = PokemonDetails(
            1,
            "bulbasaur",
            2,
            5,
            13,
            "grass, poison",
            "razor leaf, vine whip, solar beam"
        )
        val expected = flow {
            emit(expectedValue)
        }

        Mockito.`when`(mockUseCase.getPokemonDetails(Mockito.anyString())).thenReturn(expected)

        val actual = sut.getPokemonDetails("bulbasaur")

        actual.collect {
            assert(it == expectedValue)
        }

        Mockito.verify(mockUseCase).getPokemonDetails("bulbasaur")
    }
}