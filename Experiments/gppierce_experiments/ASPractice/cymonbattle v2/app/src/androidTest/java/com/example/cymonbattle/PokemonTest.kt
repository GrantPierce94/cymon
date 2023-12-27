package com.example.cymonbattle

import androidx.test.platform.app.InstrumentationRegistry
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cymonbattle.pokemon.Move
import com.example.cymonbattle.pokemon.Pokemon

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*

/**
 * Instrumented test, which will execute on an Android device.
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
@RunWith(AndroidJUnit4::class)
class PokemonTest {
    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.example.pokemon_app", appContext.packageName)
    }

    @Test
//    @OptIn(ExperimentalCoroutinesApi::class)
    fun attackTest(){
        val pokemonOne = Pokemon("squirtle", "Electric", 1)
        //intilizase move
        val move = Move(
            "tackle",
            1,
            "physical",
            "damage",
            "enemy",
            "normal",
            100,
            0,
            40,
            35,
            "none",
            0
        )
        val pokemonTwo = Pokemon("squirtle", "Electric", 1);
        MainActivity.TYPE_EFFECTIVENESS = MainActivity.createTypeRelations()
        val dmg = pokemonOne.attack(pokemonTwo, move, MainActivity.TYPE_EFFECTIVENESS)
        pokemonTwo.decreaseHP(dmg)
        //check damage
        assertEquals(pokemonTwo.maxHpStat-dmg, pokemonTwo.currentHpStat)
    }

    //test resetAllMovesPP
    @Test
    fun resetAllMovesPPTest(){
        val pokemonOne = Pokemon("squirtle", "Electric", 1)
        val move = Move(
            "tackle",
            1,
            "physical",
            "damage",
            "enemy",
            "normal",
            100,
            0,
            40,
            35,
            "none",
            0
        )
        pokemonOne.resetAllMovesPP()
        assertEquals(35, move.maxPP)
    }

    @Test
    fun decreaseHPTest(){
        val pokemonOne = Pokemon("squirtle", "Electric", 1)
        pokemonOne.decreaseHP(10)
        assertEquals(pokemonOne.maxHpStat-10, pokemonOne.currentHpStat)
        //test if hp is equal to 0
        pokemonOne.decreaseHP(100)
        assertEquals(0, pokemonOne.currentHpStat)
        assertEquals(true, pokemonOne.isDead)
    }

    //test healMe Method
    @Test
    fun healMeTest(){
        val pokemonOne = Pokemon("squirtle", "Electric", 60)
        pokemonOne.decreaseHP(20)
        pokemonOne.healMe(10)
        assertEquals(pokemonOne.maxHpStat-20+10, pokemonOne.currentHpStat)

        //if currentHpStat is greater than maxHpStat
        pokemonOne.healMe(100)
        assertEquals(pokemonOne.maxHpStat, pokemonOne.currentHpStat)
    }

    //test the resetHP Method
    @Test
    fun resetHPTest(){
        val pokemonOne = Pokemon("squirtle", "Electric", 60)
        pokemonOne.decreaseHP(20)
        pokemonOne.resetHP()
        assertEquals(pokemonOne.maxHpStat, pokemonOne.currentHpStat)
        assertEquals(false, pokemonOne.isDead)
    }

    //test the useMove Method
    @Test
    fun useMoveTest(){
        val pokemonOne = Pokemon("squirtle", "Electric", 60)
        pokemonOne.useMove(0)
        assertEquals(pokemonOne.currentMoves[0].maxPP-1,  pokemonOne.currentMoves[0].currentPP)
    }

    //test the addExperienceToPokemon Method
    @Test
    fun addExperienceToPokemonTest(){
        val pokemonOne = Pokemon("squirtle", "Electric", 60)
        val exp =   (0.3 * pokemonOne.baseExperienceReward * pokemonOne.level * 1).toInt();
        assertEquals(exp, pokemonOne.addExperienceToPokemon(pokemonOne, false));
    }


    //test canPokemonLevelUp Method
    @Test
    fun canPokemonLevelUpTest(){
        val pokemonOne = Pokemon("squirtle", "Electric", 50)
        assertEquals(false, pokemonOne.canPokemonLevelUp())
    }


}