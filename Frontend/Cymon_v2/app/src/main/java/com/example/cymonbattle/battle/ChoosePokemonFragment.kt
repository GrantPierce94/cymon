package com.example.cymonbattle.battle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.addCallback
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import com.example.cymonbattle.databinding.FragmentChoosePokemonBinding

/**
 * Used to change pokemon in battle
 */
class ChoosePokemonFragment : Fragment() {

    private lateinit var binding: FragmentChoosePokemonBinding

    private val sharedViewModel: BattleActivitySharedViewModel by activityViewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Disable onBack click
        requireActivity().onBackPressedDispatcher.addCallback(this) {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentChoosePokemonBinding.inflate(inflater, container, false)
        binding.choosePkmnRecyclerView.adapter =
            ChangePokemonAdapter(
                sharedViewModel.playerTrainer.value!!.pokemonTeam,
                sharedViewModel,
                { showToast("Cannot switch. This pokemon is unable to battle.") },
                { comeBackToMainMenu(container!!) }
            ){
                performTurnOpponentOnly(container!!)
            }
        binding.choosePkmnRecyclerView.layoutManager = GridLayoutManager(context, 2)
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
        val action = ChoosePokemonFragmentDirections.actionChoosePokemonFragmentSelf()
        // Navigate using that action
        container.findNavController().navigate(action)
    }

    /**
     * come back to main menu fragment
     * @param container used for navigation between fragments
     */
    private fun comeBackToMainMenu(container: ViewGroup){
        val action = ChoosePokemonFragmentDirections.actionChoosePokemonFragmentToMainBattleMenuFragment()
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