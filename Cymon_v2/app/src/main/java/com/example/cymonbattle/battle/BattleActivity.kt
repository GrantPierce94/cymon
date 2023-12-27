package com.example.cymonbattle.battle

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.TextView
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.battleparty.Nubber
import com.example.cymonbattle.MainMenuFragment
import com.example.cymonbattle.R
import com.example.cymonbattle.databinding.ActivityBattleBinding
import com.example.cymonbattle.pokemon.Pokemon
import com.example.cymonbattle.trainer.Trainer
import kotlin.random.Random

class BattleActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBattleBinding


    private lateinit var playerTrainer: Trainer
    public val nubber= Nubber();

    private var  isMulti: Boolean = false

    // Create the shared view model
    private val sharedViewModel: BattleActivitySharedViewModel by viewModels() {
        playerTrainer = intent.extras?.getSerializable(MainMenuFragment.TRAINER_STRING_ARG) as Trainer
            ?: getRandomTrainerForTesting()
        playerTrainer.fetchAllImages()
        val isWildPokemon = intent.extras?.getBoolean(MainMenuFragment.IS_WILD_POKEMON) ?: false
        isMulti = intent.extras?.getBoolean("IS_MULTI") ?: false
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

        nubber.txtStatus=findViewById(R.id.txtNuberStatus);

        if(!isMulti){
            val txtNuberStatus:TextView=findViewById(R.id.txtNuberStatus);
            txtNuberStatus.isVisible=false;
        }

    }

    public fun AppCompatActivity.runOnUiThreadExtension(action: Runnable) {
        if (Thread.currentThread() == Looper.getMainLooper().thread) {
            action.run()
        } else {
            Handler(Looper.getMainLooper()).post(action)
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
        //nubber.sendMessage("Trainer Created");
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

    companion object {
        @JvmStatic
        fun runOnUiThreadExtension(runnable: Runnable) {
            if (Thread.currentThread() == Looper.getMainLooper().thread) {
                runnable.run()
            } else {
                Handler(Looper.getMainLooper()).post(runnable)
            }
        }
    }

}






























