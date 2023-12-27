package com.example.cymonbattle

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.coroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.cymonbattle.battle.BattleActivity
import com.example.cymonbattle.databinding.FragmentMainMenuBinding
import com.example.cymonbattle.pokemon.Move
import com.example.cymonbattle.pokemon.Pokemon
import com.example.cymonbattle.roomDB.RoomDB
import com.example.cymonbattle.roomDB.entity.MoveEntity
import com.example.cymonbattle.roomDB.entity.PlayerEntity
import com.example.cymonbattle.roomDB.entity.PokemonEntity
import com.example.cymonbattle.trainer.Trainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MainMenuFragment : Fragment() {
    private lateinit var userTrainer: Trainer
    private var _binding: FragmentMainMenuBinding? = null
    private val binding: FragmentMainMenuBinding
        get() = this._binding!!

    private lateinit var spinner: ProgressBar
    val database by lazy { RoomDB.getDatabase(requireContext()) }

    companion object {
        val TRAINER_STRING_ARG = "user_trainer"
        val IS_WILD_POKEMON = "is_wild_pokemon"
        val ARE_ALL_PLAYER_POKEMON_DEAD = "are_all_player_pokemon_dead"
    }

    private val battleActivityLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { activityResult ->
            val trainer = activityResult.data?.extras?.getSerializable(TRAINER_STRING_ARG) as Trainer
            userTrainer = trainer
            userTrainer.fetchAllImages()
            val areDead = activityResult.data?.extras?.getBoolean(ARE_ALL_PLAYER_POKEMON_DEAD)
            if (areDead != null && areDead) {
                userTrainer.healAllPokemon()
                userTrainer.resetPPOfPokemon()
            }
        }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        userTrainer = requireArguments().getSerializable(TRAINER_STRING_ARG) as Trainer
        // override back button
        activity?.onBackPressedDispatcher?.addCallback(this, object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                return
            }
        })
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentMainMenuBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.spinner = requireActivity().findViewById(R.id.progressBar1)
        binding.changeTeamButton.setOnClickListener {
            //call change team fragment
            val action = userTrainer.let { MainMenuFragmentDirections.actionFragmentMainMenuToChangeTeamFragment(it)}
            view.findNavController().navigate(action)
        }

        binding.pokeCenterButton.setOnClickListener {
            userTrainer.healAllPokemon()
            userTrainer.resetPPOfPokemon()
            Toast.makeText(context,"All pokemon are healed and rested.",Toast.LENGTH_SHORT).show()
        }

        binding.trainerBattleButton.setOnClickListener {
            this.spinner.visibility = View.VISIBLE
            goToBattleActivity(false)
        }

        binding.wildPokemonButton.setOnClickListener {
            this.spinner.visibility = View.VISIBLE
            goToBattleActivity(true)
        }

        binding.saveButton.setOnClickListener {
            Toast.makeText(requireContext(),"Saving Player - Do not leave the game to not lose data",Toast.LENGTH_SHORT).show()
            lifecycleScope.launch(Dispatchers.IO) {
                val moveEntities: MutableList<MoveEntity> = mutableListOf()
                // Save Trainer to DB
                val collection: MutableList<PokemonEntity> = mutableListOf()
                // Save Collection
                userTrainer.collection.forEach {
                    moveEntities.addAll(getAllPokemonMovesEntity(it.currentMoves))
                    val entity = pokemonToPokemonEntity(it)
                    collection.add(entity)
                }
                // Save Team
                val team: MutableList<PokemonEntity> = mutableListOf()
                userTrainer.pokemonTeam.forEach {
                    moveEntities.addAll(getAllPokemonMovesEntity(it.currentMoves))
                    val entity = pokemonToPokemonEntity(it)
                    team.add(entity)
                }
                val playerEntity = PlayerEntity(userTrainer.name, team, collection)
                // Save to database
                lifecycle.coroutineScope.launch(Dispatchers.IO) {
                    val job = launch {
                        database.playerDAO().deletePlayersFromTable()
                        database.moveDAO().insertMoveEntity(moveEntities)
                        database.playerDAO().insertPlayer(playerEntity)
                    }
                    job.join()
                }.invokeOnCompletion {
                    activity?.runOnUiThread {
                        Toast.makeText(requireContext(),"Player Saved - You may now safely exit the game.",Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    override fun onStop() {
        super.onStop()
        this.spinner.visibility = View.GONE
    }

    /**
     * This Move Create Move Entities from a List of Moves
     * @param currentMoves - List of Moves
     * @return MutableList<MoveEntity> - List of MoveEntity
     */
    private fun getAllPokemonMovesEntity(currentMoves: MutableList<Move>): MutableList<MoveEntity> {
        val allMovesEntity: MutableList<MoveEntity> = mutableListOf()
        currentMoves.forEach {
            allMovesEntity.add(
                MoveEntity(it.name, it.level, it.category, it.damageClass, it.target, it.type,
                    it.accuracy, it.heal, it.power, it.maxPP, it.aliment, it.alimentChance
                )
            )
        }
        return allMovesEntity
    }

    /**
     * creates an intent to go to battle activity
     * @param isWildPokemon determines if it is a trainer or wild battle
     */
    private fun goToBattleActivity(isWildPokemon: Boolean) {
        val intent = Intent(context, BattleActivity::class.java)
        intent.putExtra(TRAINER_STRING_ARG, userTrainer)
        intent.putExtra(IS_WILD_POKEMON, isWildPokemon)
        battleActivityLauncher.launch(intent)
    }

    /**
     * This method creates a PokemonEntity from a pokemon
     * @param pokemon - Pokemon to convert to an entity
     * @return PokemonEntity - Entity for database
     */
    private fun pokemonToPokemonEntity(pokemon: Pokemon): PokemonEntity {
        val currentMoves: MutableList<String> = mutableListOf()
        pokemon.currentMoves.forEach { currentMoves.add(it.name) }

        return PokemonEntity(
            pokemon.SPECIES,pokemon.NICK_NAME, pokemon.level,pokemon.maxHpStat, pokemon.baseStatAttack,
            pokemon.baseStatDefense, pokemon.baseStatSpecialAttack,pokemon.baseStatSpecialDefense,pokemon.baseStatSpeed,
            pokemon.front_image_url,pokemon.back_image_url,pokemon.types,currentMoves)
    }
}

