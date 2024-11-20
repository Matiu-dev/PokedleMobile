package pl.matiu.pokebdemobile.domain


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
    "paras", "parases", "venonat",
    "venomoth", "diglett", "dugtrio",
    "meowth", "persian", "psyduck",
    "golduck", "machop", "machoke",
    "machamp", "bellsprout", "weepinbell",
    "victreebel", "tentacool", "tentacruel",
    "geodude", "graveler", "golem",
    "ponyta", "rapidash", "slowpoke",
    "slowbro", "magnemite", "magneton",
    "farfetch'd", "doduo", "dodrio",
    "seel", "dewgong", "grimer",
    "muk", "shellder", "cloyster",
    "gastly", "haunter", "gengar",
    "onix", "drowzee", "hypno",
    "krabby", "kingler", "exeggcute",
    "exeggutor", "cubone", "marowak",
    "hitmonlee", "hitmonchan", "lickitung",
    "koffing", "weezing", "rhyhorn",
    "rhydon", "chansey", "tangela",
    "kangaskhan", "horsea", "seadra",
    "goldeen", "seaking", "staryu",
    "starmie", "mr mime", "scyther",
    "electabuzz", "magmar", "pinsir",
    "tauros", "magikarp", "gyarados",
    "lapras", "ditto", "eevee",
    "vaporeon", "jolteon", "flareon",
    "porygon"
)

class TemporaryDatabase {

    companion object {
        val todayPokemon = PokemonModel(
            name = "charmander",
            typeList = listOf("fire"),
            environment = "mountain",
            color = "red",
//            evolutionStage = 1,
            averageHeight = 6,
            averageWeight = 85,
            isLegendary = false,
            isMythical = false
        )



    }
}