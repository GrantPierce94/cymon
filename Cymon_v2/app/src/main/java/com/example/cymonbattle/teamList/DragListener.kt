package com.example.cymonbattle.teamList

import android.view.DragEvent
import android.view.View
import android.view.View.OnDragListener
import androidx.recyclerview.widget.RecyclerView
import com.example.cymonbattle.Adapter.PokemonListAdapter
import com.example.cymonbattle.R
import com.example.cymonbattle.pokemon.Pokemon


class DragListener internal constructor(private val listener: Listener) :
    OnDragListener {
    private var isDropped = false


    /**
     * This function is called when the view is dragged
     * @param v
     * @param event
     */
    override fun onDrag(v: View, event: DragEvent): Boolean {

        when (event.action) {
            DragEvent.ACTION_DROP -> {
                isDropped = true
                var positionTarget = -1
                val viewSource = event.localState as View
                val viewId = v.id
                val flItem: Int = R.id.linear_layout_item
                val tvEmptyListTop: Int = R.id.tvEmptyListTop
                val tvEmptyListBottom: Int = R.id.tvEmptyListBottom
                val rvTop: Int = R.id.pokemon_team
                val rvBottom: Int = R.id.pokemon_collection
                //get the position of the view
                when (viewId) {
                    flItem, tvEmptyListTop, tvEmptyListBottom, rvTop, rvBottom -> {
                        val target: RecyclerView
                        when (viewId) {
                            //get the root view of rvTop and rvBottom - here is team and collection of Pokemons
                            tvEmptyListTop, rvTop -> target =
                                v.rootView.findViewById<View>(rvTop) as RecyclerView
                            tvEmptyListBottom, rvBottom -> target =
                                v.rootView.findViewById<View>(rvBottom) as RecyclerView
                            else -> {
                                //get the target and positionTarget
                                target = v.parent as RecyclerView
                                positionTarget = v.tag as Int
                            }
                        }
                        val source = viewSource.parent as RecyclerView
                        val adapterSource = source.adapter as PokemonListAdapter
                        val positionSource = viewSource.tag as Int
                        val sourceId = source.id
                        val list: Pokemon = adapterSource.getList()[viewSource.tag as Int]

                        val listSource: MutableList<Pokemon> =
                            adapterSource.getList().toMutableList()
                        val indexTop: Int = listSource.size

                        listSource.removeAt(positionSource);
                        adapterSource.updateList(listSource);
                        adapterSource.notifyItemInserted(positionSource)

                        val adapterTarget = target.adapter as PokemonListAdapter
                        val customListTarget: MutableList<Pokemon> =
                            adapterTarget.getList().toMutableList()

                        checkToAddPokemon(
                            sourceId,
                            indexTop,
                            rvTop,
                            rvBottom,
                            customListTarget,
                            list,
                            positionTarget,
                            target,
                            listSource,
                            positionSource,
                            adapterSource,
                            adapterTarget
                        )

                        if (sourceId == rvBottom && adapterSource.itemCount < 1) {
                            listener.setEmptyListBottom(true)
                        }
                        if (viewId == tvEmptyListBottom) {
                            listener.setEmptyListBottom(false)
                        }
                        if (sourceId == rvTop && adapterSource.itemCount < 1) {
                            listener.setEmptyListTop(true)
                        }
                        if (viewId == tvEmptyListTop) {
                            listener.setEmptyListTop(false)
                        }
                    }
                }
            }
        }
        if (!isDropped && event.localState != null) {
            (event.localState as View).visibility = View.VISIBLE
        }
        return true
    }

    /***
     * Check if the pokemon can be added to the list
     * @param sourceId the id of the source
     * @param indexTop the index of the top list
     * @param rvTop the id of the top list
     * @param rvBottom the id of the bottom list
     * @param customListTarget the list of the target
     * @param list the pokemon to add
     * @param positionTarget the position of the target
     * @param target the target
     * @param listSource the list of the source
     * @param positionSource the position of the source
     * @param adapterSource the adapter of the source
     * @param adapterTarget the adapter of the target
     */
    private fun checkToAddPokemon(
        sourceId: Int,
        indexTop: Int,
        rvTop: Int,
        rvBottom: Int,
        customListTarget: MutableList<Pokemon>,
        list: Pokemon,
        positionTarget: Int,
        target: RecyclerView,
        listSource: MutableList<Pokemon>,
        positionSource: Int,
        adapterSource: PokemonListAdapter,
        adapterTarget: PokemonListAdapter
    ) {
        if (sourceId == rvTop && target.id == rvBottom && indexTop != 1) {
            checkNegativeTargetPosition(positionTarget, list, customListTarget)
        } else if (sourceId == rvTop && target.id == rvTop) {
            checkNegativeTargetPosition(positionTarget, list, customListTarget)
        } else if (sourceId == rvBottom && target.id == rvBottom) {
            checkNegativeTargetPosition(positionTarget, list, customListTarget)
        } else if (sourceId == rvBottom && target.id == rvTop && customListTarget.size != 6) {
            checkNegativeTargetPosition(positionTarget, list, customListTarget)

        } else {
            listSource.add(positionSource, list)
            adapterSource.updateList(listSource)
            adapterSource.notifyItemInserted(positionSource)
        }
        adapterTarget.updateList(customListTarget)
        adapterTarget.notifyItemInserted(positionSource)
    }

    /***
     * Check if the target position is negative
     * @param positionTarget the position of the target
     * @param list the pokemon to add
     * @param customListTarget the list of the target
     */
    private fun checkNegativeTargetPosition(
        positionTarget: Int,
        list: Pokemon,
        customListTarget: MutableList<Pokemon>
    ) {
        if (positionTarget >= 0) {
            customListTarget.add(positionTarget, list)
        } else {
            customListTarget.add(list)
        }
    }
}