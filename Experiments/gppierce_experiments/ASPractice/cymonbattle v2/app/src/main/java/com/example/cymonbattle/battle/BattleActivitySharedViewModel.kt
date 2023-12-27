package com.example.cymonbattle.battle

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.cymonbattle.MainActivity
import com.example.cymonbattle.pokemon.Move
import com.example.cymonbattle.pokemon.Pokemon
import com.example.cymonbattle.trainer.Trainer
import kotlin.math.max
import kotlin.math.min
import kotlin.random.Random

/**
 * most of the business logic of the battle happens here and is shared to all fragments
 */
class BattleActivitySharedViewModel(
    isWildPokemon: Boolean,
    playerTrainer: Trainer

): ViewModel() {

    val isWildPokemon = isWildPokemon

    // string representing what to display to the user as battle dialog
    private var _battleDialog = MutableLiveData<String>()
    val battleDialog  : LiveData<String> = _battleDialog

    // playerTrainer
    private var _playerTrainer =  MutableLiveData<Trainer>(playerTrainer)
    val playerTrainer  : LiveData<Trainer> = _playerTrainer

    // activePlayerPokemon
    private var _activePlayerPokemon =  MutableLiveData<Pokemon>(_playerTrainer.value!!.pokemonTeam.filter { !it.isDead }[0])
    val activePlayerPokemon : LiveData<Pokemon> = _activePlayerPokemon

    fun setActivePlayerPokemon(pokemon: Pokemon){
        _activePlayerPokemon.value = pokemon
        addDialog("Player ${activePlayerPokemon.value!!.SPECIES} is sent out")
    }

    // activeOpponentPokemon
    private var _activeOpponentPokemon = MutableLiveData<Pokemon>()
    val activeOpponentPokemon : LiveData<Pokemon> = _activeOpponentPokemon

    // opponentTrainer
    private var _opponentTrainer: MutableLiveData<Trainer>? =  MutableLiveData<Trainer>()

    init {
        if(isWildPokemon){
            val randPokemonNumber = Random.nextInt(151) + 1
            _activeOpponentPokemon.value = Pokemon(randPokemonNumber.toString(), "", calcRandomOpponentPokemonLevel())
            _opponentTrainer = null
        } else {
            val randNumber = Random.nextInt(6) + 1
            _opponentTrainer!!.value = Trainer("")
            for (i in 1..randNumber) {
                val randPokemonNumber = Random.nextInt(151) + 1
                _opponentTrainer!!.value!!.addPokemonToTeamOrCollection(Pokemon(randPokemonNumber.toString(), "", calcRandomOpponentPokemonLevel()))
            }
            _activeOpponentPokemon.value = _opponentTrainer!!.value!!.pokemonTeam!![0]

        }
    }

    /**
     * used to randomly generate a level for an opponent pokemon
     * @return level
     */
    private fun calcRandomOpponentPokemonLevel(): Int{
        var highestLevel: Int = playerTrainer.value!!.pokemonTeam!![0].level
        var lowestLevel: Int = playerTrainer.value!!.pokemonTeam!![0].level
        playerTrainer.value?.pokemonTeam?.forEach {
            if(it.level > highestLevel) highestLevel = it.level
            if(it.level < lowestLevel) lowestLevel = it.level
        }
        return Random.nextInt(max(lowestLevel + 5, 0), min(highestLevel + 6, 100))
    }

    /**
     * perform a turn with the chosen player move
     * @param playerMove chosen player move
     * @return BattleState
     */
    fun performTurn(playerMove: Move): BattleState{
        _battleDialog.value = ""
        val opponentMoves = activeOpponentPokemon.value!!.MOVES
        val opponentMove: Move = opponentMoves[Random.nextInt(opponentMoves.size)]

        if(activePlayerPokemon.value!!.speedStat >= activeOpponentPokemon.value!!.speedStat){
            usePokemonMove(activePlayerPokemon.value!!, activeOpponentPokemon.value!!, playerMove)
            if(activeOpponentPokemon.value!!.isDead)
                return actionActiveOpponentPokemonDied()
            _activeOpponentPokemon.value = activeOpponentPokemon.value // update the ui
            usePokemonMove(activeOpponentPokemon.value!!, activePlayerPokemon.value!!, opponentMove)
            if(activePlayerPokemon.value!!.isDead)
                return actionActivePlayerPokemonDied()
            _activePlayerPokemon.value = activePlayerPokemon.value

        } else {
            usePokemonMove(activeOpponentPokemon.value!!, activePlayerPokemon.value!!, opponentMove)
            if(activePlayerPokemon.value!!.isDead)
                return actionActivePlayerPokemonDied()
            _activePlayerPokemon.value = activePlayerPokemon.value
            usePokemonMove(activePlayerPokemon.value!!, activeOpponentPokemon.value!!, playerMove)
            if(activeOpponentPokemon.value!!.isDead)
                return actionActiveOpponentPokemonDied()
            _activeOpponentPokemon.value = activeOpponentPokemon.value
        }


        return BattleState.InProgress
    }

    /**
     * perform a turn where the only the Opponent pokemon gets to act
     * @return Battle state
     */
    fun performTurnOpponentOnly(): BattleState{
        _battleDialog.value = ""
        val opponentMoves = activeOpponentPokemon.value!!.MOVES
        val opponentMove: Move = opponentMoves[Random.nextInt(opponentMoves.size)]

        usePokemonMove(activeOpponentPokemon.value!!, activePlayerPokemon.value!!, opponentMove)
        _activePlayerPokemon.value = activePlayerPokemon.value
        if(activePlayerPokemon.value!!.isDead)
            return actionActivePlayerPokemonDied()

        return BattleState.InProgress
    }

    /**
     * represents the state of the battle, used to communicated with outside classes
     */
    enum class BattleState {
        EndedPlayerWin,
        EndedPlayerLose,
        InProgress,
        WaitingSwitchPlayerPokemon,
        PlayerPokemonCanLearnNewMove
    }

    /**
     * actions to do when player pokemon died
     * @return battle state
     */
    private fun actionActivePlayerPokemonDied(): BattleState{
        _activePlayerPokemon.value = activePlayerPokemon.value
        addDialog("Player ${activePlayerPokemon.value!!.SPECIES} fainted")

        val alivePokemons = _playerTrainer?.value!!.pokemonTeam.filter { !it.isDead }
        if(alivePokemons.size == 0) return BattleState.EndedPlayerLose

        return BattleState.WaitingSwitchPlayerPokemon

    }

    /**
     * actions to do when opponent pokemon died
     * @return battle state
     */
    private fun actionActiveOpponentPokemonDied(): BattleState{
        addDialog("Opponent ${_activeOpponentPokemon.value!!.SPECIES} fainted")
        xpUpActivePlayerPokemon()

        if(isWildPokemon){
            return BattleState.EndedPlayerWin
        }

        val alivePokemons = _opponentTrainer?.value!!.pokemonTeam.filter { !it.isDead }
        if(alivePokemons.size == 0) return BattleState.EndedPlayerWin

        _activeOpponentPokemon.value = alivePokemons[0]
        addDialog("Opponent ${_activeOpponentPokemon.value!!.SPECIES} is sent out")

        return BattleState.InProgress

    }

    /**
     * increase xp and level up active player pokemon
     */
    private fun xpUpActivePlayerPokemon(){
        val xpGained = activePlayerPokemon.value!!.addExperienceToPokemon(activeOpponentPokemon.value!!, !isWildPokemon)
        addDialog("Player ${activePlayerPokemon.value!!.SPECIES} gained $xpGained xp")
        if(activePlayerPokemon.value!!.canPokemonLevelUp()) {
            activePlayerPokemon.value!!.levelUpPokemon()
            addDialog("Player ${activePlayerPokemon.value!!.SPECIES} leveled up!")
        }
        _activePlayerPokemon.value = activePlayerPokemon.value
    }

    /**
     * add a message to the battle dialog string
     * @param text message
     */
    private fun addDialog(text: String){
        if(!_battleDialog.value.isNullOrEmpty())
            _battleDialog.value += "\n"
        _battleDialog.value += text
    }

    /**
     * make pokemon use its move on another pokemon
     * @param pokemonAttacking attacking pokemon
     * @param pokemonDefending pokemon receiving the attack
     * @param move move being used
     */
    private fun usePokemonMove(pokemonAttacking: Pokemon, pokemonDefending: Pokemon, move: Move){
        if (!pokemonAttacking.MOVES.contains(move)){ throw IllegalArgumentException("Move not found") }


        if( move.damageClass == "status"){
            return
        }
        // Get move with decreased pp else return false if move not usable
        if(move.currentPP>0){
            // Lower PP of move
            move.currentPP -= 1
            if((move.accuracy != null && Random.nextInt(0, 100)+1 <= move.accuracy) || move.accuracy == null){
                addDialog("${pokemonAttacking.SPECIES} uses ${move.name} (${move.damageClass})")
                if (move.heal != 0) {
                    pokemonAttacking.healMe(move.heal)
                } else {
                    // Decrease pokemon HP
                    val damage: Int = pokemonAttacking.attack(pokemonDefending, move, MainActivity.TYPE_EFFECTIVENESS)
                    pokemonDefending.decreaseHP(damage)
                }
            } else {
                addDialog("${pokemonAttacking.SPECIES} uses ${move.name} but MISSES")
            }
        }
    }

    /**
     * use pokeball on opponent pokemon
     * @return is successful
     */
    fun usePokeBall():Boolean{
        // Get probability of capture
        if(!isWildPokemon) return false

        val opponent = activeOpponentPokemon.value!!

        val probabilityOfCapture: Double = ( 1 - (opponent.currentHpStat.toDouble()/opponent.maxHpStat.toDouble()))

        if(Random.nextDouble(0.0,1.0) <= probabilityOfCapture){
            playerTrainer.value!!.addPokemonToTeamOrCollection(activeOpponentPokemon.value!!)
            return true
        }
        // Could not Capture
        return false
    }

    /**
     * use potion on active player pokemon
     * @return if pokemon was healed
     */
    fun usePotion():Boolean{
        val isHealed = activePlayerPokemon.value!!.healMe(20)
        _activePlayerPokemon.value = activePlayerPokemon.value!!

        return isHealed
    }
}