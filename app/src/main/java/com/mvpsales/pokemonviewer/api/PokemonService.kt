package com.mvpsales.pokemonviewer.api

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface PokemonService {

    @GET("/api/v2/pokemon?limit=50")
    suspend fun getPokemons(@Query("offset") offset: Int): PokemonListResponse

    @GET("/api/v2/pokemon/{name}")
    suspend fun getPokemonDetails(@Path("name") name: String): PokemonDetailsResponse
}