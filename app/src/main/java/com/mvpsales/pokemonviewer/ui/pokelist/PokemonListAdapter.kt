package com.mvpsales.pokemonviewer.ui.pokelist

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import coil.transform.CircleCropTransformation
import com.mvpsales.pokemonviewer.databinding.PokemonListLoadingItemBinding
import com.mvpsales.pokemonviewer.databinding.PokemonListRvItemBinding
import com.mvpsales.pokemonviewer.model.PokemonListItem
import com.mvpsales.pokemonviewer.util.capitalized

class PokemonListAdapter(
    private val context: Context
): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private var pokemonList: List<PokemonListItem> = arrayListOf()
    var onClickPokemon: ((String) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == ItemType.POKEMON_ITEM.type) {
            val binding =
                PokemonListRvItemBinding.inflate(LayoutInflater.from(context), parent, false)
            PokemonViewHolder(binding)
        } else {
            val binding =
                PokemonListLoadingItemBinding.inflate(LayoutInflater.from(context), parent, false)
            LoadingViewHolder(binding)
        }
    }

    override fun getItemViewType(position: Int): Int =
        if (position < pokemonList.size) {
            ItemType.POKEMON_ITEM.type
        } else {
            ItemType.LOADING_ITEM.type
        }

    override fun getItemCount(): Int = pokemonList.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (holder is PokemonViewHolder) {
            holder.bind(pokemonList[position])
        }
    }

    fun setPokemonList(data: List<PokemonListItem>) {
        pokemonList = data
        notifyDataSetChanged()
    }

    fun updatePokemonList(data: List<PokemonListItem>) {
        pokemonList += data
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

    inner class LoadingViewHolder(binding: PokemonListLoadingItemBinding): RecyclerView.ViewHolder(binding.root)

    enum class ItemType(val type: Int) {
        POKEMON_ITEM(0),
        LOADING_ITEM(1)
    }
}