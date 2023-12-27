package com.example.cymonbattle.trainer

import com.example.cymonbattle.pokemon.Pokemon

class Trainer(val name: String): java.io.Serializable {
    fun fetchAllImages(){
        pokemonTeam.forEach {
            it.fetchImages()
        }
        collection.forEach {
            it.fetchImages()
        }
    }

    // Pokemon
    var pokemonTeam: MutableList<Pokemon> = mutableListOf()
        internal set
    var collection: MutableList<Pokemon> = mutableListOf()
        internal set

    // Active pokemon
    var activePokemon: Pokemon? = null
        internal set

    /**
     * This method changes the Active Pokemon
     */
    fun changeActivePokemon(index: Int) {
        activePokemon = this.pokemonTeam[index]
    }


    /**
     * This method adds a pokemon to the trainer's team.
     * If the team is full, the pokemon is added to the collection.
     *
     * @param pokemon the pokemon to add
     */
    fun addPokemonToTeamOrCollection(pokemon: Pokemon) {
        if(pokemonTeam.size < 6) {
            pokemonTeam.add(pokemon)
        }else{
            collection.add(pokemon)
        }
    }

    /**
     * This method reset the trainer's pokemon's HP to their Max HP.
     */
    fun healAllPokemon(){
        for (pokemon in pokemonTeam){
            pokemon.resetHP()
        }
    }

    /**
     * This method reset all the trainer's pokemon's moves PP to their Max PP.
     */
    fun resetPPOfPokemon(){
        for (pokemon in pokemonTeam){
            pokemon.resetAllMovesPP()
        }
    }

}