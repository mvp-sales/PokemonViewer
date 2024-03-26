package com.mvpsales.pokemonviewer.ui.pokelist

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.mvpsales.pokemonviewer.R
import com.mvpsales.pokemonviewer.databinding.ActivityMainBinding
import com.mvpsales.pokemonviewer.ui.pokemondetails.PokemonDetailsActivity
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class PokeListActivity : AppCompatActivity() {

    private val viewModel: PokeListViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val adapter = PokemonListAdapter(this)
        adapter.onClickPokemon = {
            openPokemonDetails(it)
        }

        with(binding) {
            pokemonRv.layoutManager = LinearLayoutManager(this@PokeListActivity)
            pokemonRv.adapter = adapter
            pokemonRv.addOnScrollListener(object : OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                    super.onScrolled(recyclerView, dx, dy)
                    val linearLayoutManager = recyclerView.layoutManager as? LinearLayoutManager
                    if (!viewModel.isLoading) {
                        linearLayoutManager?.let { linearLayoutManager ->
                            if (linearLayoutManager.findLastCompletelyVisibleItemPosition() == viewModel.currentOffset) {
                                lifecycleScope.launch {
                                    viewModel.getPokemonList().collect {
                                        adapter.updatePokemonList(it)
                                    }
                                }
                            }
                        }
                    }
                }
            })
            setSupportActionBar(appbar)
        }

        lifecycleScope.launch {
            viewModel.getPokemonList().collect {
                adapter.setPokemonList(it)
            }
        }

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    private fun openPokemonDetails(name: String) {
        val intent = Intent(this, PokemonDetailsActivity::class.java)
        intent.putExtra(PokemonDetailsActivity.POKEMON_NAME_EXTRA, name)
        startActivity(intent)
    }
}