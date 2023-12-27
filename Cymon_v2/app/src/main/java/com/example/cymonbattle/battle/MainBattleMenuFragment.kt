package com.example.cymonbattle.battle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.findNavController
import com.example.cymonbattle.databinding.FragmentMainBattleMenuBinding
import com.example.battleparty.Nubber;


class MainBattleMenuFragment : Fragment() {



    private lateinit var binding: FragmentMainBattleMenuBinding

    private val sharedViewModel: BattleActivitySharedViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentMainBattleMenuBinding.inflate(inflater, container, false)

        binding.fightBtn.setOnClickListener {
            val action = MainBattleMenuFragmentDirections.actionMainBattleMenuFragmentToFightMenuFragment()
            // Navigate using that action
            container!!.findNavController().navigate(action)

        }

        binding.choosePokemonBtn.setOnClickListener {
            val action = MainBattleMenuFragmentDirections.actionMainBattleMenuFragmentToChoosePokemonFragment()
            // Navigate using that action
            container!!.findNavController().navigate(action)
        }

        binding.useItemButton.setOnClickListener {
            val action = MainBattleMenuFragmentDirections.actionMainBattleMenuFragmentToItemMenuFragment()
            // Navigate using that action
            container!!.findNavController().navigate(action)
        }

        binding.runBtn.setOnClickListener {
            if(sharedViewModel.isWildPokemon)
                (activity as BattleActivity).returnToMainMenu(false)
            else
                Toast.makeText(
                    context,
                    "Cannot run from trainer battle.",
                    Toast.LENGTH_SHORT
                ).show()
        }
        return binding.root
    }

}