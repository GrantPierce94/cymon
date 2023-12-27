package com.example.cymonbattle.pokemon

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.util.Log
import com.example.cymonbattle.api.PokemonAPI
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import java.util.*
import kotlin.collections.*
import kotlin.math.floor
import kotlin.math.max
import kotlin.math.min
import kotlin.math.pow


@SuppressLint("LongLogTag")
class Pokemon(
    private var species: String,
    private var nickName: String = species,
    private var initialLevel: Int
) : java.io.Serializable {
    // Pokemon
    val SPECIES: String get() = species
    val NICK_NAME: String get() = nickName

    // Front and Back Sprites
    @Transient
     var FRONT_IMAGE: Bitmap? = null

    @Transient
     var BACK_IMAGE: Bitmap? = null

    val front_image_url: String
    val back_image_url: String

    // Base Stats
    val baseExperienceReward: Int
    val baseStatAttack: Int
    val baseStatDefense: Int
    val baseStateHp: Int
    val baseStatSpecialAttack: Int
    val baseStatSpecialDefense: Int
    val baseStatSpeed: Int

    //    private var _level: Int
    private var _currentExperience: Float
    private var _isDead: Boolean = false

    private var _allMoves: MutableList<MutableMap<String, String>>
    var currentMoves: MutableList<Move> internal set

    // Battle Statistic
    var attackStat: Int
    var defenseStat: Int internal set
    var specialAttackStat: Int internal set
    var specialDefenseStat: Int internal set
    var speedStat: Int internal set
    var maxHpStat: Int internal set
    var currentHpStat: Int

    val MOVES: List<Move> get() = currentMoves
    val isDead: Boolean get() = _isDead

    var types: List<String> internal set
    var level: Int internal set


    init {
        // Set Level
        this.level = initialLevel
        // Calculate Experience
        this._currentExperience = calculateCurrentExperience()
        this.currentMoves = mutableListOf()

        // Get All Pokemon Moves
        runBlocking {
            _allMoves = PokemonAPI.getAllPokemonMovesJson(species)
                ?: throw IllegalArgumentException("Could not get all pokemon moves. Check api link.")
        }
        // TODO: FETCH FROM LOCAL DATABASE
        var pokeJson: JSONObject?
        runBlocking {
            pokeJson = PokemonAPI.getPokemon(species)
        }

        // Checks to see if pokemon was fetched
        if (pokeJson == null) {
            Log.e("POKEMON COULD NOT BE FETCHED from api", "species : $species")
            throw IllegalArgumentException("Something went wrong while getting your pokemon. ${this::class.java}")
        }
        // Set Species
        this.species = pokeJson!!.getString("species")

        // Base Stats
        this.baseExperienceReward = pokeJson!!.getInt("base_exp_reward")
        this.baseStateHp = pokeJson!!.getInt("base_maxHp")
        this.baseStatAttack = pokeJson!!.getInt("base_attack")
        this.baseStatDefense = pokeJson!!.getInt("base_defense")
        this.baseStatSpecialAttack = pokeJson!!.getInt("base_special-attack")
        this.baseStatSpecialDefense = pokeJson!!.getInt("base_special-defense")
        this.baseStatSpeed = pokeJson!!.getInt("base_speed")

        // Types
        val types: JSONArray = pokeJson!!.getJSONArray("types")
        val mutableListOfTypes: MutableList<String> = mutableListOf<String>()
        (0 until types.length()).forEach {
            mutableListOfTypes.add(types[it] as String)
        }

        // Set Types
        this.types = mutableListOfTypes

        // Set image url
        this.front_image_url = pokeJson!!.getString("front_sprite")
        this.back_image_url = pokeJson!!.getString("back_sprite")

        // Get Front and Back Images
        // This try catch should never be called unless api fails
        runBlocking {
            lateinit var jobBackImage: Job
            try {
                jobBackImage = launch {
                    BACK_IMAGE = PokemonAPI.getPokemonImage(back_image_url)!!
                    FRONT_IMAGE = PokemonAPI.getPokemonImage(front_image_url)!!
                }
            } catch (e: Exception) {
                Log.e("COULDN'T GET POKEMON IMAGE ${this::class.java}", e.stackTraceToString())
            }

            // List of move to be removed after a pokemon learned them
            val removedMoves: MutableList<MutableMap<String, String>> = mutableListOf()
            (_allMoves.indices).forEach { index ->
                val moveLevel: Int = _allMoves[index]["level"].toString().toDouble().toInt()
                var moveJson: JSONObject?
                if (level >= moveLevel) {
                    // TODO CHECK IF IN DATABASE
                    runBlocking { moveJson = PokemonAPI.getMoveJson(_allMoves[index]["move"]!!) }
                    // Check if move was fetched
                    if (moveJson == null) {
                        Log.e(
                            "FATAL MOVE COULD NOT BE FOUND",
                            "${_allMoves[index]} could not be found in the api"
                        )
                        return@forEach
                    }
                    // Create Move
                    val moveToAdd: Move = createMove(moveJson!!, moveLevel)
                    // Check if pokemon has filled all his moves
                    if (currentMoves.size == 4) {
                        // Replace move with lowest level
                        val lowestLevelMove: Move? = currentMoves.minByOrNull { it.level }
                        currentMoves.remove(lowestLevelMove)
                        currentMoves.add(moveToAdd)
                        // Pokemon can no longer learn a learned move
                        removedMoves.add(_allMoves[index])
                    } else {
                        currentMoves.add(moveToAdd)
                        // Pokemon can no longer learn a learned move
                        removedMoves.add(_allMoves[index])
                    }
                } else {
                    return@forEach
                }
            }
            // Remove moves learned from all moves it can learn
            _allMoves.removeAll(removedMoves)
            jobBackImage.join()
            Log.i("MOVE is", "POKEMON FINISHED CREATING")
        }
    }

    init {
        // Calculate Statistics
        this.attackStat = this.calculateBattleStatistic(this.baseStatAttack)
        this.defenseStat = this.calculateBattleStatistic(this.baseStatDefense)
        this.specialAttackStat = this.calculateBattleStatistic(this.baseStatSpecialAttack)
        this.specialDefenseStat = this.calculateBattleStatistic(this.baseStatSpecialAttack)
        this.speedStat = this.calculateBattleStatistic(this.baseStatSpeed)
        this.maxHpStat = this.calculateHPStatistic(this.baseStateHp)
        this.currentHpStat = this.maxHpStat
    }

    /**
     * This method fetch the images of the Pokemon
     */
    fun fetchImages() {
        runBlocking {
            launch {
                BACK_IMAGE = PokemonAPI.getPokemonImage(back_image_url)!!
                FRONT_IMAGE = PokemonAPI.getPokemonImage(front_image_url)!!
            }
        }
    }


    /**
     * This method decreases the Pokemons' HP based on the damage done by the Attacking Pokemon.
     *
     * @param opponent: Defending Pokemon - Opponent
     * @param move : Move that is used by the attacking pokemon
     */
    fun attack(opponent: Pokemon, move: Move,TYPE_EFFECTIVENESS: Map<String, String>): Int {
        // Method to calculate damage
        fun damageCalc(attackStat: Int, defenseStat: Int): Double {
            return (((((2.0 * opponent.initialLevel) / 5.0) + 2.0) / 50.0) * move.power!! * (attackStat.toDouble() / defenseStat.toDouble()).toDouble()) + 2.0
        }
        // Calculate damage
        var dmg: Double = if (move.damageClass == "physical") {
            damageCalc(opponent.attackStat, this.defenseStat)
        } else {
            damageCalc(opponent.specialAttackStat, this.specialDefenseStat)
        }
        // Multiply by 1.5 if the attacking pokemon has same type as it's move
        opponent.types.forEach {
            if (it.lowercase() == move.type.lowercase()) {
                dmg *= 1.5
                return@forEach
            }
        }
        // Type Effectiveness
        this.types.forEach {
            when (TYPE_EFFECTIVENESS["${move.type}-${it}"]) {
                "super_effective" -> dmg *= 2.0
                "not_very_effective" -> dmg *= 1.0 / 2.0
                "no_effect" -> dmg *= 0.0
            }
        }
        return floor(dmg).toInt()
    }

    /**
     * This method replaces all current moves of a pokemon - used for loading trainer pokemon
     */
    fun replaceCurrentMoves(moves: MutableList<Move>) {
        this.currentMoves = moves
    }

    /**
     * This method resets all the Pokemon's' moves PP to their max PP.
     */
    fun resetAllMovesPP() {
        this.currentMoves.forEach {
            it.resetPP()
        }
    }

    /**
     * This method decreases the pokemon hp based on damage taken.
     *
     * @param damage Damage taken = HP to remove
     */
    fun decreaseHP(damage: Int) {
        currentHpStat = max(currentHpStat - damage, 0)
        if (currentHpStat == 0) {
            _isDead = true
        }
    }

    /**
     * This method heals the pokemon by the amount specified.
     *
     * @param hp : Amount to heal
     * @return Boolean: healed or not
     */
    fun healMe(hp: Int): Boolean {
        if (currentHpStat >= maxHpStat) {
            return false
        }
        currentHpStat = min(currentHpStat + hp, maxHpStat)
        return true
    }

    /**
     *
     * This method sets the current HP to the MAX HP
     */
    fun resetHP() {
        this.currentHpStat = this.maxHpStat
        _isDead = false
    }

    /**
     * This method uses a Pokemon's Move at a certain position.
     * It Decrease the moves PP.
     *
     * @param index : Index of the Pokemon's' move to use
     */
    fun useMove(index: Int): Move? {
        val move: Move = this.currentMoves[index]
        if (!move.isUsable) {
            return null
        }
        move.currentPP -= 1
        return move
    }


    /**
     * This method adds experience to the pokemon.
     * Formula: 0.3 * opponentBaseExperience * opponentLevel
     * @param exp : Experience to add to pokemon
     */
    fun addExperienceToPokemon(opponentPokemon: Pokemon, isTrainerBattle: Boolean): Int {
        val multiplier = if (isTrainerBattle) 2 else 1
        val expGained: Double =
            0.3 * opponentPokemon.baseExperienceReward * opponentPokemon.level * multiplier
        this._currentExperience += expGained.toInt()
        return expGained.toInt()
    }

    /**
     * This method levels up a pokemon.
     * It calculate the Pokemon's experience.
     * It sets the level from the EXP.
     * It recalculates and sets the Battle Statstics.
     */
    fun levelUpPokemon() {
        setLevelFromEXP()
        setAllStatistics()
    }

    /**
     * learn a move (and delete it from the _allMoves array)
     * @param move move to learn
     * @param indexInAllMoves index of the move to delete in _allMoves array
     * @param moveToReplace move to replace (can be null if no need to replace a move)
     */
    fun learnNewMove(move: Move, indexInAllMoves: Int, moveToReplace: Move? = null) {
        // move to replace is given but is not in current moves
        // or no move to replace is Not given but nb current moves is 4
        if ((moveToReplace != null && !currentMoves.contains(moveToReplace))
            || (currentMoves.size == 4 && moveToReplace == null)
        ) {
            throw IllegalArgumentException()
        }
        if (currentMoves.size == 4) {
            currentMoves.remove(moveToReplace)
            currentMoves.add(move)
            // Pokemon can no longer learn a learned move
            _allMoves.removeAt(indexInAllMoves)
        } else {
            currentMoves.add(move)
            // Pokemon can no longer learn a learned move
            _allMoves.removeAt(indexInAllMoves)
        }
    }

    /**
     * removes a move from _allMoves of the pokemon
     * @param index index of the move
     */
    fun removeMoveFromAllMoves(index: Int) {
        _allMoves.removeAt(index)
    }

    /**
     * get new move to learn if any
     * @return nullable Move and index of the move in _allMoves array to be able to delete from there it when learning it
     */
    fun getNewMoveToLearn(): Pair<Move, Int>? {
        (_allMoves.indices).forEach { index ->
            val moveLevel: Int = _allMoves[index]["level"].toString().toDouble().toInt()
            var moveJson: JSONObject?
            if (level >= moveLevel) {
                runBlocking { moveJson = PokemonAPI.getMoveJson(_allMoves[index]["move"]!!) }
                // Check if move was fetched
                if (moveJson == null) {
                    Log.e(
                        "FATAL MOVE COULD NOT BE FOUND",
                        "${_allMoves[index]} could not be found in the api"
                    )
                    return@forEach
                }
                // Create Move
                return Pair(createMove(moveJson!!, moveLevel), index)
            } else {
                return@forEach
            }
        }
        return null
    }

    /**
     * This method looks if pokemon is able to level up.
     *
     * @return Boolean : true if pokemon can level up; false otherwise
     */
    fun canPokemonLevelUp(): Boolean {
        return level < calculateLevelFromEXP()
    }


    /**
     * This method calculates and sets all the Battle Statistics.
     */
    private fun setAllStatistics() {
        // Calculate Statistics
        this.attackStat = this.calculateBattleStatistic(this.baseStatAttack)
        this.defenseStat = this.calculateBattleStatistic(this.baseStatDefense)
        this.specialAttackStat = this.calculateBattleStatistic(this.baseStatSpecialAttack)
        this.specialDefenseStat = this.calculateBattleStatistic(this.baseStatSpecialAttack)
        this.speedStat = this.calculateBattleStatistic(this.baseStatSpeed)
        this.maxHpStat = this.calculateHPStatistic(this.baseStateHp)
        this.currentHpStat = this.maxHpStat
    }

    /**
     * This method calculates the Battle Statistic, but not for HP.
     * The formula is : floor(  ( ( baseStat + 10 ) * level ) / 50) + 5
     * @return Int : battle stat
     */
    private fun calculateBattleStatistic(stat: Int): Int {
        return (floor((((stat + 10) * this.level) / 50).toDouble()).toInt() + 5)
    }

    /**
     * This method calculates the HP Statistic.
     * The formula is : floor(  ( ( baseStat + 10 ) * level ) / 50) + level + 10
     * @return Int : HP stat
     */
    private fun calculateHPStatistic(stat: Int): Int {
        return (floor((((stat + 10) * this.level) / 50).toDouble()).toInt() + this.level + 10)
    }

    /**
     * This method calculates the current experience.
     * The formula is level = floor((experience)^(1/3))
     *
     * return Float : Current Experience
     */
    private fun calculateCurrentExperience(): Float {
        return level.toDouble().pow(3.0).toFloat()
    }

    /**
     * This method is used to set the level to based on experience.
     */
    private fun setLevelFromEXP() {
        this.level = calculateLevelFromEXP()
        if (this.level > 100) {
            this.level = 100
        }
    }

    /**
     * This method is used to calculate the potential level of the pokemon based on the current experience it has.
     * Does not modify current level.
     *
     * @return Int : Potential Level of pokemon based on experience
     */
    private fun calculateLevelFromEXP(): Int {
        return floor(_currentExperience.toDouble().pow(1.0 / 3.0)).toInt()
    }
}
