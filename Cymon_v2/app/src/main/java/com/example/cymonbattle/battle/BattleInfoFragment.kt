package com.example.cymonbattle.battle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cymonbattle.databinding.FragmentBattleInfoBinding

/**
 * Presents the health bar, the level, and the name of a pokemon in battle
 */
class BattleInfoFragment : Fragment() {

    lateinit var binding: FragmentBattleInfoBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        binding = FragmentBattleInfoBinding.inflate(inflater, container, false)
        return binding.root
    }
}