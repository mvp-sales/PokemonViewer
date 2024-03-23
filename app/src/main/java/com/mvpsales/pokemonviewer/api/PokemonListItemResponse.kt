package com.mvpsales.pokemonviewer.api

import com.mvpsales.pokemonviewer.model.PokemonListItem
import com.squareup.moshi.Json

data class PokemonListItemResponse(
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "url")
    val url: String
)

fun PokemonListItemResponse.toModel(): PokemonListItem {
    val pokemonId = url.split("/").last { it.isNotBlank() }
    val imageUrl = "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/$pokemonId.png"
    return PokemonListItem(
        name,
        imageUrl
    )
}