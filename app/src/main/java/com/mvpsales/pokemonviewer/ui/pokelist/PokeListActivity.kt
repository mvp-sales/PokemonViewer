package com.mvpsales.pokemonviewer.ui.pokelist

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.mvpsales.pokemonviewer.R
import com.mvpsales.pokemonviewer.databinding.ActivityMainBinding
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

        with(binding) {
            pokemonRv.layoutManager = LinearLayoutManager(this@PokeListActivity)
            pokemonRv.adapter = adapter
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
}