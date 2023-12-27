package com.example.cymonbattle.battle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.cymonbattle.databinding.FragmentItemMenuBinding

class ItemMenuFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    lateinit var binding: FragmentItemMenuBinding
    private val sharedViewModel: BattleActivitySharedViewModel by activityViewModels()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentItemMenuBinding.inflate(inflater, container, false)
        binding.potionBtn.setOnClickListener {
            if(sharedViewModel.usePotion()){
                showToast("Your Pokemon was healed")
            } else {
                showToast("Your Pokemon is already at full HP")
            }
            performTurnOpponentOnly(container!!)
        }
        binding.pokeballBtn.setOnClickListener {

            if (sharedViewModel.isWildPokemon){
                if(sharedViewModel.usePokeBall()){
                    showToast("Catch successful")
                    (activity as BattleActivity).returnToMainMenu(false)
                } else {
                    showToast("Pokemon broke out, catch unsuccessful")
                    performTurnOpponentOnly(container!!)
                }
            } else {
                showToast("Cannot catch a pokemon with a trainer")
            }
        }

        return binding.root
    }

    /**
     * perform the turn for the opponent pokemon only
     * @param container used for navigation between fragments
     */
    private fun performTurnOpponentOnly(container: ViewGroup){
        val battleState = sharedViewModel.performTurnOpponentOnly()

        if(battleState == BattleActivitySharedViewModel.BattleState.EndedPlayerWin)
            actionAfterBattleEnded(true)
        else if(battleState == BattleActivitySharedViewModel.BattleState.EndedPlayerLose)
            actionAfterBattleEnded(false)
        else if(battleState == BattleActivitySharedViewModel.BattleState.WaitingSwitchPlayerPokemon)
            switchPlayerPokemon(container)
        else
            comeBackToMainMenu(container)
    }

    /**
     * what to do when battle ended
     * @param didPlayerWin if the player won
     */
    private fun actionAfterBattleEnded(didPlayerWin: Boolean){
        if(didPlayerWin) //playerwin
            showToast("You defeated all the opponent pokemon!")
        else
            showToast("All your pokemon were defeated")
        (activity as BattleActivity).returnToMainMenu(!didPlayerWin)
    }

    /**
     * force player to switch pokemon
     * @param container used for navigation between fragments
     */
    private fun switchPlayerPokemon(container: ViewGroup){
        val action = ItemMenuFragmentDirections.actionItemMenuFragmentToChoosePokemonFragment()
        // Navigate using that action
        container.findNavController().navigate(action)
    }

    /**
     * come back to main menu fragment
     * @param container used for navigation between fragments
     */
    private fun comeBackToMainMenu(container: ViewGroup){
        val action = ItemMenuFragmentDirections.actionItemMenuFragmentToMainBattleMenuFragment()
        // Navigate using that action
        container.findNavController().navigate(action)
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