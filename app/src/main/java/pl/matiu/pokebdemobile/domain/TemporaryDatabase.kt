package pl.matiu.pokebdemobile.domain

import androidx.lifecycle.viewmodel.compose.viewModel
import org.koin.java.KoinJavaComponent.inject
import pl.matiu.pokebdemobile.data.repository.PokemonRepositoryImpl
import pl.matiu.pokebdemobile.presentation.PokemonViewModel
import kotlin.random.Random


val pokemonNames = listOf(
    "bulbasaur", "ivysaur", "venusaur",
    "charmander", "charmeleon", "charizard",
    "squirtle", "wartortle", "blastoise",
    "caterpie", "metapod", "butterfree",
    "weedle", "kakuna", "beedrill",
    "pidgey", "pidgeotto", "pidgeot",
    "rattata", "raticate", "spearow",
    "fearow", "ekans", "arbok",
    "pikachu", "raichu", "sandshrew",
    "sandslash", "nidoran♀", "nidorina",
    "nidoqueen", "nidoran♂", "nidorino",
    "nidoking", "clefairy", "clefable",
    "vulpix", "ninetales", "jigglypuff",
    "wigglytuff", "zubat", "golbat",
    "oddish", "gloom", "vileplume",
    "paras", "parasect", "venonat",
    "venomoth", "diglett", "dugtrio",
    "meowth", "persian", "psyduck",
    "golduck", "mankey", "primeape",
    "growlithe", "arcanine", "poliwag",
    "poliwhirl", "poliwrath", "abra",
    "kadabra", "alakazam", "machop",
    "machoke", "machamp", "bellsprout",
    "weepinbell", "victreebel", "tentacool",
    "tentacruel", "geodude", "graveler",
    "golem", "ponyta", "rapidash",
    "slowpoke", "slowbro", "magnemite",
    "magneton", "farfetch'd", "doduo",
    "dodrio", "seel", "dewgong",
    "grimer", "muk", "shellder",
    "cloyster", "gastly", "haunter",
    "gengar", "onix", "drowzee",
    "hypno", "krabby", "kingler",
    "exeggcute", "exeggutor", "cubone",
    "marowak", "hitmonlee", "hitmonchan",
    "lickitung", "koffing", "weezing",
    "rhyhorn", "rhydon", "chansey",
    "tangela", "kangaskhan", "horsea",
    "seadra", "goldeen", "seaking",
    "staryu", "starmie", "mr. mime",
    "scyther", "jynx", "electabuzz",
    "magmar", "pinsir", "tauros",
    "magikarp", "gyarados", "lapras",
    "ditto", "eevee", "vaporeon",
    "jolteon", "flareon", "porygon",
    "omanyte", "omastar", "kabuto",
    "kabutops", "aerodactyl", "snorlax",
    "articuno", "zapdos", "moltres",
    "dratini", "dragonair", "dragonite",
    "mewtwo", "mew"
)

class TemporaryDatabase {

    companion object {
        lateinit var todayPokemon: PokemonModel
    }

    suspend fun generateRandomPokemon() {

        val pokemonRepository: PokemonRepositoryImpl by inject(PokemonRepositoryImpl::class.java)
        todayPokemon = pokemonRepository.getPokemonByName(name = pokemonNames[(Random.nextInt(0, pokemonNames.size))])

    }


}