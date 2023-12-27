package com.example.cymonbattle

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.cymonbattle.databinding.FragmentNewGameBinding
import com.example.cymonbattle.pokemon.Move
import com.example.cymonbattle.pokemon.Pokemon
import com.example.cymonbattle.roomDB.RoomDB
import com.example.cymonbattle.roomDB.entity.MoveEntity
import com.example.cymonbattle.roomDB.entity.PlayerEntity
import com.example.cymonbattle.trainer.Trainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext


class NewGameFragment : Fragment() {
    private val database by lazy { RoomDB.getDatabase(requireContext()) }
    private var _binding: FragmentNewGameBinding? = null
    private val binding: FragmentNewGameBinding
        get() = this._binding!!

    private lateinit var spinner: ProgressBar

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        _binding = FragmentNewGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.spinner = requireActivity().findViewById(R.id.progressBar1)
        var playerEntity: PlayerEntity? = null

        runBlocking {
            runBlocking {
                lifecycleScope.launch(Dispatchers.Main) {
                    withContext(Dispatchers.IO) {
                        playerEntity = database.playerDAO().getPlayer()
                    }
                }.invokeOnCompletion {
                    if (playerEntity != null) {
                        spinner.visibility = View.VISIBLE
                        binding.newGameBtn.isEnabled = false
                        // Loading Trainer
                        Toast.makeText(requireContext(),"We are loading your trainer please wait",Toast.LENGTH_LONG).show()
                        loadTrainer(playerEntity!!, view, savedInstanceState)
                    }
                }
            }
            // Set Action to a Choose Starter Pokemon Fragment
            binding.newGameBtn.setOnClickListener {
                val action =
                    NewGameFragmentDirections.actionNewGameFragmentToChooseStarterPokemonFragment()
                view.findNavController().navigate(action)
            }
        }
    }

    override fun onStop() {
        super.onStop()
        spinner.visibility = View.GONE
    }

    /**
     * This method loads the Users trainer
     * @param playerEntity : Entity of the player
     * @param view : View of the Fragment
     * @param savedInstanceState : Bundle?
     */
    private fun loadTrainer(playerEntity: PlayerEntity, view: View, savedInstanceState: Bundle?) {
        val trainer = Trainer(playerEntity.name)
        lifecycleScope.launch(Dispatchers.Main) {
            val job = launch(Dispatchers.IO) {
                runBlocking {
                    playerEntity!!.pokemonTeam.forEach {
                        val pokemon = Pokemon(it.species, it.nickName, it.level)
                        val allMoves: MutableList<Move> = mutableListOf()
                        it.currentMoves!!.forEach { moveName ->
                            val moveEN = database.moveDAO().getMoveEntity(moveName)
                            allMoves.add(createMove(moveEN)                            )
                        }
                        pokemon.replaceCurrentMoves(allMoves)
                        trainer.addPokemonToTeamOrCollection(pokemon)
                    }
                    playerEntity.pokemonCollection.forEach {
                        val pokemon = Pokemon(it.species, it.nickName, it.level)
                        val allMoves: MutableList<Move> = mutableListOf()
                        it.currentMoves!!.forEach { moveName ->
                            val moveEN = database.moveDAO().getMoveEntity(moveName)
                            allMoves.add(createMove(moveEN))
                        }
                        pokemon.replaceCurrentMoves(allMoves)
                        trainer.addPokemonToTeamOrCollection(pokemon)
                    }
                }
            }
            job.join()
            // Launch Main Menu
            val action =NewGameFragmentDirections.actionNewGameFragmentToFragmentMainMenu(trainer)
            view.findNavController().navigate(action)
            super.onViewCreated(view, savedInstanceState)
        }
    }

    /**
     * This method Creates a Move from a MoveEntity
     * @param moveEN : Move Entity
     * @return Move
     */
    private fun createMove(moveEN: MoveEntity) = Move(moveEN.name, moveEN.levelReq,moveEN.category,
        moveEN.damageClass, moveEN.target,moveEN.type,moveEN.accuracy,moveEN.heal,
        moveEN.power,moveEN.pp,moveEN.aliment,moveEN.alimentChance)

}