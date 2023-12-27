package com.example.cymonbattle.pokemon

import org.json.JSONObject

data class Move(
    val name: String,
    val level: Int,
    val category: String,
    val damageClass: String,
    val target: String,
    val type: String,
    val accuracy: Int?,
    val heal: Int,
    val power: Int?,
    private var pp: Int,
    val aliment: String,
    val alimentChance: Int
) : java.io.Serializable{
//    private var _isUsable: Boolean = true
    var isUsable: Boolean = true
        internal set

    val maxPP: Int = pp
    var currentPP: Int = pp
        internal set(value) {
            if (value <= 0) {
                isUsable = false
            }
            field = value
        }
    // Make Move unusable if pp is 0.
    init {
        if(pp <= 0){
            isUsable = false
        }
    }


    /**
     * This method will reset the currentPP to maxPP
     * and set isUsable to true.
     */
    fun resetPP() {
        currentPP = maxPP
    }
}

/**
 * This method creates and returns a Move object.
 *
 * @param moveJson : JSONObject with the move data
 * @param moveLevel : Level requirement of move
 */
fun createMove(moveJson: JSONObject, moveLevel: Int): Move {
    return Move(
        moveJson.getString("name"),
        moveLevel,
        moveJson.getString("category"),
        moveJson.getString("damage_class"),
        moveJson.getString("target"),
        moveJson.getString("type"),
        moveJson.getInt("accuracy"),
        moveJson.getInt("healing"),
        moveJson.getInt("power"),
        moveJson.getInt("maxPP"),
        moveJson.getString("ailment"),
        moveJson.getInt("ailment_chance")
    )
}
