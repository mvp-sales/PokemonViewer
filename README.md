# Pokémon Viewer App

To develop this app, I decided to use technologies I've worked with before or that I am comfortable with:

- ViewModel from Android Architecture Components
- Kotlin Coroutines and Flow
- Retrofit for network calls, Moshi for handling Json data into Kotlin objects
- Hilt for dependency injection
- Mockito for unit testing
- Coil for image loading

As we're working with a really small scope, I decided to use two Activities instead of a single Activity with two Fragments, but both approaches work for what we are building.

### Pokémon List Screen

- Used a `RecyclerView` to develop the list component.
- Two `ViewHolder` classes, one for the effective list item and the other for the loading component, allowing for infinite scrolling and loading
- The next page is loaded after the user scrolls to the current last Pokémon of the list.
- Logic regarding the current offset of the list to be passed to the `getPokemonList` method from `GetPokemonListUseCase` is handled inside the `PokeListViewModel`.
- Clicking on a list item opens the details screen, passing the Pokémon's name as an extra to the intent.

### Pokémon Details Screen

- Shows six attributes: ID, Weight, Height, Base Experience, Moves and Types.
- Moves and types are shown as strings separated by comma.
- Strings stored in `strings.xml` file.

### Use Cases

- `GetPokemonListUseCase` and `GetPokemonDetailsUseCase` call `PokemonService`'s methods, returning Flows which emit the data returned by the network calls.
- `PokemonListResponse` and `PokemonDetailsResponse` are the objects returned by the Retrofit calls, and they are mapped by the use cases to `PokemonListItem` and `PokemonDetails`, simpler classes for presenting in the UI - types and moves are complex objects as per below:

```kotlin
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
```

In `PokemonDetails` they are just represented as `String`, easily printed to the screen:

```kotlin
data class PokemonDetails(
    ...
    val types: String,
    val moves: String
)
```