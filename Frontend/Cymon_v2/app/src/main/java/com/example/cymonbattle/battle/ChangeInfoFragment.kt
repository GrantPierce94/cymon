package com.example.cymonbattle.battle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.cymonbattle.R

/**
 * Presents the image, the health bar, the level, and the name of a pokemon in battle
 * used to choose between pokemon when switching the active pokemon in battle
 */
class ChangeInfoFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.change_pokemon_info_item, container, false)
    }
}