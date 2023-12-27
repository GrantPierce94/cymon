package com.example.cymonbattle.battle

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.cymonbattle.trainer.Trainer

/**
 * allows the shared view model to have constructor parameters
 */
class BattleActivitySharedViewModelFactory(
    private val isWildPokemon: Boolean,
    private val playerTrainer: Trainer
): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(BattleActivitySharedViewModel::class.java)){
            return BattleActivitySharedViewModel(isWildPokemon, playerTrainer) as T
        }
        throw java.lang.IllegalArgumentException("ViewModel class not found")
    }

}