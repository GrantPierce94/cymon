package com.example.cymonbattle.battle

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cymonbattle.databinding.FragmentFightMenuBinding
import com.example.cymonbattle.pokemon.Move

/**
 * Handles choosing a move to perform using a recyclerview
 */
class FightMenuFragment : Fragment() {

    private lateinit var binding: FragmentFightMenuBinding

    private val sharedViewModel: BattleActivitySharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentFightMenuBinding.inflate(inflater, container, false)
        binding.fightRecyclerView.adapter = MovesAdapter(
            sharedViewModel.activePlayerPokemon.value!!.MOVES,
            sharedViewModel,
            viewLifecycleOwner,
            {
                if(it) //did player win
                    showToast("You defeated all the opponent pokemon!")
                else
                    showToast("All your pokemon were defeated")
                (activity as BattleActivity).returnToMainMenu(!it)
            },
            {
                val action = FightMenuFragmentDirections.actionFightMenuFragmentToChoosePokemonFragment()
                container!!.findNavController().navigate(action)
            },
            { showToast("Cannot use a move with 0 PP.") },
            ::handleNewMoveLearn
        ) {
            val action = FightMenuFragmentDirections.actionFightMenuFragmentToMainBattleMenuFragment()
            container!!.findNavController().navigate(action)
        }
        binding.fightRecyclerView.layoutManager = GridLayoutManager(context, 2)

        return binding.root
    }

    private fun handleNewMoveLearn(move: Move, indexInAllMoves: Int){
        val pokemon = sharedViewModel.activePlayerPokemon.value!!
        val builder = AlertDialog.Builder(context)
        builder.setTitle("New Move Available")
        builder.setMessage("Your ${pokemon.SPECIES} can learn ${move.name}. \n Do you want it to do so?")

        builder.setPositiveButton("Yes") { _, _ ->
            if(pokemon.MOVES.size < 4){
                pokemon.learnNewMove(move, indexInAllMoves)
            } else {
                val chooseMoveAlertBuilder = AlertDialog.Builder(context)
                with(chooseMoveAlertBuilder){
                    setTitle("Choose Move To Replace")
                    setItems(pokemon.MOVES.map{
                        "${it.name} (${it.type})"
                    }.toTypedArray()){ _,which ->
                        pokemon.learnNewMove(move, indexInAllMoves, pokemon.MOVES[which])
                        showToast("Your ${pokemon.SPECIES} has successfully learned ${move.name}")
                    }
                    show()
                }
            }
        }

        builder.setNegativeButton("No") { _, _ ->
            showToast("Your ${pokemon.SPECIES} has not learned ${move.name}")
            pokemon.removeMoveFromAllMoves(indexInAllMoves)
        }

        builder.show()
    }

    /**
     * shows a toast
     * @param text message to show
     */
    private fun showToast(text: String){
        Toast.makeText(
            context,
            text,
            Toast.LENGTH_SHORT
        ).show()
    }

}