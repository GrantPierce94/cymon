package com.example.cymonbattle

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.ProgressBar
import com.example.cymonbattle.databinding.FragmentChooseStarterPokemonBinding
import android.widget.Toast
import androidx.lifecycle.lifecycleScope
import androidx.navigation.findNavController
import com.example.cymonbattle.api.PokemonJsonReaderSingleton
import com.example.cymonbattle.pokemon.Pokemon
import com.example.cymonbattle.trainer.Trainer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class ChooseStarterPokemonFragment : Fragment() {
    private var _binding: FragmentChooseStarterPokemonBinding? = null
    private val binding: FragmentChooseStarterPokemonBinding
        get() = this._binding!!

    private var pokemonNickname: String? = ""
    private var trainerName: String = ""
    private var selectedPokemon: String? = null
    private lateinit var pokemonJsonReader: PokemonJsonReaderSingleton

    private lateinit var spinner: ProgressBar

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentChooseStarterPokemonBinding.inflate(inflater, container, false)
        return binding.root
    }

    @SuppressLint("LongLogTag")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        this.spinner = requireActivity().findViewById(R.id.progressBar1)
        // Add Trainer Name Validator
        trainerNickNameValidator()
        // Check for PokemonNickName
        getSelectedPokemonName()

        this.pokemonJsonReader = PokemonJsonReaderSingleton().getInstance(requireContext())

        // Show Starter pokemon images
        binding.pokemonOneImg.setImageDrawable(this.pokemonJsonReader.getStarterImage("squirtle"))
        binding.pokemonTwoImg.setImageDrawable(this.pokemonJsonReader.getStarterImage("bulbasaur"))
        binding.pokemonThreeImg.setImageDrawable(this.pokemonJsonReader.getStarterImage("charmander"))

        // Notify user of the chosen pokemon
        binding.pokemonOneImg.setOnClickListener{
            selectedPokemon = binding.pokemonOneImg.tag.toString()
            setPokemonBackground((it as ImageView))
        }

        binding.pokemonTwoImg.setOnClickListener {
            selectedPokemon = binding.pokemonTwoImg.tag.toString()
            setPokemonBackground((it as ImageView))
        }

        binding.pokemonThreeImg.setOnClickListener {
            selectedPokemon = binding.pokemonThreeImg.tag.toString()
            setPokemonBackground((it as ImageView))
        }

        binding.createTrainerBtn.setOnClickListener {
            if (selectedPokemon.equals(null)) {
                Toast.makeText(it.context, "Please choose your pokemon.", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            // Check Trainer Name is Provided
            if (trainerName == "") {
                Toast.makeText(it.context, "Please enter a trainer name.", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }
            var starterPokemon: Pokemon? = null
            lifecycleScope.launch(Dispatchers.Main){
                spinner.visibility = View.VISIBLE
                // Create Pokemon
                starterPokemon = Pokemon(selectedPokemon!!, pokemonNickname!!, 5)
            }.invokeOnCompletion {
                // Create Trainer
                val trainer = Trainer(trainerName)
                // Add the two pokemon to the trainer
                trainer.addPokemonToTeamOrCollection(starterPokemon!!)
                // Make Pokemon activePokemon
                trainer.changeActivePokemon(0)
                // Switch to main menu passing trainer
                val action = ChooseStarterPokemonFragmentDirections.actionChooseStarterPokemonFragmentToFragmentMainMenu(trainer)
                view.findNavController().navigate(action)
            }
        }
    }

    override fun onStop() {
        this.spinner.visibility = View.GONE
        super.onStop()
    }

    private fun setPokemonBackground(pokemon: ImageView) {
        pokemon.setColorFilter(androidx.appcompat.R.color.abc_tint_default)
        if (pokemon != binding.pokemonOneImg) {
            binding.pokemonOneImg.colorFilter = null
        }
        if (pokemon != binding.pokemonTwoImg) {
            binding.pokemonTwoImg.colorFilter = null

        }
        if (pokemon != binding.pokemonThreeImg) {
            binding.pokemonThreeImg.colorFilter = null
        }
    }


    private fun trainerNickNameValidator() {
        binding.trainerNameEditTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                binding.createTrainerBtn.isEnabled = false
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! < 5 || p0.isEmpty()) {
                    binding.trainerNameEditTxt.error = "Trainer Name of Length 5 is Required."
                } else {
                    binding.createTrainerBtn.isEnabled = true
                    trainerName = p0.toString()
                }
            }

            override fun afterTextChanged(p0: Editable?) {}
        })
    }


    private fun getSelectedPokemonName() {
        binding.nicknameEditTxt.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                if (p0?.length!! > 7) {
                    Toast.makeText(context, "Nickname of 7 letters is recommend", Toast.LENGTH_SHORT).show()
                } else {
                    pokemonNickname = p0.toString()
                }
            }
            override fun afterTextChanged(p0: Editable?) {}
        })
    }
}
