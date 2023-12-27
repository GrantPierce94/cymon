package com.example.cymonbattle.api

import PokeApiEndpoint
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.google.gson.Gson
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.reflect.TypeToken
import kotlinx.coroutines.*
import org.json.JSONArray
import org.json.JSONObject
import simplifyMove
import simplifyMoves
import simplifyPokemon
import simplifyTypeRelations
import simplifyTypes
import java.io.BufferedReader
import java.net.HttpURLConnection
import java.net.URL
import java.util.*

/**
 * API to fetch data from https://pokeapi.co/api/v2
 */
@SuppressLint("LongLogTag")
class PokemonAPI {
    companion object {

        /**
         * This method creates a JSONObject with the data of a pokemon fetched from API
         * It Simplifies the response
         *
         * @param pokemonNameOrId Name of the file to read
         * @return JSONObject : JSONObject with data
         */

        suspend fun getPokemon(pokemonName: String): JSONObject? {
            return try {
                var responsePokemonString: String?
                withContext(Dispatchers.IO) {
                    val url = URL("${PokeApiEndpoint.POKEMON.url}/${pokemonName.lowercase(Locale.ROOT)}")
                    responsePokemonString = fetchDataFromAPI(url)
                }
                if (responsePokemonString == null) { throw Exception("Could not get pokemon from api.")}
                // Get Pokemon stats
                responsePokemonString = simplifyPokemon(responsePokemonString!!)
                //Returns
                JSONObject(responsePokemonString!!)
            } catch (e: Exception) {
                Log.e("Exception thrown in getPokemon function API", e.stackTraceToString())
                null
            }
        }

        /**
         * This method creates a JSONObject with the data of a pokemon fetched from API
         * It Simplifies the response and get all the moves the pokemon can learn.
         *
         * @param pokemonName Name of the pokemon
         * @return JSONObject : JSONObject with all the moves
         */
        suspend fun getAllPokemonMovesJson(pokemonName: String): MutableList<MutableMap<String, String>>? {
            return try {
                var responseAllMovesPokemonString: String?
                withContext(Dispatchers.IO) {
                    val url = URL("${PokeApiEndpoint.POKEMON.url}/${pokemonName.lowercase(Locale.ROOT)}")
                    responseAllMovesPokemonString = fetchDataFromAPI(url)
                }
                if (responseAllMovesPokemonString == null) { throw Exception("Could not get pokemon from api.")}
                // Get all pokemon moves
                val allMovesString = simplifyMoves(responseAllMovesPokemonString!!)

                val moveToken = object: TypeToken<MutableList<MutableMap<String, String>>>() {}.type
                //Returns
                Gson().fromJson(allMovesString, moveToken)
            } catch (e: Exception) {
                Log.e("Exception thrown in getAllPokemonMoves function API", e.stackTraceToString())
                null
            }
        }

        /**
         * This method gets all types a generation can have from the API.
         *
         * @param genNumber: Number of generations
         * @return JSONArray? : Array of all types
         */
        suspend fun getGenerationTypes(genNumber: String): JSONArray? {
            if ( genNumber.toIntOrNull() == null) { throw RuntimeException("Generation Number has to be a number string.")}
            return try {
                var allTypes: String?
                withContext(Dispatchers.IO) {
                    val url = URL("${PokeApiEndpoint.GENERATION.url}/${genNumber}")
                    allTypes = fetchDataFromAPI(url)
                }
                if (allTypes == null) { throw Exception("Could not get pokemon from api.")}
                // Get all pokemon moves
                allTypes = simplifyTypes(allTypes!!)
                //Returns
                JSONArray(allTypes!!)
            } catch (e: Exception) {
                Log.e("Exception thrown in getGenerationTypes function API", e.stackTraceToString())
                null
            }
        }

        /**
         * This method gets all type effectiveness of a given type
         *
         * @param type : Type to check the effectiveness of against other types
         * @return LinkedTreeMap<String,String> : Key-Value pair of type and effectivity
         */
        suspend fun getTypeEffectiveness(type: String): LinkedTreeMap<String,String>? {
            return try {
                var typeAPI: String?
                withContext(Dispatchers.IO) {
                    val url = URL("${PokeApiEndpoint.TYPE.url}/$type")
                    typeAPI = fetchDataFromAPI(url)
                }
                if (typeAPI == null) { throw Exception("Could not get pokemon from api.")}
                // Get all types
                typeAPI = simplifyTypeRelations(typeAPI!!)
                //Returns
                Gson().fromJson<LinkedTreeMap<String,String>>(typeAPI ,LinkedTreeMap::class.java)
            } catch (e: Exception) {
                Log.e("Exception thrown in getGenerationTypes function API", e.stackTraceToString())
                null
            }
        }

        /**
         * This method returns an Drawable of an image.

         * @param url URL of the image file
         * @return Drawable? : Drawable if it exists
         */
        suspend fun getPokemonImage(url: String): Bitmap? {
            var image: Bitmap?
            return try{
                withContext(Dispatchers.IO) {
//                    image = Drawable.createFromStream(URL(url).content as InputStream, "name")!!
                 image = BitmapFactory.decodeStream(URL(url).openStream())
                }

                // Returns
                image
            }catch (e: Exception){
                Log.e("Failed to get Image", "$url failed getting image from api. Check getPokemonImage function")
                null
            }
        }

        /**
         * This method fetch a move from the api.
         *
         * @param moveName Name of the move
         * @return JSONObject? : JSONObject of the move or null
         */
        suspend fun getMoveJson(moveName: String): JSONObject? {
            return try {
                var responseMoveString: String?
                withContext(Dispatchers.IO) {
                    val url = URL("${PokeApiEndpoint.MOVE.url}/${moveName.lowercase(Locale.ROOT)}")
                    responseMoveString = fetchDataFromAPI(url)
                }
                if (responseMoveString == null) { throw Exception("Could not get pokemon move from api.")}
                responseMoveString = simplifyMove(responseMoveString!!)
                // Returns
                JSONObject(responseMoveString!!)
            } catch (e: Exception) {
                Log.e("Exception in getMoveJSON", e.stackTraceToString())
                null
            }
        }

        /**
         * This method Abstracts the creation of a JSONObject.
         * Accepts a URL and opens a connection to fetch the data from the api with a GET.
         * Will send null if it is not HTTP_OK
         *
         * @param url : URL of the data to GET
         * @return String? : Content from the api or null if failed
         */
        private fun fetchDataFromAPI(url: URL): String?{
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connect()
            var content = ""
            if (connection.responseCode == HttpURLConnection.HTTP_OK) {
                val inStream = connection.inputStream
                val buffer = BufferedReader(inStream.reader())
                // Get content
                buffer.use { buf -> content = buf.readText() }
                // Close stream
                inStream.close()
            }
            // Finish with network
            connection.disconnect()
            // If response is not OK return null
            if(connection.responseCode != HttpURLConnection.HTTP_OK){ return null }
            // Parse content to jsonObject
            return content
        }
    }
}


