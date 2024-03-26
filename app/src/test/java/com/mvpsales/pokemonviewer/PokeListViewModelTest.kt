package com.mvpsales.pokemonviewer

import com.mvpsales.pokemonviewer.model.PokemonListItem
import com.mvpsales.pokemonviewer.ui.pokelist.PokeListViewModel
import com.mvpsales.pokemonviewer.usecase.GetPokemonListUseCase
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PokeListViewModelTest {

    private lateinit var sut: PokeListViewModel
    @Mock
    private lateinit var mockUseCase: GetPokemonListUseCase

    @Before
    fun setup() {
        sut = PokeListViewModel(mockUseCase)
    }

    @Test
    fun `should return flow with pokelist`() = runTest {
        val expectedValue = arrayListOf(
            PokemonListItem(
                "bulbasaur",
                "http://bulbasaur.png"
            ),
            PokemonListItem(
                "squirtle",
                "http://squirtle.png"
            ),
            PokemonListItem(
                "charmander",
                "http://charmander.png"
            ),
        )
        val expected = flow {
            emit(expectedValue)
        }

        Mockito.`when`(mockUseCase.getPokemonList(Mockito.anyInt())).thenReturn(expected)

        val actual = sut.getPokemonList()

        actual.onEach {
            assert(sut.isLoading)
        }.onCompletion {
            assert(!sut.isLoading)
        }.collect {
            assert(it == expectedValue)
            assert(sut.currentOffset == it.size)
        }

        Mockito.verify(mockUseCase).getPokemonList(0)
    }
}