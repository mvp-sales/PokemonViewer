package com.mvpsales.pokemonviewer.api

import com.mvpsales.pokemonviewer.model.PokemonDetails
import com.squareup.moshi.Json

data class PokemonDetailsResponse(
    @field:Json(name = "id")
    val id: Int,
    @field:Json(name = "name")
    val name: String,
    @field:Json(name = "height")
    val height: Int,
    @field:Json(name = "weight")
    val weight: Int,
    @field:Json(name = "base_experience")
    val baseExperience: Int,
    @field:Json(name = "types")
    val types: List<PokemonTypesResponse>,
    @field:Json(name = "moves")
    val moves: List<PokemonMovesResponse>,
)

data class PokemonTypesResponse(
    @field:Json(name = "type")
    val type: PokemonTypeResponse
)

data class PokemonTypeResponse(
    @field:Json(name = "name")
    val name: String
)

data class PokemonMovesResponse(
    @field:Json(name = "move")
    val type: PokemonMoveResponse
)

data class PokemonMoveResponse(
    @field:Json(name = "name")
    val name: String
)

fun PokemonDetailsResponse.toModel(): PokemonDetails =
    PokemonDetails(
        id,
        name,
        height,
        weight,
        baseExperience,
        types.map { it.type }.joinToString(", ") { it.name },
        moves.map { it.type }.joinToString(", ") { it.name },
    )


