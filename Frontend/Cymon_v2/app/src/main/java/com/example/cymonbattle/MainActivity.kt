package com.example.cymonbattle

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.cymonbattle.api.PokemonAPI
import com.example.cymonbattle.api.PokemonAPI.Companion.getTypeEffectiveness
import com.google.gson.internal.LinkedTreeMap
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import kotlin.collections.*

class MainActivity : AppCompatActivity() {
    companion object {
        lateinit var TYPE_EFFECTIVENESS: Map<String, String>
        /**
         * This method returns a JsonArray of all the types in gen 1
         */
        private suspend fun getAllTypeGen1(): JSONArray {
            return PokemonAPI.getGenerationTypes("1")!!
        }

        /**
         * This method creates the type relations
         * @return Map<String,String> where : typeToCheckEffectiveness-typeAgainst -> effectiveness
         */
        fun createTypeRelations(): Map<String, String> {
            val typeRelations: MutableMap<String,String> = mutableMapOf()
            lateinit var allTypes: JSONArray
            lateinit var typeEffectivness: LinkedTreeMap<String, String>
            CoroutineScope(Dispatchers.Main).launch {
                // Get all types for gen1
                val job = launch {
                    allTypes = getAllTypeGen1()
                }
                job.join()
            }.invokeOnCompletion {
                CoroutineScope(Dispatchers.Main).launch  {
                    // Loop through all Types
                    (0 until allTypes.length()).forEach {
                        val job = launch {
                            // Get Type Effectiveness against other types
                            typeEffectivness = getTypeEffectiveness(allTypes[it].toString())!!
                        }
                        job.join()
                        // Create Type Relations
                        typeEffectivness.forEach{ (k, v) ->
                            typeRelations["${allTypes[it].toString().lowercase()}-${k.lowercase()}"] = v
                        }
                    }
                }
            }
            return typeRelations
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TYPE_EFFECTIVENESS = createTypeRelations()
        setContentView(R.layout.activity_main)
    }
}