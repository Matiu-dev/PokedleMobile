package pl.matiu.pokebdemobile.domain

class TemporaryDatabase {

    companion object {
        var todayPokemon = PokemonModel(
            name = "charmander",
            typeList = listOf("fire"),
            environment = "mountain",
            color = "orange",
            evolutionStage = 1,
            averageHeight = 60.0F,
            averageWeight = 8.5F,
            isLegendary = false,
            isMythical = false
        )

        var pokemonGuessList = mutableMapOf(
            1L to PokemonModel(
                name = "charmander",
                typeList = listOf("fire"),
                environment = "mountain",
                color = "orange",
                evolutionStage = 1,
                averageHeight = 60.0F,
                averageWeight = 8.5F,
                isLegendary = false,
                isMythical = false
            ),
            2L to PokemonModel(
                name = "bulbasaur",
                typeList = listOf("grass", "poison"),
                environment = "grassland",
                color = "green",
                evolutionStage = 1,
                averageHeight = 70.0F,
                averageWeight = 6.9F,
                isLegendary = false,
                isMythical = false
            ),
            3L to PokemonModel(
                name = "squirtle",
                typeList = listOf("water"),
                environment = "waters-edge",
                color = "blue",
                evolutionStage = 1,
                averageHeight = 50.0F,
                averageWeight = 9F,
                isLegendary = false,
                isMythical = false
            )
        )

    }

    fun getPokemonGuessList(): Map<Long, PokemonModel> {
        return pokemonGuessList
    }
}