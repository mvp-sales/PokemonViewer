package com.mvpsales.pokemonviewer.api

import com.squareup.moshi.Json

data class PokemonListResponse(
    @field:Json(name = "results")
    val results: List<PokemonListItemResponse>
)
