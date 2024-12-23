package pl.matiu.pokebdemobile.domain.sharedPrefs

import android.content.Context
import android.content.SharedPreferences
import pl.matiu.pokebdemobile.domain.entity.PokemonModel

class TodayPokemonSharedPrefs {
    companion object {
        private const val PREFERENCES_KEY = "PREFERENCES"
        private const val TODAY_POKEMON_KEY = "TODAY_POKEMON"
    }

    fun getTodayPokemon(context: Context): String {
        return context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE).getString(
            TODAY_POKEMON_KEY, "") ?: ""
    }

    fun setTodayPokemon(todayPokemonName: String, context: Context) {

        val sharedPreferences: SharedPreferences =
            context.getSharedPreferences(PREFERENCES_KEY, Context.MODE_PRIVATE)

        val editor = sharedPreferences.edit()
        editor.putString(TODAY_POKEMON_KEY, todayPokemonName)
        editor.apply()
    }
}