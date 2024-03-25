package com.mvpsales.pokemonviewer.ui.pokemondetails

import android.os.Bundle
import android.os.PersistableBundle
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import coil.transform.CircleCropTransformation
import com.mvpsales.pokemonviewer.databinding.ActivityMainBinding
import com.mvpsales.pokemonviewer.databinding.ActivityPokemonDetailsBinding
import com.mvpsales.pokemonviewer.ui.pokelist.PokemonListAdapter
import com.mvpsales.pokemonviewer.util.capitalized
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokemonDetailsActivity: AppCompatActivity() {

    private val viewModel: PokemonDetailsViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val binding = ActivityPokemonDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            setSupportActionBar(appbar)
        }

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        intent.getStringExtra(POKEMON_NAME_EXTRA)?.let {
            lifecycleScope.launch {
                viewModel.getPokemonDetails(it).collect {
                    binding.apply {
                        pokemonNameTv.text = it.name.capitalized()
                        pokemonIdTv.text = "ID: ${it.id}"
                        pokemonHeightTv.text = "Height: ${it.height}"
                        pokemonWeightTv.text = "Weight: ${it.weight}"
                        pokemonBaseexperienceTv.text = "Base experience: ${it.baseExperience}"
                        pokemonMovesTv.text = "Moves: ${it.moves}"
                        pokemonTypesTv.text = "Types: ${it.types}"
                        pokemonIv.load(it.imageUrl) {
                            crossfade(true)
                            transformations(CircleCropTransformation())
                        }
                    }
                }
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean =
        when(item.itemId) {
            android.R.id.home -> {
                finish()
                true
            }
            else -> false
        }

    companion object {
        const val POKEMON_NAME_EXTRA = "POKEMON_NAME_EXTRA"
    }
}