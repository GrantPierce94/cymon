package com.example.cymonbattle

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.cymonbattle.pokemon.Pokemon
import com.example.cymonbattle.trainer.Trainer
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class TrainerTest {
    var second : Pokemon? = null
    var third : Pokemon? = null
    var fourth : Pokemon? = null
    var sixth : Pokemon? = null
    var ninth : Pokemon? = null
    var opponent : Pokemon? = null
    var eleventh : Pokemon? = null
    var twelfth : Pokemon? = null

    @Before
    fun createPokemon(){
        second =  Pokemon("charizard", "kiki", 5)
        third =  Pokemon("bulbasaur", "allo", 5)
        fourth =  Pokemon("squirtle", "ha", 5)
        sixth =  Pokemon("nidoqueen", "ha", 5)
        ninth =  Pokemon("pikachu", "hassa", 5)
        opponent =  Pokemon("mewtwo", "hass", 5)
        eleventh =  Pokemon("dewgong", "hass", 5)
        twelfth =  Pokemon("golbat", "hass", 5)
    }

    @Test
    fun testFetchAllImagesTeamCollection() {
        val trainer = Trainer("david")
        trainer.addPokemonToTeamOrCollection(second!!)
        trainer.addPokemonToTeamOrCollection(third!!)
        trainer.addPokemonToTeamOrCollection(eleventh!!)
        trainer.addPokemonToTeamOrCollection(twelfth!!)
        // Test Team
        trainer.pokemonTeam.forEach {
            assertNotNull(it.BACK_IMAGE)
            assertNotNull(it.FRONT_IMAGE)
        }
        // Test collection
        trainer.collection.forEach {
            assertNotNull(it.BACK_IMAGE)
            assertNotNull(it.FRONT_IMAGE)
        }
    }

    @Test
    fun testActivePokemon(){
        val trainer = Trainer("david")
        trainer.addPokemonToTeamOrCollection(second!!)
        trainer.changeActivePokemon(0)
        assertEquals(second, trainer.activePokemon)
    }

    @Test
    fun testChangeActivePokemon(){
        val trainer = Trainer("david")
        trainer.addPokemonToTeamOrCollection(second!!)
        trainer.addPokemonToTeamOrCollection(third!!)
        trainer.changeActivePokemon(1)
        assertEquals(third,trainer.activePokemon)
    }

    @Test
    fun testPokemonAddToTeam(){
        val trainer = Trainer("david")
        trainer.addPokemonToTeamOrCollection(second!!)
        trainer.addPokemonToTeamOrCollection(third!!)
        trainer.addPokemonToTeamOrCollection(eleventh!!)
        trainer.addPokemonToTeamOrCollection(twelfth!!)
        assertEquals(4,trainer.pokemonTeam.size)
    }

    @Test
    fun testPokemonAddToCollection(){
        val trainer = Trainer("david")
        trainer.addPokemonToTeamOrCollection(second!!)
        trainer.addPokemonToTeamOrCollection(third!!)
        trainer.addPokemonToTeamOrCollection(eleventh!!)
        trainer.addPokemonToTeamOrCollection(twelfth!!)
        trainer.addPokemonToTeamOrCollection(second!!)
        trainer.addPokemonToTeamOrCollection(third!!)
        trainer.addPokemonToTeamOrCollection(eleventh!!)
        trainer.addPokemonToTeamOrCollection(twelfth!!)
        assertEquals(8-6,trainer.collection.size)
    }

    @Test
    fun testHealAllPokemon(){
        val trainer = Trainer("david")
        trainer.addPokemonToTeamOrCollection(second!!)
        trainer.addPokemonToTeamOrCollection(third!!)
        trainer.addPokemonToTeamOrCollection(eleventh!!)
        trainer.addPokemonToTeamOrCollection(twelfth!!)
        MainActivity.TYPE_EFFECTIVENESS = MainActivity.createTypeRelations()
        val dmg = opponent!!.attack(trainer.pokemonTeam[0],opponent!!.currentMoves[0],MainActivity.TYPE_EFFECTIVENESS )
        trainer.pokemonTeam.forEach {
            it.decreaseHP(dmg)
        }
        assertEquals(trainer.pokemonTeam[0].maxHpStat-dmg,trainer.pokemonTeam[0].currentHpStat)
        assertEquals(trainer.pokemonTeam[1].maxHpStat-dmg,trainer.pokemonTeam[1].currentHpStat)
        assertEquals(trainer.pokemonTeam[2].maxHpStat-dmg,trainer.pokemonTeam[2].currentHpStat)

        trainer.healAllPokemon()
        assertEquals(trainer.pokemonTeam[0].maxHpStat,trainer.pokemonTeam[0].currentHpStat)
        assertEquals(trainer.pokemonTeam[1].maxHpStat,trainer.pokemonTeam[1].currentHpStat)
        assertEquals(trainer.pokemonTeam[2].maxHpStat,trainer.pokemonTeam[2].currentHpStat)

    }

    @Test
    fun resetPPOfPokemon(){
        val trainer = Trainer("david")
        trainer.addPokemonToTeamOrCollection(second!!)
        trainer.addPokemonToTeamOrCollection(third!!)
        trainer.addPokemonToTeamOrCollection(eleventh!!)
        trainer.addPokemonToTeamOrCollection(twelfth!!)
        // Decrease PP
        trainer.pokemonTeam[0].currentMoves[0].currentPP -= 6
        // Check if PP is decreased
        assertEquals(trainer.pokemonTeam[0].currentMoves[0].maxPP-6,trainer.pokemonTeam[0].currentMoves[0].currentPP)
        trainer.resetPPOfPokemon()
        assertEquals(trainer.pokemonTeam[0].currentMoves[0].maxPP,trainer.pokemonTeam[0].currentMoves[0].currentPP)

    }




}