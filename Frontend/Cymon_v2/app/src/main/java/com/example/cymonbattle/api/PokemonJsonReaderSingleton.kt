package com.example.cymonbattle.api

import android.content.Context
import android.graphics.drawable.Drawable
import com.example.cymonbattle.pokemon.Move
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import org.json.JSONObject
import java.io.FileNotFoundException
import java.net.URL
import kotlin.collections.*

class PokemonJsonReaderSingleton {

    val URL: URL = URL("https://pokeapi.co/api/v2/")
    @Volatile
    private var INSTANCE: PokemonJsonReaderSingleton? = null
    private lateinit var _pokemonSpecies: List<String>
    private lateinit var _pokemonTypes: List<String>
    private val _gson = Gson()
    private lateinit var _drawables: Map<String, Drawable>

    // Properties
    val pokemonSpecies: List<String> get() = _pokemonSpecies
    val pokemonTypes: List<String> get() = _pokemonTypes


    /**
     * This method is used to get the Instance of the class.
     * @return PokemonJsonReaderSingleton : Singleton Class PokemonJsonReaderSingleton
     */
    fun getInstance(context: Context): PokemonJsonReaderSingleton {
        return if (INSTANCE == null) {
            // Get Pokemon Types
            _pokemonTypes = this.createStringListOfJson(context, "types.json")
            // Get Pokemon species
            _pokemonSpecies = this.createStringListOfJson(context, "species.json")
            // Get Starter Pokemon Drawables
            _drawables = mapOf(Pair("charmander", this.getPokemonImage(context, "4.png")!!),
                Pair("bulbasaur", this.getPokemonImage(context, "1grey.png")!!),
                Pair("squirtle", this.getPokemonImage(context, "7.png")!!))

            // Set INSTANCE to this
            this.INSTANCE = this
            this.INSTANCE!!
        } else {
            INSTANCE!!
        }
    }

    /**
     * This method returns one of the starter pokemon drawables.
     *
     * @param pokeName Name of the pokemon
     * @return Drawable image of the pokemon
     */
    fun getStarterImage(pokeName: String): Drawable {
        return this._drawables[pokeName.lowercase()]!!
    }

    /**
     * This method returns an Drawable of an image.
     * The file is located in assets/pokemon/sprites.
     *
     * @param context Context in which to get the Drawable
     * @param imgName Name of the file of the image
     * @return Drawable? : Image if it exists
     */
    fun getPokemonImage(context: Context, imgName: String): Drawable? {
        return Drawable.createFromStream(context.assets.open("pokemon/sprites/$imgName"), null)
    }

    /**
     * This method creates a List of LinkedTreeMap which contains a list of moves, their move name and level requirement
     *
     * @param context Context in which to read the file.
     * @param jsonFile Name of the file to read
     * @return List<LinkedTreeMap<String,String>> : List of moves containing a name and level requirement
     * @throws FileNotFoundException : Thrown when file name is incorrect.
     */
    @Throws(FileNotFoundException::class)
    fun getMovesList(context: Context, jsonFile: String): List<LinkedTreeMap<String, String>> {
        val data = context.assets.open("move_lists/${jsonFile}").bufferedReader().use {
            it.readText()
        }
        return _gson.fromJson<List<LinkedTreeMap<String, String>>>(data, List::class.java)
    }

    /**
     * This method creates a move from a json file.
     * The file should be located in assets/moves.
     *
     * @param context Context in which to read the file.
     * @param jsonFile Name of the file to read
     * @return Move : Move of a pokemon
     * @throws FileNotFoundException : Thrown when file name is incorrect.
     */
    @Throws(FileNotFoundException::class)
    fun getMoves(context: Context, jsonFile: String): Move {
        val data = context.assets.open("moves/${jsonFile}").bufferedReader().use {
            it.readText()
        }
        return _gson.fromJson(data, Move::class.java)
    }

    /**
     * This method creates a Pokemon from the JSON file given.
     * The file should be located in assets/pokemon
     *
     * @param context Context in which to read the file.
     * @param jsonFile Name of the file to read
     * @return JSONObject : JSONObject with data
     * @throws FileNotFoundException : Thrown when file name is incorrect.
     */
    @Throws(FileNotFoundException::class)
    fun getPokemon(context: Context, jsonFile: String): JSONObject {
        val data = context.assets.open("pokemon/${jsonFile}").bufferedReader().use {
            it.readText()
        }

        return JSONObject(data)
    }

    /**
     * This method is a general method to read any JSON file which consists of a list of strings.
     * The throw here should be ignored since it is only called within the instance and is exception proof.
     *
     * @param context Context in which to read the file.
     * @param jsonFile Name of the file to read
     * @return List<String> : representing a list of String of the file.
     * @throws FileNotFoundException : Thrown when file name is incorrect.
     */
    @Throws(FileNotFoundException::class)
    private fun createStringListOfJson(context: Context, jsonFile: String): List<String> {
        val data = context.assets.open(jsonFile).bufferedReader().use {
            it.readText()
        }
        val listPokemonTypesToken = object : TypeToken<List<String>>() {}.type
        return _gson.fromJson(data, listPokemonTypesToken)
    }

    /**
     * This method creates a Map of type-relations of a type X
     * and compares their effectivity against the type X.
     * The file should be located in assets/type_relations
     *
     * @param context Context in which to read the file.
     * @param jsonFile Name of the file to read
     * @return Map<String,String> : the different relations of a type to other types
     * @throws FileNotFoundException : Thrown when file name is incorrect.
     */
    @Throws(FileNotFoundException::class)
    fun getTypeRelations(context: Context, jsonFile: String): Map<String, String>? {
        val data = context.assets.open("type_relations/${jsonFile}").bufferedReader().use {
            it.readText()
        }
        return _gson.fromJson<Map<String, String>>(data, Map::class.java)
    }
}
