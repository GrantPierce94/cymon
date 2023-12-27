package com.example.cymonbattle.teamList

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.cymonbattle.Adapter.PokemonListAdapter
import com.example.cymonbattle.MainMenuFragment
import com.example.cymonbattle.databinding.FragmentChangeTeamBinding
import com.example.cymonbattle.trainer.Trainer


class ChangeTeamFragment  : Fragment(), Listener {
    private lateinit var RecyclerViewTeam : RecyclerView
    private lateinit var RecyclerViewCollection : RecyclerView

    private var _binding: FragmentChangeTeamBinding? = null
    private val binding: FragmentChangeTeamBinding
        get() = this._binding!!

    /**
     * The fragment argument representing the section number for this
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        _binding = FragmentChangeTeamBinding.inflate(inflater, container, false)
        return binding.root
    }

    /**
     * This function is called when the view is created
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val userTrainer =
            requireArguments().getSerializable(MainMenuFragment.TRAINER_STRING_ARG) as Trainer
        initTeamRecyclerView(userTrainer);
        initCollectionRecyclerView(userTrainer);
    }

    /**
     * Initialize the RecyclerView for the team
     * @param userTrainer the trainer object
     */
    private fun initTeamRecyclerView(userTrainer: Trainer) {
        RecyclerViewTeam  = binding.pokemonTeam
        val numberOfColumns = 3
        RecyclerViewTeam.layoutManager = GridLayoutManager(context, numberOfColumns)
        val teamAdapter = PokemonListAdapter(userTrainer.pokemonTeam, this)
        RecyclerViewTeam.adapter = teamAdapter

        binding.tvEmptyListTop.setOnDragListener(teamAdapter.getDragInstance());
        binding.pokemonTeam.setOnDragListener(teamAdapter.getDragInstance());
    }

    /**
     * Initialize the collection recycler view
     * @param userTrainer the trainer object
     */
    private fun initCollectionRecyclerView(userTrainer: Trainer) {
        RecyclerViewCollection = binding.pokemonCollection
        val numberOfColumns = 3
        RecyclerViewCollection.layoutManager = GridLayoutManager(context, numberOfColumns)
        val collectionAdapter = PokemonListAdapter(userTrainer.collection, this)
        RecyclerViewCollection.adapter = collectionAdapter

        binding.tvEmptyListBottom.setOnDragListener(collectionAdapter.getDragInstance());
        binding.pokemonCollection.setOnDragListener(collectionAdapter.getDragInstance());
    }

    /**
     * This method is called when a pokemom team is empty
     * @param visibility: the visibility of the pokemon
     */
    override fun setEmptyListTop(visibility: Boolean) {
        binding.tvEmptyListTop.visibility = if (visibility) View.VISIBLE else View.GONE
        RecyclerViewTeam.visibility = if (visibility) View.GONE else View.VISIBLE
    }

    /**
     * This method is called when a pokemom Collection is empty
     * @param visibility: the visibility of the pokemon
     */
    override fun setEmptyListBottom(visibility: Boolean) {
        binding.tvEmptyListBottom.visibility = if (visibility) View.VISIBLE else View.GONE
        RecyclerViewCollection.visibility = if (visibility) View.GONE else View.VISIBLE
    }
}