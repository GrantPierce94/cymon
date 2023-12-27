package com.example.cymonbattle.battle

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.recyclerview.widget.RecyclerView
import com.example.cymonbattle.databinding.MoveItemBinding
import com.example.cymonbattle.pokemon.Move

/**
 * adapter for the recycler view that displays the moves
 * when a move is chosen: performs a turn using the shared view model
 */
class MovesAdapter (
    private val movesList: List<Move>,
    private val sharedViewModel: BattleActivitySharedViewModel,
    private val lifecycleOwner: LifecycleOwner,
    private val actionAfterBattleEnded: (Boolean) -> Unit, // Boolean didPlayerWin
    private val switchPlayerPokemon: () -> Unit,
    private val notifyPlayerNoPP: () -> Unit,
    private val tryLearnMove: (Move, Int) -> Unit,
    private val actionGoToMainMenuFragment: () -> Unit
    ) :
    RecyclerView.Adapter<MovesAdapter.ViewHolder>() {

    class ViewHolder(val binding: MoveItemBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = MoveItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)

        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding
        val move = movesList[position]

        binding.moveNameText.text = move.name
        binding.moveTypeText.text = move.type
        binding.ppText.text = "${move.currentPP}/${move.maxPP}"
        binding.moveButton.setOnClickListener {

            if(move.currentPP == 0){
                notifyPlayerNoPP()
                actionGoToMainMenuFragment()
                return@setOnClickListener
            }

            val battleState = sharedViewModel.performTurn(move)

            managePlayerNewMove()

            if(battleState == BattleActivitySharedViewModel.BattleState.EndedPlayerWin)
                actionAfterBattleEnded(true)
            else if(battleState == BattleActivitySharedViewModel.BattleState.EndedPlayerLose)
                actionAfterBattleEnded(false)
            else if(battleState == BattleActivitySharedViewModel.BattleState.WaitingSwitchPlayerPokemon)
                switchPlayerPokemon()
            else
                actionGoToMainMenuFragment()

        }
        sharedViewModel.activePlayerPokemon.observe(lifecycleOwner) {
            binding.ppText.text = "${move.currentPP}/${move.maxPP}"
        }
    }

    /**
     * check if new move can be learned and do something about it
     */
    private fun managePlayerNewMove(){
        val (move, index) = sharedViewModel.activePlayerPokemon.value!!.getNewMoveToLearn()
            ?: return

        tryLearnMove(move, index)

    }

    override fun getItemCount(): Int = movesList.size

}