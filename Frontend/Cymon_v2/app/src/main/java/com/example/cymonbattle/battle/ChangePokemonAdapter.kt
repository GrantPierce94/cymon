package com.example.cymonbattle.battle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.cymonbattle.databinding.ChangePokemonInfoItemBinding
import com.example.cymonbattle.pokemon.Pokemon

/**
 * adapter to the recycler view that displays the pokemon you can choose when switching pokemon
 */
class ChangePokemonAdapter (
    private val pokemonList: List<Pokemon>,
    private val sharedViewModel: BattleActivitySharedViewModel,
    private val actionTryToSwitchToDeadPokemon: () -> Unit,
    private val actionAfterChooseSamePokemon: () -> Unit,
    private val actionAfterChoose: () -> Unit
    ) :
    RecyclerView.Adapter<ChangePokemonAdapter.ViewHolder>() {

    class ViewHolder(val binding: ChangePokemonInfoItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ChangePokemonInfoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val pokemon = pokemonList[position]
        binding.changePkmnImg.setImageBitmap(pokemon.FRONT_IMAGE)

        binding.pkmnName.text = if(pokemon.NICK_NAME != "") pokemon.NICK_NAME else pokemon.SPECIES
        binding.pkmnLvl.text = "L" + pokemon.level.toString()
        binding.hpProgressBar.max = pokemon.maxHpStat
        binding.hpProgressBar.progress = pokemon.currentHpStat
        binding.nbHpTxt.text = "${pokemon.currentHpStat}/${pokemon.maxHpStat}"

        binding.changePokemonInfoItem.setOnClickListener {
            if(!pokemon.isDead) {
                if(pokemon == sharedViewModel.activePlayerPokemon.value!!)
                    // if chosen pokemon is already the active pokemon dont waist a turn
                    actionAfterChooseSamePokemon()
                else {
                    sharedViewModel.setActivePlayerPokemon(pokemon)
                    actionAfterChoose()
                }
            } else
                actionTryToSwitchToDeadPokemon()
        }
    }



    override fun getItemCount(): Int = pokemonList.size

}