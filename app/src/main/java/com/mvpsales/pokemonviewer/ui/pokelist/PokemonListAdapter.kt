package com.mvpsales.pokemonviewer.ui.pokelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mvpsales.pokemonviewer.databinding.PokemonListRvItemBinding
import com.mvpsales.pokemonviewer.model.PokemonListItem
import com.mvpsales.pokemonviewer.util.capitalized

class PokemonListAdapter(
    private val context: Context
): RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>() {

    private var pokemonList: List<PokemonListItem> = arrayListOf()
    var onClickPokemon: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val binding = PokemonListRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
        return PokemonViewHolder(binding)
    }

    override fun getItemCount(): Int = pokemonList.size

    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        holder.bind(pokemonList[position])
    }

    fun setPokemonList(data: List<PokemonListItem>) {
        pokemonList = data
        notifyDataSetChanged()
    }

    inner class PokemonViewHolder(private val binding: PokemonListRvItemBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(pokemon: PokemonListItem) {
            with(binding) {
                pokemonNameTv.text = pokemon.name.capitalized()
                pokemonIv.load(pokemon.imageUrl) {
                    crossfade(true)
                    transformations(CircleCropTransformation())
                }
                root.setOnClickListener {
                    onClickPokemon?.let {
                        it(pokemon.name)
                    }
                }
            }
        }
    }
}