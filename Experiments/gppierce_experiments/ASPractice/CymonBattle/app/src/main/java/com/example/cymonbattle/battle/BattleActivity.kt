package com.example.cymonbattle.battle

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import com.example.cymonbattle.MainMenuFragment
import com.example.cymonbattle.R
import com.example.cymonbattle.databinding.ActivityBattleBinding
import com.example.cymonbattle.pokemon.Pokemon
import com.example.cymonbattle.trainer.Trainer
import kotlin.random.Random

class BattleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBattleBinding

    private lateinit var playerTrainer: Trainer

    // Create the shared view model
    private val sharedViewModel: BattleActivitySharedViewModel by viewModels() {
        playerTrainer = intent.extras?.getSerializable(MainMenuFragment.TRAINER_STRING_ARG) as Trainer
            ?: getRandomTrainerForTesting()
        playerTrainer.fetchAllImages()
        val isWildPokemon = intent.extras?.getBoolean(MainMenuFragment.IS_WILD_POKEMON) ?: false
        BattleActivitySharedViewModelFactory(isWildPokemon, playerTrainer)
    }

    // Override the back button
    override fun onBackPressed() {
        return
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBattleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // observe the shared view model fields
        sharedViewModel.activePlayerPokemon.observe(this) {
            binding.playerPokemonImageView.setImageBitmap(it.BACK_IMAGE)
            binding.playerPokemonInfoFragmentContainer.getFragment<BattleInfoFragment>().binding.apply {
                pkmnName.text = if(it.NICK_NAME != "") it.NICK_NAME else it.SPECIES
                pkmnLvl.text = getString(R.string.show_level, it.level.toString())
                hpProgressBar.max = it.maxHpStat
                hpProgressBar.progress = it.currentHpStat
                nbHpTxt.text = "${it.currentHpStat}/${it.maxHpStat}"
            }
        }

        sharedViewModel.activeOpponentPokemon.observe(this) {
            binding.opposingPokemonImageView.setImageBitmap(it.FRONT_IMAGE)
            binding.opposingPokemonInfoFragmentContainer.getFragment<BattleInfoFragment>().binding.apply {
                pkmnName.text = it.SPECIES
                pkmnLvl.text = getString(R.string.show_level, it.level.toString())
                hpProgressBar.max = it.maxHpStat
                hpProgressBar.progress = it.currentHpStat
                nbHpTxt.text = "${it.currentHpStat}/${it.maxHpStat}"
            }
        }

        sharedViewModel.battleDialog.observe(this) {
            binding.battleDialogueText.text = it
        }
    }

    /**
     * Creates random trainer for testing purposes
     * @return trainer
     */
    private fun getRandomTrainerForTesting(): Trainer{
        val trainer = Trainer("Adam")
        val randNumber = Random.nextInt(6) + 1
        for (i in 1..randNumber) {
            val randPokemonNumber = Random.nextInt(151) + 1
            trainer.addPokemonToTeamOrCollection(Pokemon(randPokemonNumber.toString(), "", Random.nextInt(25, 35)))
        }
        return trainer
    }

    /**
     * Returns to main activity
     * sends the player trainer and if all pokemon are dead
     * @return areAllPlayerPokemonDead
     */
    fun returnToMainMenu(areAllPlayerPokemonDead: Boolean){
        val intent = Intent(this, MainMenuFragment::class.java)
        intent.putExtra(MainMenuFragment.TRAINER_STRING_ARG, sharedViewModel.playerTrainer.value!!)
        intent.putExtra(MainMenuFragment.ARE_ALL_PLAYER_POKEMON_DEAD, areAllPlayerPokemonDead)
        setResult(RESULT_OK, intent)
        finish()
    }

}






























