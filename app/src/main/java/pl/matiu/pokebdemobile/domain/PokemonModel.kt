package pl.matiu.pokebdemobile.domain

data class PokemonModel(
    var name: String? = "",
    var typeList: List<String>? = mutableListOf<String>(""),
    var color: String? = "",
    var environment: String? = "",
//    var evolutionStage: Int? = -1,
    var averageHeight: Int? = -1,
    var averageWeight: Int? = -1,
    var isLegendary: Boolean = false,
    var isMythical: Boolean = false
)