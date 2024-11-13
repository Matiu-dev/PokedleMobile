package pl.matiu.pokebdemobile.domain

class TemporaryDatabase {

    companion object {
        var todayPokemon = PokemonModel(
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

        var pokemonGuessList = mutableMapOf(
            1L to PokemonModel(
                name = "charmander",
                typeList = listOf("fire"),
                environment = "mountain",
                color = "orange",
//                evolutionStage = 1,
                averageHeight = 60,
                averageWeight = 8,
                isLegendary = false,
                isMythical = false
            ),
            2L to PokemonModel(
                name = "bulbasaur",
                typeList = listOf("grass", "poison"),
                environment = "grassland",
                color = "green",
//                evolutionStage = 1,
                averageHeight = 70,
                averageWeight = 6,
                isLegendary = false,
                isMythical = false
            ),
            3L to PokemonModel(
                name = "squirtle",
                typeList = listOf("water"),
                environment = "waters-edge",
                color = "blue",
//                evolutionStage = 1,
                averageHeight = 50,
                averageWeight = 9,
                isLegendary = false,
                isMythical = false
            )
        )

    }

    fun getPokemonGuessList(): Map<Long, PokemonModel> {
        return pokemonGuessList
    }
}