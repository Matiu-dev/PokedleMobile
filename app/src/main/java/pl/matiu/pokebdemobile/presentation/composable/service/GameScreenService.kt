package pl.matiu.pokebdemobile.presentation.composable.service

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.compose.ui.graphics.Color
import pl.matiu.pokebdemobile.domain.entity.PokemonModel
import pl.matiu.pokebdemobile.domain.pokemonNames
import pl.matiu.pokebdemobile.presentation.PokemonViewModel

class GameScreenService {
    fun checkGuessPokemonState(pokemonName: String,
                               todayPokemon: PokemonModel?,
                               listOfGuessedPokemon:List<PokemonModel>,
                               onGuessPokemonStateChange: (GuessPokemonState) -> Unit
    ) {
        when {
            !isPokemonExist(pokemonName) -> {
                onGuessPokemonStateChange(GuessPokemonState.POKEMON_NOT_EXIST)
            }

            isPokemonSelected(guessedPokemonList = listOfGuessedPokemon, pokemonName = pokemonName) -> {
                onGuessPokemonStateChange(GuessPokemonState.POKEMON_CHECKED)
            }

            pokemonName == todayPokemon?.name -> {
                onGuessPokemonStateChange(GuessPokemonState.SUCCESS)
            }

            else -> {
                onGuessPokemonStateChange(GuessPokemonState.FAILURE)
            }
        }
    }

    private fun isPokemonExist(pokemonName: String): Boolean {
        return pokemonNames.contains(pokemonName)
    }

    private fun isPokemonSelected(guessedPokemonList: List<PokemonModel>, pokemonName: String): Boolean {
        return guessedPokemonList.any { it.name == pokemonName }
    }

    fun handlePokemonGuessState(context: Context, pokemonName: String, pokemonViewModel: PokemonViewModel,
                                guessPokemonState: GuessPokemonState?, onEndGameChange: (Boolean) -> Unit) {
        when(guessPokemonState) {
            GuessPokemonState.SUCCESS -> {

                pokemonViewModel.getPokemonInfo(pokemonName = pokemonName, context = context)
                onEndGameChange(true)

                Toast.makeText(
                    context,
                    "Udało Ci się zgadnąć. Dzisiejszy pokemon to ${pokemonName}.",
                    Toast.LENGTH_SHORT
                ).show()

            }
            GuessPokemonState.FAILURE -> {

                pokemonViewModel.getPokemonInfo(pokemonName = pokemonName, context = context)

                Toast.makeText(
                    context,
                    "Nie trafiłeś tym razem. Spróbuj ponownie.",
                    Toast.LENGTH_SHORT
                ).show()
            }
            GuessPokemonState.POKEMON_NOT_EXIST -> {
                Toast.makeText(context, "Nie ma takiego pokemona.", Toast.LENGTH_SHORT)
                    .show()
            }
            GuessPokemonState.POKEMON_CHECKED -> {
                Toast.makeText(
                    context,
                    "Sprawdziłeś już tego pokemona.",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            null -> Log.d("PokemonModel", "nullem jestem")
        }
    }

    fun checkContains(typeList: List<String>, todayPokemon: PokemonModel?): Color {
        var containsAny = false
        var containsAll = true

        for (typeInGuessedPokemon in typeList) {
            if (typeInGuessedPokemon in todayPokemon?.typeList!!) {
                containsAny = true
            } else {
                containsAll = false
            }
        }

        return if (containsAll) {
            Color.Green
        } else {
            if (containsAny) {
                Color.Yellow
            } else {
                Color.Red
            }
        }
    }
}