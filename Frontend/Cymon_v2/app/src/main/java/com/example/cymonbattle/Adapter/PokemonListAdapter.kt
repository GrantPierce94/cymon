package com.example.cymonbattle.Adapter

import android.content.ClipData
import android.os.Build
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.DragShadowBuilder
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.cymonbattle.R
import com.example.cymonbattle.pokemon.Pokemon
import com.example.cymonbattle.teamList.DragListener
import com.example.cymonbattle.teamList.Listener


class PokemonListAdapter(private val pokemonList: MutableList<Pokemon>, private val listener: Listener) : RecyclerView.Adapter<PokemonListAdapter.PokemonViewHolder>(), View.OnTouchListener{

    class PokemonViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val pokemonImage: ImageView = itemView.findViewById(R.id.pokemonImage)
        val pokemonName: TextView = itemView.findViewById(R.id.pokemonName)
    }

    /**
     * This function is called when the view is created
     * @param parent
     * @param viewType
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PokemonViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.pokemon_list_item, parent, false)
        return PokemonViewHolder(itemView)
    }

    /**
     * On bind view holder
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: PokemonViewHolder, position: Int) {
        val pokemon = pokemonList[position]
        holder.pokemonImage.setImageBitmap(pokemon.FRONT_IMAGE)
        holder.pokemonName.text = pokemon.NICK_NAME
        holder.itemView.tag = position
        holder.itemView.setOnTouchListener(this)
        holder.itemView.setOnDragListener(DragListener(listener))
    }

    /**
     * get the size of the list
     */
    override fun getItemCount() = pokemonList.size


    /**
     * This function is called when the view is touched
     * @param v
     * @param event
     */
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                val data = ClipData.newPlainText("", "")
                val shadowBuilder = DragShadowBuilder(v)
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    v.startDragAndDrop(data, shadowBuilder, v, 0)
                } else {
                    v.startDrag(data, shadowBuilder, v, 0)
                }
                return true
            }
        }
        return false
    }

    /**
     * update the list
     * @param list
     */
    fun updateList(list: MutableList<Pokemon>) {
        pokemonList.clear()
        pokemonList.addAll(list)
        notifyDataSetChanged()
    }

    /**
     * get the list
     * @return list
     */
    fun getList(): List<Pokemon> {
        return pokemonList
    }

    /**
     * get DragListener
     * @return DragListener
     */
    fun getDragInstance(): DragListener {
        return DragListener(listener)
    }

}

